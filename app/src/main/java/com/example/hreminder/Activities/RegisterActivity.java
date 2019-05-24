package com.example.hreminder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hreminder.Database.HRepository;
import com.example.hreminder.Database.IDataSource;
import com.example.hreminder.Local.CreateDatabase;
import com.example.hreminder.Local.ReminderDAO;
import com.example.hreminder.Local.ReminderDataSource;
import com.example.hreminder.R;

import io.reactivex.disposables.CompositeDisposable;

public class RegisterActivity extends AppCompatActivity {


        private Button register;

        private CompositeDisposable compositeDisposable;
        private HRepository hRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        compositeDisposable = new CompositeDisposable();

        //com.example.hreminder.Activities.Database
        CreateDatabase createDatabase = CreateDatabase.getInstance(this);
        hRepository = HRepository.getInstance(ReminderDataSource.getInstance(createDatabase.reminderDAO()));

    }

    public void onClickSwitchToFingerprint(View view) {
        Intent intent = new Intent(this, Fingerprint_login.class);
        startActivity(intent);
        finish();
    }
}
