package com.example.salonbookingapp;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Review extends AppCompatActivity {
    String salonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review);
        salonName = getIntent().getStringExtra("salonName");

        TextView salon = findViewById(R.id.textView7);
        salon.setText(salonName);
        TextView overall1 = findViewById(R.id.textView6);
        TextView overall2 = findViewById(R.id.textView5);
        RatingBar customerRating = findViewById(R.id.ratingBar6);

        TextView allCusService = findViewById(R.id.textView8);
        TextView allPrice = findViewById(R.id.textView9);
        TextView allserviceQuality = findViewById(R.id.textView10);
        TextView allEnvironment = findViewById(R.id.textView11);

        String username;

        TextView cusService = findViewById(R.id.textView1);
        TextView price = findViewById(R.id.textView3);
        TextView serviceQuality = findViewById(R.id.textView2);
        TextView environment = findViewById(R.id.textView4);

        String file = "salon.txt";
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if(words[0].equals(salonName)) {
                    overall1.setText(words[5]);
                    overall2.setText(words[5]);
                    customerRating.setRating(Float.parseFloat(words[5]));
                }
            }
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}