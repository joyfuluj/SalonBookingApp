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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

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
        String file = "salon.txt";
        FileOutputStream outputStream;

        try {
            // Write initial data to "review.txt"
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

            String line;
            float sumAll = 0f;
            int count = 0;
            // Read "review.txt" to calculate sum and count for the specified salon
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if (words[0].equals(salonName)) {
                    sumAll += Float.parseFloat(words[1]);
                    count++;
                }
            }
            br.close();
            fis.close();
            // Read and modify "salon.txt"
            fis = openFileInput(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String location = "";
            String intro = "";
            String service = "";
            String image = "";
            StringBuilder fileContent = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                // Update the specific line for the salon
                if (words[0].equals(salonName)) {
                    location = words[1];  // Assuming locations, intro, etc., are in specific columns
                    intro = words[2];
                    service = words[3];
                    image = words[4];
                    String change = salonName + "," + location + "," + intro + "," + service + "," + image + "," +
                            String.format("%.1f", sumAll / count);
                    fileContent.append(change).append("\n");
                } else {
                    // Retain original lines
                    fileContent.append(line).append("\n");
                }
            }
            br.close();
            fis.close();
            // Write the updated content back to "salon.txt"
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE); // Overwrite existing file content
            fos.write(fileContent.toString().getBytes());
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, Search.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    public void back(View v){
        finish();
    }
}