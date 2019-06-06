package com.example.hreminder.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.R;

import static android.graphics.Color.parseColor;

public class ChangeProfileActivity extends BaseActitivty {

    private String callingActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            callingActivity = extras.getString("source");
        } else{
            //kein Extra
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_main, menu);
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
            case R.id.action_home:
                goToHome();
                return true;
            case android.R.id.home:
                if(callingActivity.equals("SettingsActivity")){
                    NavUtils.navigateUpTo(this, new Intent(this, SettingsActivity.class));
                }else{
                    //zu Calender zurück
                    NavUtils.navigateUpTo(this,new Intent(this, CalenderActivity.class));
                }
                return true;
            case R.id.action_appointments:
                goToAppointments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToHome(){
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchToHome(View view) {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    public void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "ChangeProfileActivity");
        startActivity(intent);
    }
}
