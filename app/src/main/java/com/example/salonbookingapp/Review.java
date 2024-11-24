package com.example.salonbookingapp;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
        RatingBar customerRating = findViewById(R.id.ratingBar6);

        TextView allCusService = findViewById(R.id.textView8);
        TextView allPrice = findViewById(R.id.textView9);
        TextView allserviceQuality = findViewById(R.id.textView10);
        TextView allEnvironment = findViewById(R.id.textView11);

        TextView username = findViewById(R.id.textView13);

        TextView cusService = findViewById(R.id.textView1);
        TextView price = findViewById(R.id.textView4);
        TextView serviceQuality = findViewById(R.id.textView2);
        TextView environment = findViewById(R.id.textView3);
        TextView serviceName = findViewById(R.id.textView15);
        TextView comment = findViewById(R.id.textView18);

        float sumAll = 0f;
        float sumService = 0f;
        float sumPrice = 0f;
        float sumQuality = 0f;
        float sumEnvironment = 0f;

        int count = 0;

        String file = "review.txt";
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if (words[0].equals(salonName)) {
                    sumAll += Float.parseFloat(words[1]); // Parse as float
                    count++;
                    cusService.setText("Customer Service: " + String.format("%.1f", Float.parseFloat(words[2])));
                    sumService += Float.parseFloat(words[2]); // Parse as float
                    price.setText("Price: " + String.format("%.1f", Float.parseFloat(words[3])));
                    sumPrice += Float.parseFloat(words[3]); // Parse as float
                    serviceQuality.setText("Service Quality: " + String.format("%.1f", Float.parseFloat(words[4])));
                    sumQuality += Float.parseFloat(words[4]); // Parse as float
                    environment.setText("Environment: " + String.format("%.1f", Float.parseFloat(words[5])));
                    sumEnvironment += Float.parseFloat(words[5]); // Parse as float
                    username.setText("name: " + words[6]);
                    serviceName.setText("Service: " + words[8]);
                    comment.setText(words[7]);
                }
            }

            if (count > 0) {
                customerRating.setRating(Float.parseFloat(String.valueOf(sumAll / count)));
                overall1.setText(String.valueOf(sumAll / count));
                allCusService.setText("Customer Service: " + String.valueOf(sumService / count));
                allPrice.setText("Price: " + String.valueOf(sumPrice / count));
                allserviceQuality.setText("Service Quality: " + String.valueOf(sumQuality / count));
                allEnvironment.setText("Environment: " + String.valueOf(sumEnvironment / count));

            } else {
                overall1.setText("No Reviews Found");
            }

            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            overall1.setText("Error loading reviews");
        }
    }
}
