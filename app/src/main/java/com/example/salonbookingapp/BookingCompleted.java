package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingCompleted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_completed);

        // Get data from the Intent
        String salon = getIntent().getStringExtra("CUSTOMER_SALON");
        String menu = getIntent().getStringExtra("CUSTOMER_MENU");
        String stylist = getIntent().getStringExtra("CUSTOMER_STYLIST");
        String date = getIntent().getStringExtra("CUSTOMER_DATE");
        String time = getIntent().getStringExtra("CUSTOMER_TIME");

        // Set the data to the TextViews
        TextView salonTextView = findViewById(R.id.booking_salon);
        TextView menuTextView = findViewById(R.id.booking_menu);
        TextView stylistTextView = findViewById(R.id.booking_stylist);
        TextView dateTimeTextView = findViewById(R.id.booking_date_time);

        salonTextView.setText(salon);
        menuTextView.setText(menu);
        stylistTextView.setText(stylist);
        dateTimeTextView.setText(date + "\n" + time);

//        // Mypage Button
//        Button myPageButton = findViewById(R.id.mypage_button);
//        myPageButton.setOnClickListener(v -> {
//            // Move to Mypage (or another relevant activity)
//            Intent intent = new Intent(BookingCompleted.this, MyPage.class);
//            startActivity(intent);
//        });
    }
}
