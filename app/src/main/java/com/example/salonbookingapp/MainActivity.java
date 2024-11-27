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
        if(user.equals("staff")){
            Intent intent = new Intent(this, MyPageforStaff.class);
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
        int[] rawResources = {R.raw.review, R.raw.booking, R.raw.salon, R.raw.user, R.raw.stylist}; // Raw resource IDs
        String[] fileNames = {"review.txt", "booking.txt", "salon.txt", "user.txt", "stylist.txt"}; // Internal file names

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