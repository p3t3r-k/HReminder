package com.example.hreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.behindTheScenes.Session;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;


public class MainActivity extends BaseActivity {

    private String checkLoginUsername;
    private String checkLoginPin;

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

        if (db.checkIfLogExists()) {
            if (db.getLastUserID() != null) {
                String lastUserID = db.getLastUserID();
                EditText userNameField = findViewById(R.id.inputUsername);
                userNameField.setText(db.getUsernameByID(lastUserID));
            }
        }

    }

    private void login() {

        EditText getLoginUsername = findViewById(R.id.inputUsername);
        checkLoginUsername = getLoginUsername.getText().toString();

        EditText getLoginPin = findViewById(R.id.inputPin);
        checkLoginPin = getLoginPin.getText().toString();

    }



    public void onClickSwitchToCalender(View view) {

        login();
        if (db.getUser(checkLoginUsername, checkLoginPin)) {

            //get & set UserID for further Activities
            long id = db.getUserIDByName(checkLoginUsername);
            String lastUserID = Long.toString(id);
                LastUser.setLastUserID(lastUserID);
                db.setLastUserID(lastUserID);


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
