package com.example.ap_workshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class registrationPage extends AppCompatActivity {

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    EditText firstName;
    EditText lastName;
    EditText emailUser;
    EditText passwordUser;
    Button regBtn;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailUser = findViewById(R.id.emailReg);
        passwordUser = findViewById(R.id.passwordReg);
        regBtn = findViewById(R.id.btnReg);

        dbHandler = new DBHandler(registrationPage.this);
        }

        public void checkDataEntered(View view){
        if((isEmpty(firstName)) || (isEmpty(lastName)) || (isEmpty(emailUser)) || (isEmpty(passwordUser))) {
            if (isEmpty(firstName)) {
                firstName.setError("First name is required!");
            }

            if (isEmpty(lastName)) {
                lastName.setError("Last name is required!");
            }

            if (isEmpty(emailUser)) {
                emailUser.setError("Email is required!");
            }

            if (isEmpty(passwordUser)) {
                passwordUser.setError("Password is required!");
            }

        }else{
            String firstname = firstName.getText().toString();
            String lastname = lastName.getText().toString();
            String email = emailUser.getText().toString();
            String password = passwordUser.getText().toString();

            dbHandler.addNewUser(firstname, lastname, email, password);

            Toast.makeText(registrationPage.this, "You have successfully signed up!", Toast.LENGTH_SHORT).show();
            firstName.setText("");
            lastName.setText("");
            emailUser.setText("");
            passwordUser.setText("");

            Intent loginIntent = new Intent(registrationPage.this, MainActivity.class);
            startActivity(loginIntent);
        }
    }


        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            return super.dispatchTouchEvent(ev);
        }
    }
