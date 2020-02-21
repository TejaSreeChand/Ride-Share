package com.sathyabama.finalyear.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sathyabama.finalyear.rideshareapp.adapter.RiderAcceptedRecyclerAdapter;
import com.sathyabama.finalyear.rideshareapp.model.RideConfirmBookingModel;
import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;

import java.util.ArrayList;

public class RiderAcceptedRides extends AppCompatActivity {
    RecyclerView acceptedRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_accepted_rides);
        acceptedRV = findViewById(R.id.acceptedRV);
        PreferenceConfig preferenceConfig = new PreferenceConfig(getApplicationContext());
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Rider").child(preferenceConfig.readPhoneNumber()).child("pendingBookings");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<RideConfirmBookingModel> rideConfirmBooking = new ArrayList<>();
                for(DataSnapshot rides : dataSnapshot.getChildren()){
                    rideConfirmBooking.add(rides.getValue(RideConfirmBookingModel.class));
                }

                System.out.println("Checcccccck" +rideConfirmBooking.size());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                RiderAcceptedRecyclerAdapter adapter = new RiderAcceptedRecyclerAdapter(rideConfirmBooking,getApplicationContext(),RiderAcceptedRides.this);
                acceptedRV.setAdapter(adapter);
                acceptedRV.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
