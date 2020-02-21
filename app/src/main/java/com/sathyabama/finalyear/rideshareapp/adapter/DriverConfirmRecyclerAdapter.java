package com.sathyabama.finalyear.rideshareapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sathyabama.finalyear.rideshareapp.R;
import com.sathyabama.finalyear.rideshareapp.model.RideBookingModel;
import com.sathyabama.finalyear.rideshareapp.model.RideConfirmBookingModel;
import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DriverConfirmRecyclerAdapter extends RecyclerView.Adapter<DriverConfirmRecyclerAdapter.ViewHolder> {
    private ArrayList<RideBookingModel> rideBookingModelArrayList;
    private Context context;

    public DriverConfirmRecyclerAdapter(ArrayList<RideBookingModel> rideBookingModelArrayList, Context context) {
        this.rideBookingModelArrayList = rideBookingModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_confirm_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RideBookingModel currentRide = rideBookingModelArrayList.get(position);
        holder.nameTV.setText(currentRide.getFullName());
        holder.timeTV.setText(currentRide.getTime());
        holder.phoneTV.setText(currentRide.getPhoneNum());
        holder.destTV.setText(getAddress(currentRide.getDestLat(),currentRide.getDestLong()));
        holder.sourceTV.setText(getAddress(currentRide.getCurrentLat(),currentRide.getCurrentLong()));
        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.custom_dialog_box, null);
                final Dialog dialog = new Dialog(view.getContext(), R.style.AppTheme);
                dialog.setContentView(promptsView);
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
                final EditText userInput = ( promptsView).findViewById(R.id.priceET);
                final Button submitPriceButton = promptsView.findViewById(R.id.submitBtn);

                submitPriceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PreferenceConfig preferenceConfig = new PreferenceConfig(context);
                        RideConfirmBookingModel rideConfirmBookingModel =
                                new RideConfirmBookingModel(currentRide.getFullName(),currentRide.getCurrentLat(),currentRide.getCurrentLong(),currentRide.getDestLat(),currentRide.getDestLong(),currentRide.getPhoneNum(),currentRide.getTime(),userInput.getText().toString(),preferenceConfig.readUPIId(),preferenceConfig.readFullName());
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                        databaseReference.child("Driver").child(preferenceConfig.readPhoneNumber())
                                .child("pendingBookings").child(currentRide.getPhoneNum()).setValue(rideConfirmBookingModel);
                        databaseReference.child("Rider").child(currentRide.getPhoneNum())
                                .child("pendingBookings").child(preferenceConfig.readPhoneNumber()).setValue(rideConfirmBookingModel);
                        databaseReference.child("Booking").child(currentRide.getPhoneNum()).removeValue();
                        dialog.dismiss();
                    }
                });
             //   alertDialogBuilder.setView(promptsView);
                //AlertDialog alertDialog = alertDialogBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideBookingModelArrayList==null ? 0: rideBookingModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, phoneTV, sourceTV, destTV, timeTV;
        Button confirmBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTextView);
            phoneTV = itemView.findViewById(R.id.phoneNumTextView);
            sourceTV = itemView.findViewById(R.id.sourceTextView);
            destTV = itemView.findViewById(R.id.destTextView);
            timeTV = itemView.findViewById(R.id.timeTextView);
            confirmBtn = itemView.findViewById(R.id.confirmButton);
        }
    }
    private String getAddress(double latitude, double longitude){

        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addresses.get(0).getAddressLine(0);
    }


}
