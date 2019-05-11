package com.example.hreminder;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import static android.graphics.Color.parseColor;

public class CalenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        ActionBar abar = getSupportActionBar();
        abar.setLogo(R.drawable.deletesign);
        abar.setDisplayUseLogoEnabled(true);
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.calender_ab, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Info icon is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Setting icon is selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
