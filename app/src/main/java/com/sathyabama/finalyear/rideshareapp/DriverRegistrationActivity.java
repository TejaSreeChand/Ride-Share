package com.sathyabama.finalyear.rideshareapp;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverRegistrationActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button registerButton;
    EditText etName, etPhone, etEmail, etPassword, etAge, etVehicleNum;
    EditText etMaker, etLic, etAadhar, etNoOfSeats, etGender, etUpi;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    GPSTracker gps;
    double latOfSensor = 0, lonOfSensor = 0;
    String latitude = "", longitude = "";
    final Handler ha = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);
        getSupportActionBar().setTitle("Driver Registration");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etName = findViewById(R.id.et_fullName);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_mobile);
        etAge = findViewById(R.id.et_age);
        etVehicleNum = findViewById(R.id.et_vehicle_no);
        etLic = findViewById(R.id.et_lic);
        etAadhar = findViewById(R.id.et_aadhar_no);
        etMaker = findViewById(R.id.et_vehicle_maker);
        etNoOfSeats = findViewById(R.id.et_no_of_seats);
        etGender = findViewById(R.id.et_gender_driver);
        etUpi = findViewById(R.id.et_upi_id);

        radioGroup = findViewById(R.id.radio);
        registerButton = findViewById(R.id.button_register_driver);

        //getting gps data
        gps = new GPSTracker(DriverRegistrationActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation() && gps.getLatitude() != 0) {

            latOfSensor = gps.getLatitude();
            lonOfSensor = gps.getLongitude();

            //button enable kore dao

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName;
                String email;
                String password;
                String phoneNumber;
                String age;
                String vehicleNumber;
                String maker;
                String acStatus;
                String licNo;
                String aadharNum;
                String noOfSeats;
                String gender;

                fullName = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phoneNumber = etPhone.getText().toString().trim();
                age = etAge.getText().toString().trim();
                vehicleNumber = etVehicleNum.getText().toString().trim();
                maker = etMaker.getText().toString().trim();
                licNo = etLic.getText().toString().trim();
                aadharNum = etAadhar.getText().toString().trim();
                noOfSeats = etNoOfSeats.getText().toString().trim();
                gender = etGender.getText().toString().trim();

                if (latOfSensor == 0) {
                    gps.showSettingsAlert();
                    Toast.makeText(getApplicationContext(), "Error getting location!", Toast.LENGTH_LONG).show();
                    Log.e("Error", "location error1");
                } else {
                    latitude = String.valueOf(latOfSensor);
                    longitude = String.valueOf(lonOfSensor);
                }

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                String vehicleType = radioButton.getText().toString();


                if (fullName.equals("") || password.equals("") || email.equals("") ||
                        phoneNumber.equals("")  ||
                        age.equals("") || vehicleNumber.equals("") ||
                        latitude.equals("") || longitude.equals("")) {

                    if (latitude.equals("") || longitude.equals("")) {
                        Toast.makeText(getApplicationContext(), "Error getting location!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    DatabaseReference myRef = database.getReference("Driver").child(phoneNumber);
                    DriverReg driver = new DriverReg(fullName, email, password, phoneNumber, age,
                            vehicleNumber, maker, "yes", licNo, aadharNum, noOfSeats, gender, etUpi.getText().toString());
                    myRef.setValue(driver);
                    Toast.makeText(DriverRegistrationActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DriverRegistrationActivity.this, LoginScreenActivity.class));
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
        gps = new GPSTracker(DriverRegistrationActivity.this);

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
