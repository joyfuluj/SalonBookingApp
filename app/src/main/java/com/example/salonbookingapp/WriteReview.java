package com.example.salonbookingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class WriteReview extends AppCompatActivity {
    TextView salon;
    EditText service;
    RatingBar ratingCus;
    RatingBar ratingEnv;
    RatingBar ratingQuality;
    RatingBar ratingPrice;
    RatingBar ratingAll;
    EditText comment;
    String salonName;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_review);

        Intent intent = getIntent();
        salonName = intent.getStringExtra("salonName");
        username = intent.getStringExtra("username");

        salon = findViewById(R.id.textView6);
        salon.setText(salonName);


    }
    String fileContents;
    public void storeDataInFile(View view){

        service = findViewById(R.id.editTextText);
        String serviceName = service.getText().toString();
        ratingCus = findViewById(R.id.ratingBar);
        float rating = ratingCus.getRating();
        String formattedRatingCus = String.format("%.1f", rating);
        ratingEnv = findViewById(R.id.ratingBar4);
        float environment = ratingEnv.getRating();
        String formattedRatingEnv = String.format("%.1f", environment);
        ratingQuality = findViewById(R.id.ratingBar3);
        float quality = ratingQuality.getRating();
        String formattedRatingQuality = String.format("%.1f", quality);
        ratingPrice = findViewById(R.id.ratingBar5);
        float price = ratingPrice.getRating();
        String formattedRatingPrice = String.format("%.1f", price);
        ratingAll = findViewById(R.id.ratingBar2);
        float overAll = ratingAll.getRating();
        String formattedRatingAll = String.format("%.1f", overAll);
        comment = findViewById(R.id.editTextText4);
        String userComment = comment.getText().toString();



        if (rating == 0.0f) {
            Toast.makeText(this, "Please rate the customer.", Toast.LENGTH_SHORT).show();
        } else if (environment == 0.0f) {
            Toast.makeText(this, "Please rate the environment.", Toast.LENGTH_SHORT).show();
        } else if (quality == 0.0f) {
            Toast.makeText(this, "Please rate the service quality.", Toast.LENGTH_SHORT).show();
        } else if (price == 0.0f) {
            Toast.makeText(this, "Please rate the price.", Toast.LENGTH_SHORT).show();
        } else if (overAll == 0.0f) {
            Toast.makeText(this, "Please provide an overall rating.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Thank you for your ratings!", Toast.LENGTH_SHORT).show();

            fileContents = salonName + "," + formattedRatingAll + "," + formattedRatingCus + "," + formattedRatingPrice + "," + formattedRatingQuality + "," + formattedRatingEnv + "," + username + "," + userComment + "," + serviceName + "\n";
        }

        String filename = "review.txt";
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("salonName", salonName);
        startActivity(intent);
    }
    public void back(View v){
        finish();
    }
}