package com.sathyabama.finalyear.rideshareapp.adapter;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sathyabama.finalyear.rideshareapp.R;
import com.sathyabama.finalyear.rideshareapp.model.RideBookingModel;
import com.sathyabama.finalyear.rideshareapp.model.RideConfirmBookingModel;
import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RiderAcceptedRecyclerAdapter extends RecyclerView.Adapter<RiderAcceptedRecyclerAdapter.ViewHolder> {
    private ArrayList<RideConfirmBookingModel> rideBookingModelArrayList;
    private Context context;
    private Activity activity;
    public RiderAcceptedRecyclerAdapter(ArrayList<RideConfirmBookingModel> rideBookingModelArrayList, Context context, Activity activity) {
        this.rideBookingModelArrayList = rideBookingModelArrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rider_accepted_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RideConfirmBookingModel currentRide = rideBookingModelArrayList.get(position);
        holder.nameTV.setText(currentRide.getDriverName());
        holder.timeTV.setText(currentRide.getTime());
        holder.phoneTV.setText(currentRide.getPhoneNum());
       holder.destTV.setText(getAddress(currentRide.getDestLat(),currentRide.getDestLong()));
        holder.sourceTV.setText(getAddress(currentRide.getCurrentLat(),currentRide.getCurrentLong()));
        holder.priceTV.setText(currentRide.getPrice());
        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price;
                if(currentRide.getPrice().contains(".")){
                    price = currentRide.getPrice();
                }else {
                    price = currentRide.getPrice()+".00";
                }
                final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                        .with(activity)
                        .setPayeeVpa(currentRide.getUpiID())
                        .setPayeeName(currentRide.getDriverName())
                        .setTransactionId("UNIQUE_TRANSACTION_ID")
                        .setTransactionRefId("UNIQUE_TRANSACTION_REF_ID")
                        .setDescription("RideShare payment")
                        .setAmount(price)
                        .build();
                easyUpiPayment.startPayment();
                easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
                    @Override
                    public void onTransactionCompleted(TransactionDetails transactionDetails) {
                        Toast.makeText(context,"Payment Successful",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTransactionSuccess() {

                    }

                    @Override
                    public void onTransactionSubmitted() {

                    }

                    @Override
                    public void onTransactionFailed() {
                        Toast.makeText(context,"Payment Failed",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTransactionCancelled() {
                        Toast.makeText(context,"Payment Cancelled",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAppNotFound() {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideBookingModelArrayList==null ? 0: rideBookingModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, phoneTV, sourceTV, destTV, timeTV, priceTV;
        Button confirmBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTextView);
            phoneTV = itemView.findViewById(R.id.phoneNumTextView);
            sourceTV = itemView.findViewById(R.id.sourceTextView);
            destTV = itemView.findViewById(R.id.destTextView);
            timeTV = itemView.findViewById(R.id.timeTextView);
            priceTV= itemView.findViewById(R.id.priceTextView);
            confirmBtn = itemView.findViewById(R.id.confirmButton);
        }
    }
    private String getAddress(double latitude, double longitude){

        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addresses.get(0).getAddressLine(0);
    }


}
