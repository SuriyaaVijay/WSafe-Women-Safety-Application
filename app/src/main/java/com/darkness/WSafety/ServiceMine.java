
package com.darkness.WSafety;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashSet;
import java.util.Set;

public class ServiceMine extends Service {

    static boolean isRunning = false;

    static MediaPlayer mediaPlayer;

    FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    SmsManager manager = SmsManager.getDefault();
    String myLocation;
    View view;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.siren);
        mediaPlayer.setLooping(true);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            location.getAltitude();
                            location.getLongitude();
                            myLocation = "http://maps.google.com/maps?q=loc:"+location.getLatitude()+","+location.getLongitude();
                        }else {
                            myLocation = "Unable to Find Location :(";
                        }
                    }
                });

        ShakeDetector.create(this, () -> {
            mediaPlayer.start();
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            Set<String> oldNumbers = sharedPreferences.getStringSet("enumbers", new HashSet<>());
            if(!oldNumbers.isEmpty()){
                for(String ENUM : oldNumbers)
                manager.sendTextMessage(ENUM,null,"Im in Trouble!\nSending My Location :\n"+myLocation,null,null);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            if (intent.getAction().equalsIgnoreCase("STOP")) {
                if(isRunning){
                    mediaPlayer.stop();
                    this.stopForeground(true);
                    this.stopSelf();
                    isRunning = false;
                }
            } else {
                Intent notificationIntent = new Intent(this, SmsActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("MYID", "CHANNELFOREGROUND", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    m.createNotificationChannel(channel);
                    Notification notification = new Notification.Builder(this, "MYID")
                            .setContentTitle("Women Safety")
                            .setContentText("Shake Device to Send SOS")
                            .setSmallIcon(R.drawable.girlpower)
                            .setContentIntent(pendingIntent)
                            .build();
                    this.startForeground(115, notification);
                    isRunning = true;
                    return START_NOT_STICKY;
                }
                if (intent.getAction().equalsIgnoreCase("PLAY")) {
                    mediaPlayer.start();
                }
            }

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
}
