package com.example.hreminder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.R;

public class MainActivity extends BaseActitivty {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void onClickSwitchToCalender(View view) {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSwitchToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
