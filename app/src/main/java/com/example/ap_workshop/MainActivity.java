package com.example.ap_workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emailUser;
    EditText passwordUser;
    Button login;
    TextView regNavigation;
    DBHandler dbHandler;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.userPassword);
        login = findViewById(R.id.loginBtn);
        regNavigation = findViewById(R.id.registrationPage);

        dbHandler = new DBHandler(MainActivity.this);
        db = dbHandler.getReadableDatabase();

        String text = "Don't have an account? Click here to register!";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableText = new ClickableSpan() {
            public void onClick(View widget) {
                Intent registerIntent = new Intent(MainActivity.this, registrationPage.class);
                startActivity(registerIntent);
            }
        };

        ss.setSpan(clickableText, 29, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        regNavigation.setText(ss);
        regNavigation.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void loginCheck(View view){
        String query = "SELECT firstname, email, password FROM registeredUsers WHERE email=?";
        String[] selectionArgs = {String.valueOf(emailUser.getText())};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "Please sign up first before trying to log in", Toast.LENGTH_SHORT).show();
            emailUser.setText("");
            passwordUser.setText("");
        }else{
            cursor.moveToFirst();
            if(cursor.getString(2).equals(String.valueOf(passwordUser.getText()))){
                Toast.makeText(MainActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                Intent afterLoginActivity = new Intent(MainActivity.this, adminPanel.class);
                String nameLoggedIn = cursor.getString(0);
                afterLoginActivity.putExtra("first name", nameLoggedIn);
                startActivity(afterLoginActivity);
            }else{
                Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                passwordUser.setText("");
            }
        }
        cursor.close();
    }
}