package com.darkness.WSafety;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ContactActivity extends AppCompatActivity {
    EditText contact;
    RecyclerView recyclerView;
    HashMap<String,String> contacts;
    ArrayList<String> send;
    ContactsAdapter adapter;
    ImageView edit;
    TextView callerInfo;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        edit = findViewById(R.id.editCallButton);
        Button policeButton = findViewById(R.id.btn_police);
        Button hospitalButton = findViewById(R.id.btn_hospital);

        edit.setOnClickListener(v -> {
            Dialog dialog = new Dialog(ContactActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            Button close,save;
            close = dialog.findViewById(R.id.dialogCancel);
            save = dialog.findViewById(R.id.dialogSave);
            EditText  number = dialog.findViewById(R.id.dialogEditText);
            save.setOnClickListener(view -> {
                String numberText = number.getText().toString();
                if (numberText.length() == 10) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("firstNumber", numberText);
                    editor.apply();
                    setCallingInformation();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ContactActivity.this, "Enter valid number!", Toast.LENGTH_SHORT).show();
                }
            });
            close.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });
        callerInfo = findViewById(R.id.callText);
        setCallingInformation();
        contacts = new HashMap<>();
        send = new ArrayList<>();
        adapter = new ContactsAdapter(this, send, this::deleteItemFromDatabase);
        recyclerView = findViewById(R.id.contacts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        contact = findViewById(R.id.contactGet);
        Button addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(view -> createContact(contact.getText().toString()));

        policeButton.setOnClickListener(v -> {
            String searchQuery = "Near by Police Stations";
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + searchQuery);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });


        hospitalButton.setOnClickListener(v -> {
            String searchQuery = "Near by Hospitals";
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + searchQuery);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });


    }

    @SuppressLint("MutatingSharedPrefs")
    private void createContact(String contactString) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> oldNumbers = sharedPreferences.getStringSet("enumbers", new LinkedHashSet<>());
        oldNumbers.add(contactString);
        editor.remove("enumbers");
        editor.putStringSet("enumbers",oldNumbers);
        editor.apply();

        contact.setText("");
        editor.apply();
        getData();

    }
    @SuppressLint("SetTextI18n")
    private void setCallingInformation(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String firstNumber = sharedPreferences.getString("firstNumber","null");

        if (firstNumber.isEmpty()||firstNumber.equalsIgnoreCase("null")){
            callerInfo.setText("Please add number.");
        }else {
            callerInfo.setText(firstNumber);
        }

    }
    @SuppressLint("MutatingSharedPrefs")
    private void deleteItemFromDatabase(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        Set<String> oldNumbers = sharedPreferences.getStringSet("enumbers", new LinkedHashSet<>());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("enumbers");
        oldNumbers.remove(send.get(position));
        editor.putStringSet("enumbers",oldNumbers);
        editor.apply();
        getData();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        send.clear();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        Set<String> oldNumbers = sharedPreferences.getStringSet("enumbers", new LinkedHashSet<>());
        send.addAll(oldNumbers);
        adapter.notifyDataSetChanged();
    }
}
