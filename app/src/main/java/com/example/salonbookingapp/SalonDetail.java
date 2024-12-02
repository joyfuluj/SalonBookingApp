package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salon_detail);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // "Book now" button's Click event
        Button bookNowButton = findViewById(R.id.button3); // "Book now" button's ID
        bookNowButton.setOnClickListener(v -> {
            // Move to "Coupon Menu" page
            Log.d("Navigation", "Navigating to PickDateTime");
            Toast.makeText(this, "Navigating to PickDateTime", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SalonDetail.this, PickDateTime.class); // 遷移先
            intent.putExtra("MENU", "Coupon A"); // 必要に応じてデータを渡す
            startActivity(intent);
            finish(); // 現在のアクティビティを終了

        });


        Intent intent = getIntent();
        salonName = intent.getStringExtra("salonName");
        username = intent.getStringExtra("username");

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
        intent.putExtra("username", username);
        startActivity(intent);

    }
    public void back(View v){
        finish();
    }
}