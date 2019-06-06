package com.example.hreminder.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.R;

import static android.graphics.Color.parseColor;

public class ChangePinActivity extends BaseActitivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
