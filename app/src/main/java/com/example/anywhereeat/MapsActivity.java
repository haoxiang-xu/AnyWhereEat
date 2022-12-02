package com.example.anywhereeat;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.anywhereeat.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng kelowna = new LatLng(49.940147, -119.396516);
        mMap.addMarker(new MarkerOptions().position(kelowna).title("Kelowna"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kelowna));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    public void showStatus(View view) {
        cv = (CardView) findViewById(R.id.statusDisplay);
        cv.setVisibility(View.VISIBLE);
    }

    public void closeStatus(View view) {
        cv = (CardView) findViewById(R.id.statusDisplay);
        cv.setVisibility(View.INVISIBLE);
    }

    public void customerService(View view) {
        Intent customerService = new Intent(this, CustomerServiceActivity.class);
        startActivity(customerService);
    }

    public void backButton(View view) {
        Intent backIntent = new Intent(this, HomeActivity.class);
        startActivity(backIntent);
    }
}