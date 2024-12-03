package com.example.salonbookingapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    Spinner spinner1;
    String username;
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

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        String file = "salon.txt";
        StringBuilder data = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                LatLng position = getPosition(words[1]);
                googleMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(words[0]));
            }
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LatLng def = new LatLng(49.9394, -119.3948);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(def, 11));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
         googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
             @Override
             public boolean onMarkerClick(Marker marker) {
                 showMarkerDialog(marker.getTitle());
                 return true;
             }
         });
    }

    private void showMarkerDialog(String markerTitle){
            AlertDialog.Builder builder = new AlertDialog.Builder(Map.this);
            builder.setTitle("Select Marker: " + markerTitle)
                    .setMessage("Do you want to view more details about this location?")
                    .setPositiveButton("View Details", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Launch the Detail Activity
                            Intent intent = new Intent(Map.this, SalonDetail.class);
                            intent.putExtra("salonName", markerTitle);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
    }
    GoogleMap googleMap;
    public void onClick(View view) {
        if (spinner1 != null) {
            String selectedLocation = spinner1.getSelectedItem().toString();
            LatLng targetLocation = getPosition(selectedLocation);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, 13)); // Zoom level 15
        } else {
            Toast.makeText(getApplicationContext(),
                    "Spinner not initialized",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public LatLng getPosition(String locationName) {
        LatLng pos = null;
        switch (locationName) {
            case "Orchard Park":
                pos = new LatLng(49.8801, -119.4436);
                break;
            case "UBCO":
                pos = new LatLng(49.9394, -119.3948);
                break;
            case "Rutland":
                pos = new LatLng(49.889334, -119.391872);
                break;
            case "Downtown":
                pos = new LatLng(49.886923, -119.495192);
                break;
            case "Glenmore":
                pos = new LatLng(49.897194, -119.454342);
                break;
            case "Lake Country":
                pos = new LatLng(50.0190, -119.4130);
                break;
            case "Mission":
                pos = new LatLng(49.8571, -119.4876);
                break;
            default:
                pos = new LatLng(0, 0);
                break;
        }
        return pos;
    }
    public void back(View v){
        finish();
    }
}