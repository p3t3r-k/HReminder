package com.example.hreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Fingerprint_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_login);
    }

    public void onClickSwitchToProfile(View view) {
        Intent intent = new Intent(this, UserProfilActivity.class);
        startActivity(intent);
        finish();
    }
}
