package com.example.salonbookingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinner1 = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng kelowna = new LatLng(49.8801, -119.4436);
        LatLng ubco = new LatLng(49.9394, -119.3948);
        LatLng lakeCountry = new LatLng(50.0537, -119.4106);
        googleMap.addMarker(new MarkerOptions()
                .position(kelowna)
                .title("Kelowna"));

        googleMap.addMarker(new MarkerOptions()
                .position(ubco)
                .title("UBCO"));

        googleMap.addMarker(new MarkerOptions()
                .position(lakeCountry)
                .title("Lake Country"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubco, 10));

        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
