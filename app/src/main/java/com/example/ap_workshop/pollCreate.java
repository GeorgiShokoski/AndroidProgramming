package com.example.ap_workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class pollCreate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    public ViewGroup layout;
    public Button optionBtn;
    public EditText question;
    public int cnt;
    public String[] textEditIds;
    public int arrMove;
    public String authorName;

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_create);

        createNotificationChannel();

        optionBtn = findViewById(R.id.optionBtn);
        layout = findViewById(R.id.layoutAdd);
        question = findViewById(R.id.questionTextEdit);

        cnt = 0;
        textEditIds = new String[4];
        int arrMove = 0;

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

        Intent getAuthorName = getIntent();
        authorName = getAuthorName.getStringExtra("author name");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Intent goToLogin = new Intent(pollCreate.this, MainActivity.class);
            startActivity(goToLogin);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_polls) {
            Intent goToPolls = new Intent(pollCreate.this, adminPanel.class);
            startActivity(goToPolls);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addNewOption(View view){
        cnt++;
        if(cnt <= 4) {
            EditText textEdit = new EditText(getApplicationContext());
            LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params.setMargins(100,30,100,30);
            textEdit.setLayoutParams(params);
            textEdit.setInputType(InputType.TYPE_CLASS_TEXT);
            textEdit.setHint("Option");
            textEdit.setTextColor(Color.parseColor("white"));
            textEdit.setHintTextColor(Color.parseColor("white"));
            textEdit.setId(View.generateViewId());
            textEdit.setPadding(50, 15, 100, 15);
            textEdit.setBackgroundColor(Color.parseColor("#FF5722"));
            layout.addView(textEdit);
            textEditIds[arrMove] = textEdit.getText().toString();
            arrMove++;
        }else{
            Toast.makeText(pollCreate.this, "You can only add a maximum of four options!", Toast.LENGTH_SHORT).show();
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

    public void sendNotification(View view){
        if((!isEmpty(question)) && (cnt >= 2)) {
            Intent goToPolls = new Intent(pollCreate.this, adminPanel.class);
            String textEditQuestion = question.getText().toString();
            goToPolls.putExtra("text_edit_question", textEditQuestion);
            goToPolls.putExtra("text_edit_ids", textEditIds);
            goToPolls.putExtra("author_name", authorName);
            startActivity(goToPolls);

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle("A new poll has been created!")
                    .setContentText("Go vote!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1337, builder.build());
            question.setText("");
        }else{
            if(isEmpty(question)) {
                Toast.makeText(pollCreate.this, "You must write a question before posting!", Toast.LENGTH_SHORT).show();
            }
            else if(cnt < 2){
                Toast.makeText(pollCreate.this, "You must add options for voting before posting!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel for sending a notification";
            String description = "Please send";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}