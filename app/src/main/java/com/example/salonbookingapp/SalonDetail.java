package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class SalonDetail extends AppCompatActivity {
    String salonIntro;
    String salonName;
    String username;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salon_detail);

        // Initialize views after setting content
        image = findViewById(R.id.imageView5);

        // Get intent data
        Intent intent = getIntent();
        salonName = intent.getStringExtra("salonName");
        username = intent.getStringExtra("username");

        String file = "salon.txt";

        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            String imageName = "";
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if (words[0].equals(salonName)) {
                    imageName = words[4];
                    break;
                }
            }
            br.close();

            int resId = getResources().getIdentifier(imageName.replace(".jpeg", ""), "drawable", getPackageName());
            if (resId != 0) {
                image.setImageResource(resId);
            } else {
                // Set a default image if the resource was not found
                image.setImageResource(R.drawable.default_salon);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button bookNowButton = findViewById(R.id.button3);
        bookNowButton.setOnClickListener(v -> {
            Intent bookIntent = new Intent(SalonDetail.this, CouponMenu.class);
            bookIntent.putExtra("salonName", salonName);
            bookIntent.putExtra("username", username);
            startActivity(bookIntent);
        });

        TextView salon = findViewById(R.id.textView14);
        salon.setText(salonName);
        TextView intro = findViewById(R.id.textView16);

        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if (words[0].equals(salonName)) {
                    salonIntro = words[2];
                    intro.setText(salonIntro);
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickReview(View view) {
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("salonName", salonName);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void back(View v) {
        finish();
    }
}
