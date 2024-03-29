package com.sathyabama.finalyear.rideshareapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;



public class LoginScreenActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    ProgressBar pbLoading;
    EditText etPhone, etPassword;
    private Button loginBtn, riderRegBtn, driverRegBtn;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        riderRegBtn =  findViewById(R.id.button_rider_reg);
        driverRegBtn =  findViewById(R.id.button_driver_reg);
        etPhone = (EditText) findViewById(R.id.et_phone_login);
        etPassword = (EditText) findViewById(R.id.et_password_login);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        PreferenceConfig preferenceConfig = new PreferenceConfig(getApplicationContext());
        if(preferenceConfig.readLoginStatus()){
            if(preferenceConfig.isRider()){
                Intent i = new Intent(LoginScreenActivity.this, RiderMainAcitivity.class);
                i.putExtra("riderName", preferenceConfig.readFullName());
                i.putExtra("riderPhone", preferenceConfig.readPhoneNumber());
                startActivity(i);
                finish();
            }else{
                Intent i = new Intent(LoginScreenActivity.this, DriverMainActivity.class);
                i.putExtra("driverPhone", preferenceConfig.readPhoneNumber());
                startActivity(i);
                finish();
            }
        }

        if (Build.VERSION.SDK_INT >= 23) {
            permissionCheck();
        }

        addListenerOnButton();

        riderRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RiderRegistrationActivity.class));
            }
        });
        driverRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DriverRegistrationActivity.class));
            }
        });
    }


    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radio);
        loginBtn = (Button) findViewById(R.id.button_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                String radioText = radioButton.getText().toString();
                loginToDb(etPhone.getText().toString(), etPassword.getText().toString(), radioText);

                //viewing loading bar
                pbLoading.setVisibility(View.VISIBLE);

            }

        });

    }

    void permissionCheck() {
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(LoginScreenActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }


    public void loginToDb(final String phone, final String password, final String user) {
        //checking whether the user is registered or not, if then send to MainActivity
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference(user).child(phone);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                PreferenceConfig preferenceConfig = new PreferenceConfig(getApplicationContext());
                if (user.equals("Rider")) {
                    RiderReg riderReg = snapshot.getValue(RiderReg.class);

                    if (riderReg.password.trim().equals(password)) {
                        Toast.makeText(LoginScreenActivity.this, "Welcome rider:" + riderReg.fullName, Toast.LENGTH_SHORT).show();

                        //sending data to rider activity

                        Intent i = new Intent(LoginScreenActivity.this, RiderMainAcitivity.class);
                        preferenceConfig.writeLoginStatus(true);
                        preferenceConfig.writePhoneNumber(riderReg.mobile);
                        preferenceConfig.writeFullName(riderReg.fullName);
                        preferenceConfig.writeEmerNumb(riderReg.emergencyNumber);
                        preferenceConfig.writeIsRider(true);
                        i.putExtra("riderName", riderReg.fullName);
                        i.putExtra("riderPhone", riderReg.mobile);
                        startActivity(i);
                        finish();

                    }


                } else {
                    DriverReg driverReg = snapshot.getValue(DriverReg.class);

                    if (driverReg.password.trim().equals(password)) {
                        Toast.makeText(LoginScreenActivity.this, "Welcome driver:" + driverReg.fullName, Toast.LENGTH_SHORT).show();

                        //sending data to driver activity
                        Intent i = new Intent(LoginScreenActivity.this, DriverMainActivity.class);
                        String driverPhone = driverReg.mobile;
                        preferenceConfig.writeLoginStatus(true);
                        preferenceConfig.writePhoneNumber(driverReg.mobile);
                        preferenceConfig.writeFullName(driverReg.fullName);
                        preferenceConfig.writeIsRider(false);
                        preferenceConfig.writeUPIId(driverReg.upiId);
                        i.putExtra("driverPhone", driverPhone);
                        startActivity(i);
                        finish();

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginScreenActivity.this, "Error happened in fetching user data!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}