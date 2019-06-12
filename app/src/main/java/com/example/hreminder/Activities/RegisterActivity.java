package com.example.hreminder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.Database.HReminder;
import com.example.hreminder.Database.HRepository;
import com.example.hreminder.Local.CreateDatabase;
import com.example.hreminder.Local.HReminderDAO;
import com.example.hreminder.Local.ReminderDataSource;
import com.example.hreminder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    //Database
    private CompositeDisposable compositeDisposable;
    private HRepository hRepository;

    //adapter
    //ArrayAdapter adapter;
    //List<HReminder> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Init
        compositeDisposable = new CompositeDisposable();
        //Database
        CreateDatabase createDatabase = CreateDatabase.getInstance(this);
        hRepository = HRepository.getInstance(ReminderDataSource.getInstance(createDatabase.reminderDAO()));



        //Load all data from Database
        //loadData();


    }

    private void saveData() {


        Disposable disposable = Observable.create(emitter -> {
            //getUserData();
            HReminder hReminder = new HReminder(getcreateUsername, getcreatePin, false, "w", 10 - 10 - 2000, 123, false, false, false, false, false, false, false, false);
            hRepository.insertDB(hReminder);
            emitter.onComplete();
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer) o -> Toast.makeText(RegisterActivity.this, "user added", Toast.LENGTH_LONG).show(), throwable -> Toast.makeText(RegisterActivity.this, "" + throwable.getMessage(), Toast.LENGTH_LONG).show(), () -> loadData()

                );

    }

    private void loadData() {
        //Use RxJava
        Disposable disposable = hRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(hReminders -> onGetAllUserSuccess(hReminders), throwable -> Toast.makeText(RegisterActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show());
        compositeDisposable.add(disposable);

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

    private void onGetAllUserSuccess(HReminder hReminders) {
        //list.clear();
        //list.addAll(hReminders);
        //adapter.notifyDataSetChanged();
    }

    private void checkData() {

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
            if (checkPinMatch && checkPin && checkUsername){
                checkInput = true;
            } else {
                checkInput = false;
            }
        }


    public void onClickSwitchToFingerprint(View view) {

        getUserData();
        checkData();
        if (checkInput) {
            saveData();

            Intent intent = new Intent(this, Fingerprint_login.class);
            startActivity(intent);
            finish();
        } else {
            Log.e("ErrorRegister","Datenregisitrieung fehlgeschlagen.");
            Toast.makeText(getApplicationContext(), "Registrierung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
        }

    }
}
