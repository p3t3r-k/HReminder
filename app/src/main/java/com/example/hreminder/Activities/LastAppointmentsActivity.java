package com.example.hreminder.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.hreminder.R;

import static android.graphics.Color.parseColor;

public class LastAppointmentsActivity extends AppCompatActivity {

    private String callingActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_appointments);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerDoc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.doctorsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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



    public void onClickSwitchToCalenderAct(View view) {
        Intent intent = new Intent(this, CalenderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println(callingActivity);
                if(callingActivity.equals("SettingsActivity")){
                    NavUtils.navigateUpTo(this, new Intent(this, SettingsActivity.class));
                }
                if (callingActivity.equals("DoctorsMapActivity")) {
                    NavUtils.navigateUpTo(this, new Intent(this, DoctorsMapActivity.class));
                } if (callingActivity.equals("ChangeProfileActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, ChangeProfileActivity.class));
            } else {
                    //zu Calender zurück
                    NavUtils.navigateUpTo(this, new Intent(this, CalenderActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
