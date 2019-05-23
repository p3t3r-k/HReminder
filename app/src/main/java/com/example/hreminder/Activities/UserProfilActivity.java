package com.example.hreminder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hreminder.R;

public class UserProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);
    }

    public void onClickSwitchToLastApp(View view) {
        Intent intent = new Intent(this, LastAppointmentsActivity.class); startActivity(intent);
    }

}
