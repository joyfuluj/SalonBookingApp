package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        copyFilesFromRawToInternalStorage();
    }

    public void onClick (View view){
        EditText username = findViewById(R.id.username);
        String user = username.getText().toString().trim();
        if (user != null && user.toLowerCase().contains("stylist")) {
            Intent intent = new Intent(this, MyPageforStaff.class);
            intent.putExtra("username", user);
            startActivity(intent);
        }
        else if(user.isEmpty()){
            Toast.makeText(this, "Please enter your username.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, Search.class);
            intent.putExtra("username", user);
            startActivity(intent);

        }
    }

    private void copyFilesFromRawToInternalStorage() {

        int[] rawResources = {R.raw.review, R.raw.booking, R.raw.salon, R.raw.user, R.raw.stylist, R.raw.schedule2, R.raw.schedule1, R.raw.schedule3, R.raw.days_schedule1, R.raw.days_schedule2, R.raw.days_schedule3, R.raw.reservations}; // Raw resource IDs
        String[] fileNames = {"review.txt", "booking.txt", "salon.txt", "user.txt", "stylist.txt", "schedule2.txt", "schedule1.txt", "schedule3.txt", "days_schedule1.txt", "days_schedule2.txt", "days_schedule3.txt", "reservations.txt"}; // Internal file names


        File internalDir = getFilesDir();

        for (int i = 0; i < rawResources.length; i++) {
            File targetFile = new File(internalDir, fileNames[i]);

            if (!targetFile.exists()) {
                try (InputStream inputStream = getResources().openRawResource(rawResources[i]);
                     FileOutputStream outputStream = new FileOutputStream(targetFile)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                } catch (Exception e) {
                    Log.e("FileCopyError", "Error copying file", e);
                }
            }
        }
    }
}