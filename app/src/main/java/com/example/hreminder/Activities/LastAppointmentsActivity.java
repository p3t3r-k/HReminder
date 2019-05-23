package com.example.hreminder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hreminder.R;

public class LastAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_appointments);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerDoc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.doctorsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void onClickSwitchToCalenderAct(View view) {
        Intent intent = new Intent(this, CalenderActivity.class); startActivity(intent);
    }
}
