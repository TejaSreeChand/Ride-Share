package com.sathyabama.finalyear.rideshareapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sathyabama.finalyear.rideshareapp.R;

public class PreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PreferenceConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }
    public void writePhoneNumber(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_phone_number), phone);
        editor.commit();
    }
    public String readPhoneNumber() {
        return sharedPreferences.getString(context.getString(R.string.pref_phone_number),context.getString(R.string.pref_phone_number));
    }
    public void writeFullName(String fullName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_full_name), fullName);
        editor.commit();
    }
    public String readFullName() {
        return sharedPreferences.getString(context.getString(R.string.pref_full_name),context.getString(R.string.pref_full_name));
    }
    public void writeUPIId(String upiID){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_upi_id), upiID);
        editor.commit();
    }
    public String readUPIId() {
        return sharedPreferences.getString(context.getString(R.string.pref_upi_id),context.getString(R.string.pref_upi_id));
    }
    public void writeEmerNumb(String num){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_emer_numb), num);
        editor.commit();
    }
    public String readEmerNum() {
        return sharedPreferences.getString(context.getString(R.string.pref_emer_numb),context.getString(R.string.pref_emer_numb));
    }
    public void writeIsRider(Boolean isRider){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_rider), isRider);
        editor.commit();
    }
    public  boolean isRider(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_rider),false);
    }

}
