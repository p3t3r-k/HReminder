package com.example.hreminder;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import static android.graphics.Color.parseColor;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClickSwitchToPin(View view) {
        Intent intent = new Intent(this, ChangePinActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchToChangeProfile(View view) {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("source", "SettingsActivity");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
    