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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class resultsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public int pollId;
    public resultsDBHandler resultsdbHandler;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    public SQLiteDatabase db;
    public TextView option1;
    public TextView option2;
    public TextView option3;
    public TextView option4;
    public TextView value1;
    public TextView value2;
    public TextView value3;
    public TextView value4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent getIntent = getIntent();
        pollId = Integer.parseInt(getIntent.getStringExtra("pollId"));

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

        option1 = findViewById(R.id.optionNameOne);
        option2 = findViewById(R.id.optionNameTwo);
        option3 = findViewById(R.id.optionNameThree);
        option4 = findViewById(R.id.optionNameFour);

        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        value3 = findViewById(R.id.value3);
        value4 = findViewById(R.id.value4);

        resultsdbHandler = new resultsDBHandler(resultsActivity.this);
        db = resultsdbHandler.getReadableDatabase();

        int cnt = 0;

        String query = "SELECT COUNT(*) as numberVotes,optionName FROM resultsPoll WHERE pollId=? GROUP BY optionName";
        String[] selectionArgs = {String.valueOf(pollId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        while(cursor.moveToNext()){
            if(cnt == 0) {
                option1.setText(cursor.getString(1));
                value1.setText(String.valueOf(cursor.getInt(0)));
                cnt++;
            }else if(cnt == 1) {
                option2.setText(cursor.getString(1));
                value2.setText(String.valueOf(cursor.getInt(0)));
                cnt++;
            }else if(cnt == 2){
                option3.setVisibility(option3.VISIBLE);
                value3.setVisibility(option3.VISIBLE);
                option3.setText(cursor.getString(1));
                value3.setText(String.valueOf(cursor.getInt(0)));
                cnt++;
            }else{
                option4.setVisibility(option4.VISIBLE);
                value4.setVisibility(option3.VISIBLE);
                option4.setText(cursor.getString(1));
                value4.setText(String.valueOf(cursor.getInt(0)));
                cnt++;
            }
        }

        cursor.close();
        db.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Intent goToLogin = new Intent(resultsActivity.this, MainActivity.class);
            startActivity(goToLogin);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_polls) {
            Intent goToPolls = new Intent(resultsActivity.this, adminPanel.class);
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
}