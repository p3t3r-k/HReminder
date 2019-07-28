package com.example.hreminder.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hreminder.R;
import com.example.hreminder.behindTheScenes.BaseActivity;
import com.example.hreminder.behindTheScenes.PlaceAutocompleteAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.graphics.Color.parseColor;

/**
 * Activity to display embedded GoogleMaps
 */
public class DoctorsMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = false;

    private AutoCompleteTextView mSearchText;
    private ImageView mGps;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    /**
     * set Actionbar
     * ask for permission to use GPS sensor
     *
     * @param savedInstanceState saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_map);
        ActionBar abar = getSupportActionBar();
        Objects.requireNonNull(abar).setBackgroundDrawable(new ColorDrawable(parseColor("#a4c639")));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mSearchText = findViewById(R.id.input_search);
        mGps = findViewById(R.id.ic_gps);

        getLocationPermission();
    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        getLocationPermission();
        initMap();
    }

    /**
     * starts the map if permissions are granted
     *
     * @param googleMap Google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    /**
     * method that initialises the map
     */
    @SuppressWarnings("deprecation")
    private void init() {

        GoogleApiClient mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlaceAutocompleteAdapter mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                geoLocate();
            }

            return false;
        });

        mGps.setOnClickListener(view -> getDeviceLocation());

        hideSoftKeyboard();
    }

    /**
     * method with which you can search for specific locations
     */
    private void geoLocate() {

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(DoctorsMapActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 10);
        } catch (IOException e) {
            Toast.makeText(this, "Location not found",
                    Toast.LENGTH_LONG).show();
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),
                    address.getAddressLine(0));

        }
    }

    /**
     * method which gets coordinates of the device location with FusedLocationProviderClient
     */
    private void getDeviceLocation() {

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                //noinspection unchecked
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();

                        if (currentLocation != null) {
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    "My Location");
                        }
                    } else {
                        Toast.makeText(DoctorsMapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * moves the camera on the map to the devices location
     *
     * @param latLng coordinates of the device
     * @param title  name of the location the device is in
     */
    private void moveCamera(LatLng latLng, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DoctorsMapActivity.DEFAULT_ZOOM));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    /**
     * method that start the map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(DoctorsMapActivity.this);
        }
    }

    /**
     * method that requests permission to use GPS sensor
     */
    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * result of permission request to use location services
     *
     * @param requestCode  Code which requests permission
     * @param permissions  gets the permission to use GPS sensor
     * @param grantResults result if permission is granted or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        return;
                    }
                }
                mLocationPermissionsGranted = true;
                //initialize our map
                initMap();
            }
        }
    }

    /**
     * hide Keyboard when input has been made
     */
    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * method to build actionbar
     *
     * @param menu menu
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinfl = getMenuInflater();
        menuinfl.inflate(R.menu.actionbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onClick on different MenuItems go to Activities
     *
     * @param item MenuItem of Actionbar
     * @return boolean true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                gotToSettings();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Help icon is selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_home:
                goToCalendar();
            case android.R.id.home:
                goToCalendar();
                return true;
            case R.id.action_appointments:
                goToAppointments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * go to CalenderActivity
     */
    private void goToCalendar() {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    /**
     * go to SettingsActivity
     */
    private void gotToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * go to LastAppointmentsActivity
     */
    private void goToAppointments() {
        Intent intent = new Intent(this, LastAppointmentsActivity.class);
        intent.putExtra("source", "DoctorsMapActivity");
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
