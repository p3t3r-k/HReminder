package com.example.hreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;

import java.util.regex.Pattern;

/**
 * Activity to let user register and create an account
 */
public class RegisterActivity extends BaseActivity {

    private String getcreatePin;
    private String getValidatePin;
    private String getcreateUsername;

    private boolean checkInput = false;

    private DbHelper db;

    /**
     * build layout, and get instance of database
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
    }

    /**
     * get inputData
     */
    private void getUserData() {

        //get username
        EditText inputCreateUsername = findViewById(R.id.inputCreateUsername);
        getcreateUsername = inputCreateUsername.getText().toString();


        //get PIN
        EditText inputCreatePin = findViewById(R.id.inputCreatePin);
        getcreatePin = inputCreatePin.getText().toString();

        //get validate Pin
        EditText inputValdiatePin = findViewById(R.id.inputValidatePin);
        getValidatePin = inputValdiatePin.getText().toString();


    }

    /**
     * check if put in data is valid and fulfills the requirements
     * else AlertDialog shows up
     */
    private void checkData() {

        if (getcreateUsername.isEmpty() && getcreatePin.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Username/password field empty", Toast.LENGTH_SHORT).show();
        } else {

            final Pattern pattern = Pattern.compile("[A-Za-z0-9]*");
            boolean checkUsername;
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
            boolean checkPin;
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

            boolean checkPinMatch;
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

            checkInput = checkPinMatch && checkPin && checkUsername;
        }

    }

    /**
     * if input is valid, add Account to database and go to UserProfilActivity
     * @param view Button
     */
    public void onClickSwitchToProfileCreate(View view) {

        getUserData();
        checkData();
        if (checkInput) {

            db.addUser(getcreateUsername, getcreatePin);

            long id = db.getUserIDByName(getcreateUsername);
            String lastUserID = Long.toString(id);
                LastUser.setLastUserID(lastUserID);
                db.setLastUserID(LastUser.getLastUserID());


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
