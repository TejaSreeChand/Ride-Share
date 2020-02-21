package com.sathyabama.finalyear.rideshareapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;
import com.sathyabama.finalyear.rideshareapp.views.RiderBookingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifsabir on 1/21/18.
 */

public class RiderMainAcitivity extends AppCompatActivity {
    TextView tvRiderName, tvRiderMobile;
    Button btnDriversMap, btnSendReq, emergencyBtn, logoutBtn;

    DatabaseReference databaseReference;


    ProgressDialog progressDialog;

    List<DriverReg> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_main);
        tvRiderName = (TextView) findViewById(R.id.tv_rider_name);
        tvRiderMobile = (TextView) findViewById(R.id.tv_rider_mobile);
       logoutBtn = findViewById(R.id.logout);
        btnSendReq = (Button) findViewById(R.id.button_request_ride);
        //retrieving phone data
        final String riderName = getIntent().getExtras().getString("riderName", null);
        final String riderPhone = getIntent().getExtras().getString("riderPhone", null);
        final PreferenceConfig preferenceConfig = new PreferenceConfig(getApplicationContext());
        emergencyBtn = findViewById(R.id.emer_btn);
        tvRiderName.setText(riderName);
        tvRiderMobile.setText(riderPhone);
        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + preferenceConfig.readEmerNum()));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    ActivityCompat.requestPermissions(RiderMainAcitivity.this,new String[]{Manifest.permission.CALL_PHONE},2);
                    startActivity(intent);
                    return;
                }
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceConfig.writeLoginStatus(false);
                startActivity(new Intent(getApplicationContext(),LoginScreenActivity.class));
            }
        });
        btnSendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RiderMainAcitivity.this, RiderBookingActivity.class);
                i.putExtra("riderName", riderName);
                i.putExtra("riderPhone", riderPhone);
                startActivity(i);
                finish();
            }
        });


        //showing list view

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(RiderMainAcitivity.this));

        progressDialog = new ProgressDialog(RiderMainAcitivity.this);

        progressDialog.setMessage("Loading Data... \n Please wait!");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Driver");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                    DriverReg studentDetails = dataSnapshot1.getValue(DriverReg.class);

                    list.add(studentDetails);
                }


                adapter = new RecyclerViewAdapter(RiderMainAcitivity.this, list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }
}