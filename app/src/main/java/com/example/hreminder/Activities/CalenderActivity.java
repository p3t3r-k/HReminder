package com.example.hreminder.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hreminder.R;

import static android.graphics.Color.parseColor;

public class CalenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CalenderActivity.this, CalenderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_calender, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                gotToSettings();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Help icon is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_changeProfile:
                goToProfile();
                return true;
            case R.id.action_map:
                goToMap();
                return true;
            case R.id.action_appointments:
                goToAppointments();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToProfile() {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }

    public void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToMap() {
        Intent intent = new Intent(this, DoctorsMapActivity.class);
        startActivity(intent);
    }

    public void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "CalenderActivity");
        startActivity(intent);
    }


}


