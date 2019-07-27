package com.example.hreminder.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;

import com.example.hreminder.R;
import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.behindTheScenes.LocaleManager;
import com.example.hreminder.behindTheScenes.Session;
import com.example.hreminder.database.DbHelper;

import java.util.Objects;

import static android.content.pm.PackageManager.GET_META_DATA;
import static android.graphics.Color.parseColor;

/**
 * Activity to make some changes
 */
public class SettingsActivity extends BaseActivity {

    private String callingActivity;
    private Session session;
    private DbHelper db;
    private String lastUserID;

    /**
     * build layout, action bar, and get instance of database
     * @param savedInstanceState savedInstance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetTitles();
        setContentView(R.layout.activity_settings);
        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setTitle(getResources().getString(R.string.app_name));
        abar.setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        session = new Session(this);

        onClickChangeLanguage();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            callingActivity = extras.getString("source");
        }

        db = new DbHelper(this);
    }

    /**
     * get last logged user ID
     */
    @Override
    protected void onStart() {
        super.onStart();
        lastUserID = db.getLastUserID();
        LastUser.setLastUserID(lastUserID);
    }

    /**
     * build dialog to let user change Language
     */
    private void showChangeLanguageDialog() {
        final String[] languageList = {"English", "Deutsch"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);


        mBuilder.setTitle(R.string.changeLanguageDialog);
        mBuilder.setSingleChoiceItems(languageList, -1, (dialog, i) -> {
            if (i == 0) {
                LocaleManager.setNewLocale(getBaseContext(), LocaleManager.LANGUAGE_KEY_ENGLISH);
                recreate();
            } else {
                LocaleManager.setNewLocale(getBaseContext(), LocaleManager.LANGUAGE_KEY_DEUTSCH);

                recreate();
            }
            dialog.dismiss();
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }

    /**
     * onClickListener for change language
     */
    private void onClickChangeLanguage() {
        Button changeLang = findViewById(R.id.btnLanguage);
        changeLang.setOnClickListener(v -> showChangeLanguageDialog());
    }


    /**
     * get Local
     * @param base context
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    /**
     * reset layout
     */
    private void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * go to ChangePinActivity
     * @param view Button
     */
    public void onClickSwitchToPin(View view) {
        Intent intent = new Intent(this, ChangePinActivity.class);
        startActivity(intent);
    }

    /**
     * go to ChangeProfileActivity
     * @param view Button
     */
    public void onClickSwitchToChangeProfile(View view) {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("source", "SettingsActivity");
        startActivity(intent);
    }

    /**
     * logout current user
     * @param view Button
     */
    public void onClickLogout(View view) {
        logout();
    }

    /**
     * logout user - go to MainActivity
     */
    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
    }

    /**
     * delete Profile and delete all database entries
     * @param view Button
     */
    public void onClickDeleteProfile(View view) {

        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle(R.string.deleteProfileDialog)
                .setMessage(R.string.deleteProfileDialogMsg)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    db.dropUserProfile(lastUserID);
                    logout();
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    /**
     * onClick on Actionbar menu change to respective Activities
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (callingActivity != null && callingActivity.equals("CalenderActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, CalenderActivity.class));
            } else if (callingActivity != null && callingActivity.equals("ChangeProfileActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, ChangeProfileActivity.class));
            } else if (callingActivity != null && callingActivity.equals("LastAppointmentsActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, LastAppointmentsActivity.class));
            } else if (callingActivity != null && callingActivity.equals("DoctorsMapActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, DoctorsMapActivity.class));
            } else {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
