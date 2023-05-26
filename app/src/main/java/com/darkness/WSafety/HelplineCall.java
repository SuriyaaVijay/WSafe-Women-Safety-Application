package com.darkness.WSafety;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class HelplineCall extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        View buttonDistress = findViewById(R.id.btn_dist);
        View buttonAbuse = findViewById(R.id.btn_abuse);
        View buttonPolice = findViewById(R.id.btn_pol);
        View buttonHelpline = findViewById(R.id.btn_stud);
        View buttonAmbulance = findViewById(R.id.btn_ambulance);

        buttonDistress.setOnClickListener(this::callDistress);
        buttonAbuse.setOnClickListener(this::callAbuse);
        buttonPolice.setOnClickListener(this::callPolice);
        buttonHelpline.setOnClickListener(this::callHelpline);
        buttonAmbulance.setOnClickListener(this::callAmbulance);
    }

    public void callDistress(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:1091"));
        startActivity(intent);
    }

    public void callAbuse(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:181"));
        startActivity(intent);
    }

    public void callPolice(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:100"));
        startActivity(intent);
    }

    public void callHelpline(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:1098"));
        startActivity(intent);
    }

    public void callAmbulance(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:108"));
        startActivity(intent);
    }
}