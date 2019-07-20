package com.example.hreminder.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;

import java.util.Objects;

import static android.graphics.Color.parseColor;

public class ChangePinActivity extends BaseActivity {

    private DbHelper db;
    private String iDUser;
    private String newPinS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        db = new DbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        iDUser = LastUser.getLastUserID();
    }


    private boolean getData(){
        //check if UserID and Pin match
        EditText oldPin = findViewById(R.id.inputOldPin);
        String oldPinS = oldPin.getText().toString();

        if (db.checkPinByID(iDUser,oldPinS)){
            EditText newPin = findViewById(R.id.inputNewPin);
            //PIN for DB-Update
            newPinS = newPin.getText().toString();
            EditText reNewPin = findViewById(R.id.inputValidatePin);
            String reNewPinS = reNewPin.getText().toString();

            if (!newPinS.equals(reNewPinS) || newPinS.equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePinActivity.this);
                builder.setMessage(R.string.changePINNotMatching);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            } else if(newPinS.length() != 4){
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePinActivity.this);
                builder.setMessage(R.string.changePINTooShort);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;

            }else{
                return true;
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePinActivity.this);
            builder.setMessage(R.string.changePINWrongPIN);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickChangePin(View view) {
        if (getData()){
        if (db.updatePIN(iDUser,newPinS)){
            Toast.makeText(getApplicationContext(),R.string.changePINSuccess,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,SettingsActivity.class);
            intent.putExtra("source", "CalenderActivity");
            startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePinActivity.this);
            builder.setMessage(R.string.changePINFail);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();}
        }


    }
}
