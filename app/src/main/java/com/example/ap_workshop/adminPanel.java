package com.example.ap_workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class adminPanel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView textView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    public String firstName;
    public optionDBHandler optiondbHandler;
    public pollDBHandler polldbHandler;
    public SQLiteDatabase db;
    public ImageButton addPoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        addPoll = findViewById(R.id.addPoll);
        addPoll.bringToFront();

        RecyclerView recyclerView = findViewById(R.id.pollRecycler);

        List<String> data = new ArrayList<>();
        // populate the data list with your data
        optiondbHandler = new optionDBHandler(adminPanel.this);
        polldbHandler = new pollDBHandler(adminPanel.this);
        db = polldbHandler.getReadableDatabase();

        String pollQuery = "SELECT id, question FROM polls WHERE dateTo > datetime()";
        Cursor pollCursor = db.rawQuery(pollQuery, null);
        while(pollCursor.moveToNext()){
            String question = pollCursor.getString(1);
            data.add(question);
        }


        pollAdapter adapter = new pollAdapter(data);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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

        Intent getIntent = getIntent();
        firstName = getIntent.getStringExtra("first name");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createPoll(View view){
        Intent goToPoll = new Intent(adminPanel.this, pollCreate.class);
        goToPoll.putExtra("author_name", firstName);
        startActivity(goToPoll);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Intent goToLogin = new Intent(adminPanel.this, MainActivity.class);
            startActivity(goToLogin);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_polls) {
            // Handle the item2 action
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}