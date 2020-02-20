//package com.sathyabama.finalyear.rideshareapp.map;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.sathyabama.finalyear.rideshareapp.R;
//import com.sathyabama.finalyear.rideshareapp.model.RideBookingModel;
//import com.sathyabama.finalyear.rideshareapp.utils.PreferenceConfig;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//
//public class SelectToMapActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
//    private GoogleMap mMap;
//    private MarkerOptions place1, place2;
//    Button getDirection;
//    private Polyline currentPolyline;
//    MapView mMapView;
//    private GoogleMap gMap;
//    private LocationManager locationManager;
//    private SupportMapFragment mapFragment;
//    private static final long MIN_TIME = 200;
//    private static final float MIN_DISTANCE = 100;
//    private String latitude, longitude;
//    private PreferenceConfig preferenceConfig;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map_activity);
//        getDirection = findViewById(R.id.btnGetDirection);
//        mMapView = findViewById(R.id.mapNearBy);
//        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
////        googleMap = mapFragment.get();
//        mMapView.getMapAsync(this);
//        preferenceConfig = new PreferenceConfig(getApplicationContext());
//        mMapView.onCreate(savedInstanceState);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, SelectToMapActivity.this);
//        mMapView.onResume();
//
//        getDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (latitude != null && longitude != null) {
//                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                    final DatabaseReference databaseReference = firebaseDatabase.getReference();
//                    final DatabaseReference bookingRef = databaseReference.child("Booking");
//                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//                    LocalDateTime now = LocalDateTime.now();
//                    RideBookingModel rideBookingModel = new RideBookingModel(preferenceConfig.readFullName(), latitude, longitude, latitude, longitude, preferenceConfig.readPhoneNumber(), dtf.format(now));
//                    bookingRef.child("bookings").setValue(rideBookingModel);
//                }
//            }
//        });
//
//
//    }
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title(location.getLatitude() + " : " + location.getLongitude());
//        latitude = String.valueOf(location.getLatitude());
//        longitude = String.valueOf(location.getLongitude());
//        gMap.clear();
//        gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        gMap.addMarker(markerOptions);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 70);
//        gMap.animateCamera(cameraUpdate);
//        locationManager.removeUpdates(this);
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        gMap = googleMap;
//
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                latitude = String.valueOf(latLng.latitude);
//                longitude = String.valueOf(latLng.longitude);
//                gMap.clear();
//                gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                gMap.addMarker(markerOptions);
//            }
//        });
//    }
//}