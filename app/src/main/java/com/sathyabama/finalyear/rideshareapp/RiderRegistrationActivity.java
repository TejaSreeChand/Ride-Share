package com.sathyabama.finalyear.rideshareapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RiderRegistrationActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button registerButton;
    EditText etName, etPhone, etPassword, etEmail, etAadhar, etGender, etEmergency;

    GPSTracker gps;
    double latOfSensor = 0, lonOfSensor = 0;
    String latitude = "", longitude = "";
    final Handler ha = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_registration);
        getSupportActionBar().setTitle("Rider Registration");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = findViewById(R.id.et_fullName);
        etPassword = findViewById(R.id.et_password_rider);
        etEmail = findViewById(R.id.et_email_rider);
        etPhone = findViewById(R.id.et_mobile);
        etAadhar = findViewById(R.id.et_aadhar_no);
        etEmergency = findViewById(R.id.et_emergency_no);
        etGender = findViewById(R.id.et_gender_rider);
        registerButton = findViewById(R.id.button_register_rider);

        //getting gps data
        gps = new GPSTracker(RiderRegistrationActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation() && gps.getLatitude() != 0) {

            latOfSensor = gps.getLatitude();
            lonOfSensor = gps.getLongitude();


        } else {
            gps.showSettingsAlert();
        }


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName, password, phoneNumber, aadhar, email, gender, emergencyNumber;

                fullName = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                phoneNumber = etPhone.getText().toString().trim();
                gender = etPhone.getText().toString().trim();
                aadhar = etAadhar.getText().toString().trim();
                emergencyNumber = etEmergency.getText().toString();
                email = etEmail.getText().toString().trim();
                if (latOfSensor == 0) {
                    gps.showSettingsAlert();
                    Toast.makeText(getApplicationContext(), "Error getting location!", Toast.LENGTH_LONG).show();
                    Log.e("Error", "location error1");
                } else {
                    latitude = String.valueOf(latOfSensor);
                    longitude = String.valueOf(lonOfSensor);
                }


                if (fullName.equals("") || password.equals("") ||
                        phoneNumber.equals("") || gender.equals("") |
                        latitude.equals("") || longitude.equals("")) {

                    if (latitude.equals("") || longitude.equals("")) {
                        Toast.makeText(getApplicationContext(), "Error getting location!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    DatabaseReference myRef = database.getReference("Rider").child(phoneNumber);
                    RiderReg rider = new RiderReg(fullName, password, phoneNumber, email,
                            gender, aadhar, emergencyNumber);
                    myRef.setValue(rider);
                    Toast.makeText(RiderRegistrationActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RiderRegistrationActivity.this, LoginScreenActivity.class));
                    finish();

                }
            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        locationThread();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(this, LoginScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(intent, 0);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void locationThread() {
        gps = new GPSTracker(RiderRegistrationActivity.this);

        ha.postDelayed(new Runnable() {

            @SuppressLint("NewApi")
            @Override
            public void run() {
                //call function
                if (gps.getLocation() == null) {
                    gps.showSettingsAlert();
                } else {

                    // check if GPS enabled
                    if (gps.canGetLocation() && gps.getLatitude() != 0) {
                        latOfSensor = gps.getLatitude();
                        lonOfSensor = gps.getLongitude();
                        Toast.makeText(getApplicationContext(), "Location found!", Toast.LENGTH_SHORT).show();

                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                    if (gps.getLatitude() == 0)
                        ha.postDelayed(this, 3000);
                }
            }

        }, 3000);

    }
}
