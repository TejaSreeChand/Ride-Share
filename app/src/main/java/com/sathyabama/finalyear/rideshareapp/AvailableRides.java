package com.sathyabama.finalyear.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sathyabama.finalyear.rideshareapp.adapter.BookingRecyclerAdapter;
import com.sathyabama.finalyear.rideshareapp.adapter.DriverConfirmRecyclerAdapter;
import com.sathyabama.finalyear.rideshareapp.model.RideBookingModel;

import java.util.ArrayList;

public class AvailableRides extends AppCompatActivity {
    private RecyclerView bookingReqRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rides);
        bookingReqRV = findViewById(R.id.driverRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Booking");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<RideBookingModel> bookingModelArrayList = new ArrayList<>();
                for( DataSnapshot booking :dataSnapshot.getChildren()){
                    bookingModelArrayList.add(booking.getValue(RideBookingModel.class));
                }
                if(bookingModelArrayList.size()!=0){
                    DriverConfirmRecyclerAdapter adapter = new DriverConfirmRecyclerAdapter(bookingModelArrayList,getApplicationContext());
                    bookingReqRV.setAdapter(adapter);
                    bookingReqRV.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
