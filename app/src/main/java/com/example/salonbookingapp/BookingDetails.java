package com.example.salonbookingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_details);

        Intent intent = getIntent();
        String day = intent.getStringExtra("date");
        String year = intent.getStringExtra("year");
        String time = intent.getStringExtra("time");
        TextView date = findViewById(R.id.day);
        TextView timeView = findViewById(R.id.time);
        date.setText(year+"/"+day);
        timeView.setText(time);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void back(View v){
        finish();
    }
    public void delete(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure you want to delete this booking?");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = getIntent();
                    String day = intent.getStringExtra("date");
                    String time = intent.getStringExtra("time");
                    String fileT = intent.getStringExtra("fileT");

                    FileInputStream fis2 = openFileInput(fileT);
                    InputStreamReader isr2 = new InputStreamReader(fis2);
                    BufferedReader br2 = new BufferedReader(isr2);

                    StringBuilder updatedContent = new StringBuilder();
                    String line2;
                    while ((line2 = br2.readLine()) != null) {
                        String[] words2 = line2.split(",\\s*");
                        if (words2[1].equals(time) && words2[0].equals(day)) {
                            updatedContent.append(day).append(",").append(time).append(",").append(words2[2]).append(",").append("0").append(",").append(words2[4]).append("\n");
                        } else {
                            updatedContent.append(line2).append("\n");
                        }
                    }
                    br2.close();
                    fis2.close();

                    FileOutputStream fos = openFileOutput(fileT, Context.MODE_PRIVATE);
                    fos.write(updatedContent.toString().getBytes());
                    fos.close();

                    Toast.makeText(BookingDetails.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                    recreate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}