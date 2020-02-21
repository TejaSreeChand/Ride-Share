package com.sathyabama.finalyear.rideshareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sathyabama.finalyear.rideshareapp.adapter.BookingRecyclerAdapter;
import com.sathyabama.finalyear.rideshareapp.model.RideBookingModel;
import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;

import java.util.ArrayList;


public class DriverMainActivity extends AppCompatActivity {

    private RecyclerView bookingReqRV;
    private ArrayList<RideBookingModel> bookingModelArrayList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        floatingActionButton = findViewById(R.id.driverMainFAB);
        bookingReqRV = findViewById(R.id.driverBookedRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        final PreferenceConfig preferenceConfig = new PreferenceConfig(getApplicationContext());
        logout = findViewById(R.id.logoutDriver);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Driver").child(preferenceConfig.readPhoneNumber()).child("currentBookings");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot booking : dataSnapshot.getChildren()) {
                    bookingModelArrayList.add(booking.getValue(RideBookingModel.class));
                }
                if (bookingModelArrayList.size() != 0) {
                    BookingRecyclerAdapter adapter = new BookingRecyclerAdapter(bookingModelArrayList, getApplicationContext());
                    bookingReqRV.setAdapter(adapter);
                    bookingReqRV.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AvailableRides.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceConfig.writeLoginStatus(false);
                startActivity(new Intent(getApplicationContext(),LoginScreenActivity.class));
            }
        });
    }


}