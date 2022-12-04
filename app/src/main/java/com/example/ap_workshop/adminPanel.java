package com.example.ap_workshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class adminPanel extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Intent getIntent = getIntent();
        String firstName = getIntent.getStringExtra("first name");

        textView = findViewById(R.id.getFirstNameText);
        textView.setText(firstName);
    }

    public void logOut(View view){
        Intent goToLogin = new Intent(adminPanel.this, MainActivity.class);
        startActivity(goToLogin);
    }

    public void createPoll(View view){
        Intent goToPoll = new Intent(adminPanel.this, pollCreate.class);
        startActivity(goToPoll);
    }
}