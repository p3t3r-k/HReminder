package com.example.hreminder.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;

import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.LastUser;
import com.example.hreminder.database.DbHelper;
import com.example.hreminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.graphics.Color.parseColor;

public class LastAppointmentsActivity extends BaseActivity {

    private String callingActivity = "";
    //private String loggedUserID;
    private DbHelper db;
    private Calendar myCalendar;
    private EditText dateEdit;
    private String selectedPhysician;
    private String selectedDate;
    private TableLayout tableLayout;
    private TableRow row;

    private String iDUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_appointments);

        //init Spinner
        Spinner spinner = findViewById(R.id.spinnerDoc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.doctorsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //init ActionBar
        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //get IntentExtras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            callingActivity = extras.getString("source");
            // loggedUserID = extras.getString("idUser");
        }

        db = new DbHelper(this);
        buildDatePickerDialog();


    }

    @Override
    protected void onStart() {
        super.onStart();
        iDUser = LastUser.getLastUserID();

        if (db.checkIfAppointmentsExistByID(iDUser)) {
            rebuildTableLayout();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    private void rebuildTableLayout() {
        Cursor cursorDoc = db.getDocByID(LastUser.getLastUserID());
        Cursor cursorDate = db.getDateByID(LastUser.getLastUserID());

        cursorDoc.moveToFirst();
        cursorDate.moveToFirst();
        for (int i = 1; i <= cursorDoc.getCount(); i++) {

            selectedPhysician = cursorDoc.getString(0);
            selectedDate = cursorDate.getString(0);

            addRow();
            if (cursorDoc.moveToNext()) {
                cursorDate.moveToNext();
            } else {
                break;
            }
        }
    }


    private void buildDatePickerDialog() {
        myCalendar = Calendar.getInstance();

        myCalendar.set(2019, 1, 1);
        dateEdit = findViewById(R.id.dateEditlastApp);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        dateEdit.setOnClickListener(v -> new DatePickerDialog(LastAppointmentsActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateLabel() {
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);
        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }


    public void onClickSwitchToCalenderAct(View view) {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    public void onClickAddAppointment(View view) {
        if (getData()) {
            db.addAppointment(iDUser, selectedPhysician, selectedDate);

            addRow();
        }
    }

    private boolean getData() {
        Spinner spinnerDocs = findViewById(R.id.spinnerDoc);
        selectedPhysician = spinnerDocs.getSelectedItem().toString();
        if (dateEdit.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LastAppointmentsActivity.this);
            builder.setMessage(R.string.inputError);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        } else {
            selectedDate = dateEdit.getText().toString();
            return true;
        }
    }

    private void addRow() {
        tableLayout = findViewById(R.id.tableLayoutLastApp);
        row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

        row.setLayoutParams(lp);

        TextView physician = new TextView(this);
        TextView date = new TextView(this);
        TextView space = new TextView(this);
        Button deleteRowButton = new Button(this);

        physician.setText(selectedPhysician);
        physician.setBackgroundResource(R.drawable.border);
        physician.setGravity(Gravity.CENTER);
        physician.setPadding(30, 0, 30, 0);
        physician.setLayoutParams(lp);
        date.setText(selectedDate);
        date.setBackgroundResource(R.drawable.border);
        date.setGravity(Gravity.CENTER);
        date.setPadding(30, 0, 30, 0);
        date.setLayoutParams(lp);
        space.setText("       ");
        space.setPadding(5, 0, 5, 0);
        space.setLayoutParams(lp);
        deleteRowButton.setText(R.string.deleteApp);
        deleteRowButton.setClickable(true);
        TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        deleteRowButton.setLayoutParams(lpButton);


        int rowCount = tableLayout.getChildCount();
        if (rowCount == 0) {
            row.setTag(rowCount);
        } else {
            row.setTag(rowCount + 1);
        }

        row.addView(physician);
        row.addView(date);
        row.addView(space);
        row.addView(deleteRowButton);
        tableLayout.addView(row);


        deleteRowButton.setOnClickListener(v -> {
            row = (TableRow) v.getParent();
            int rowID = (Integer) row.getTag();


            String txVDoc;
            String txVDate;
            View vDoc = row.getChildAt(0);
            txVDoc = ((TextView) vDoc).getText().toString();

            View vDate = row.getChildAt(1);
            txVDate = ((TextView) vDate).getText().toString();

            if (db.deleteAppByID(iDUser, txVDoc, txVDate)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.appointDeleted) + txVDoc + " " + txVDate, Toast.LENGTH_SHORT).show();
                tableLayout.removeView(row);
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            System.out.println(callingActivity);
            if (callingActivity.equals("SettingsActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, SettingsActivity.class));
            }
            if (callingActivity.equals("DoctorsMapActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, DoctorsMapActivity.class));
            }
            if (callingActivity.equals("ChangeProfileActivity")) {
                NavUtils.navigateUpTo(this, new Intent(this, ChangeProfileActivity.class));
            } else {
                //zu Calender zurück
                NavUtils.navigateUpTo(this, new Intent(this, CalenderActivity.class));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
