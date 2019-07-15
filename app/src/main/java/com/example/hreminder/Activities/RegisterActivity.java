package com.example.hreminder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.BehindTheScenes.LastUser;
import com.example.hreminder.Database.DbHelper;
import com.example.hreminder.R;

import java.util.regex.Pattern;

public class RegisterActivity extends BaseActitivty {

    private EditText inputCreateUsername;
    private EditText inputCreatePin;
    private EditText inputValdiatePin;

    private String getcreatePin;
    private String getValidatePin;
    private String getcreateUsername;

    private boolean checkInput = false;
    private boolean checkUsername = false;
    private boolean checkPin = false;
    private boolean checkPinMatch = false;


    private DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
    }


    private void getUserData() {

        //get username
        inputCreateUsername = (EditText) findViewById(R.id.inputCreateUsername);
        getcreateUsername = inputCreateUsername.getText().toString();


        //get PIN
        inputCreatePin = (EditText) findViewById(R.id.inputCreatePin);
        getcreatePin = inputCreatePin.getText().toString();

        //get validate Pin
        inputValdiatePin = (EditText) findViewById(R.id.inputValidatePin);
        getValidatePin = inputValdiatePin.getText().toString();


    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void checkData() {

        if (getcreateUsername.isEmpty() && getcreatePin.isEmpty()) {
            displayToast("Username/password field empty");
        } else {

            final Pattern pattern = Pattern.compile("[A-Za-z0-9]*");
            if (!pattern.matcher(getcreateUsername).matches()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(R.string.createUsernameError);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                checkUsername = false;
            } else {
                checkUsername = true;
            }

            final Pattern pattern1 = Pattern.compile("[\\d]{4}");
            if (!pattern1.matcher(getcreatePin).matches()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(R.string.createPinError);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                checkPin = false;
            } else {
                checkPin = true;
            }

            if (!getcreatePin.matches(getValidatePin)) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                builder1.setMessage(R.string.createPinMatchingError);
                builder1.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
                checkPinMatch = false;
            } else {
                checkPinMatch = true;
            }

            if (checkPinMatch && checkPin && checkUsername) {
                checkInput = true;
            } else {
                checkInput = false;
            }
        }

    }


    public void onClickSwitchToProfileCreate(View view) {

        getUserData();
        checkData();
        if (checkInput) {

            db.addUser(getcreateUsername, getcreatePin);

            long id = db.getUserIDByName(getcreateUsername);
            String lastUserID = Long.toString(id);
            if (lastUserID != null) {
                LastUser.setLastUserID(lastUserID);
                db.setLastUserID(LastUser.getLastUserID());
            }

            Intent intent = new Intent(this, UserProfilActivity.class);
            intent.putExtra("loggedUser",getcreateUsername);
            startActivity(intent);
            finish();
        } else {
            Log.e("ErrorRegister", "Datenregisitrieung fehlgeschlagen.");
            Toast.makeText(getApplicationContext(), "Registrierung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
        }

    }
}
