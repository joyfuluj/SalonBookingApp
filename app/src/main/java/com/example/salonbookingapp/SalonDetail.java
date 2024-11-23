package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SalonDetail extends AppCompatActivity {
    String salonIntro;
    String salonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salon_detail);
        salonName = getIntent().getStringExtra("salonName");

        TextView salon = findViewById(R.id.textView14);
        salon.setText(salonName);
        TextView intro = findViewById(R.id.textView16);


        String file = "salon.txt";
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if(words[0].equals(salonName)){
                    salonIntro = words[2];
                    intro.setText(salonIntro);
                }
            }
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickReview(View view){
        Intent intent = new Intent(this,Review.class);
        intent.putExtra("salonName", salonName);
        startActivity(intent);

    }
}