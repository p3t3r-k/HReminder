package com.example.hreminder.Activities;

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

import com.example.hreminder.BehindTheScenes.BaseActitivty;
import com.example.hreminder.BehindTheScenes.LocaleManager;
import com.example.hreminder.BehindTheScenes.Session;
import com.example.hreminder.R;
import java.util.Locale;
import java.util.Objects;

import static android.graphics.Color.parseColor;
import static android.content.pm.PackageManager.GET_META_DATA;

public class SettingsActivity extends BaseActitivty {

    String callingActivity;

    private Session session;

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


    }




    private void showChangeLanguageDialog() {
        final String [] languageList = {"English", "Deutsch"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        String selectedLang = Locale.getDefault().getDisplayLanguage();


        mBuilder.setTitle(R.string.changeLanguageDialog);
        mBuilder.setSingleChoiceItems(languageList, -1, (dialog, i) -> {
            if (i == 0){
                LocaleManager.setNewLocale(getBaseContext(), LocaleManager.LANGUAGE_KEY_ENGLISH);
                recreate();
            }
            else {
                LocaleManager.setNewLocale(getBaseContext(), LocaleManager.LANGUAGE_KEY_DEUTSCH);

                recreate();
            }
            dialog.dismiss();
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }

    public void onClickChangeLanguage(){
        Button changeLang = findViewById(R.id.btnLanguage);
        changeLang.setOnClickListener(v -> showChangeLanguageDialog());
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    protected void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClickSwitchToPin(View view) {
        Intent intent = new Intent(this, ChangePinActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchToChangeProfile(View view) {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        intent.putExtra("source", "SettingsActivity");
        startActivity(intent);
    }

    public void onClickLogout(View view){
        logout();
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
    }


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
