package com.example.ap_workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.security.cert.PKIXRevocationChecker;

public class voteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public TextView questionView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    public optionDBHandler optiondbHandler;
    public pollDBHandler polldbHandler;
    public SQLiteDatabase db;
    public RadioButton Option1;
    public RadioButton Option2;
    public RadioButton Option3;
    public RadioButton Option4;
    public Button resultBtn;
    public int pollId;
    public resultsDBHandler resultsdbHandler;
    public RadioGroup radiogroup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Option1 = findViewById(R.id.radio_button_1);
        Option2 = findViewById(R.id.radio_button_2);
        Option3 = findViewById(R.id.radio_button_3);
        Option4 = findViewById(R.id.radio_button_4);
        resultBtn = findViewById(R.id.resultBtn);
        radiogroup = findViewById(R.id.radio_group);

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
        );

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navView = findViewById(R.id.navigationView);
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        Intent intQuestion = getIntent();
        String strQuestion = intQuestion.getStringExtra("text");

        questionView = findViewById(R.id.voteQuestion);
        questionView.setText(strQuestion);

        optiondbHandler = new optionDBHandler(voteActivity.this);
        polldbHandler = new pollDBHandler(voteActivity.this);
        resultsdbHandler = new resultsDBHandler(voteActivity.this);
        db = polldbHandler.getReadableDatabase();

        String pollQuery = "SELECT id FROM polls WHERE question=?";
        String[] selectionArgs = {String.valueOf(questionView.getText())};

        Cursor cursor = db.rawQuery(pollQuery, selectionArgs);
        cursor.moveToFirst();
        pollId = cursor.getInt(0);

        cursor.close();
        db.close();

        int cnt = 0;

        db = optiondbHandler.getReadableDatabase();

        String optionsQuery = "SELECT optionName FROM pollOptions WHERE pollId=?";
        String[] selectionArgsPollId = {String.valueOf(pollId)};

        Cursor pollOptionsCursor = db.rawQuery(optionsQuery, selectionArgsPollId);
        while(pollOptionsCursor.moveToNext()){
            if(cnt == 0) {
                Option1.setText(pollOptionsCursor.getString(0));
                cnt++;
            }else if(cnt == 1) {
                Option2.setText(pollOptionsCursor.getString(0));
                cnt++;
            }else if(cnt == 2){
                Option3.setVisibility(Option3.VISIBLE);
                Option3.setText(pollOptionsCursor.getString(0));
                cnt++;
            }else{
                Option4.setVisibility(Option4.VISIBLE);
                Option4.setText(pollOptionsCursor.getString(0));
                cnt++;
            }
        }
        pollOptionsCursor.close();
        db.close();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Intent goToLogin = new Intent(voteActivity.this, MainActivity.class);
            startActivity(goToLogin);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_polls) {
            Intent goToPolls = new Intent(voteActivity.this, adminPanel.class);
            startActivity(goToPolls);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToResults(View view){
        Intent result = new Intent(voteActivity.this, resultsActivity.class);
        result.putExtra("pollId", String.valueOf(pollId));
        startActivity(result);
    }

    public void insertResult(View view){
        int checkedId = radiogroup.getCheckedRadioButtonId();
        if(checkedId != -1){
            RadioButton radioButton = findViewById(checkedId);
            String value = radioButton.getText().toString();
            resultsdbHandler.addNewResult(value, pollId);
        }
        Intent seeResults = new Intent(voteActivity.this, resultsActivity.class);
        seeResults.putExtra("pollId", String.valueOf(pollId));
        startActivity(seeResults);
    }
}