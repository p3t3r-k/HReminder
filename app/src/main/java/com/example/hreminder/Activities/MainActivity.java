package com.example.hreminder.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.BehindTheScenes.LastUser;
import com.example.hreminder.BehindTheScenes.Session;
import com.example.hreminder.Database.DbHelper;
import com.example.hreminder.Database.HReminder;
import com.example.hreminder.Local.CreateDatabase;
import com.example.hreminder.R;

import io.reactivex.Flowable;

public class MainActivity extends BaseActitivty {

    private EditText getLoginUsername;
    private EditText getLoginPin;

    private EditText userNameField;

    private String checkLoginUsername;
    private String checkLoginPin;

    private String lastUserID;

    private DbHelper db;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);
        session = new Session(this);

        if (session.loggedin()) {
            startActivity(new Intent(MainActivity.this, CalenderActivity.class));
            finish();
        }


   /*   if (db.getLastUserID() != null){
            lastUserID = db.getLastUserID();
            userNameField = findViewById(R.id.inputUsername);
            userNameField.setText(db.getUsernameByID(idTest));
        }*/

    }

    private void login() {

        getLoginUsername = findViewById(R.id.inputUsername);
        checkLoginUsername = getLoginUsername.getText().toString();

        getLoginPin = findViewById(R.id.inputPin);
        checkLoginPin = getLoginPin.getText().toString();

    }



    public void onClickSwitchToCalender(View view) {

        login();
        if (db.getUser(checkLoginUsername, checkLoginPin)) {

            //get & set UserID for further Activities
            long id = db.getUserIDByName(checkLoginUsername);
            String lastUserID = Long.toString(id);
            if (lastUserID != null) {
                LastUser.setLastUserID(lastUserID);
                db.setLastUserID(lastUserID);
            }

            session.setLoggedin(true);
            Intent intent = new Intent(this, CalenderActivity.class);
            startActivity(intent);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.loginError);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    public void onClickSwitchToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickPromptFingerprint(View view) {
        //Fingerprint Abfrage starten
        Intent intent = new Intent(this, FingerprintPrActivity.class);
        startActivity(intent);
    }


}
