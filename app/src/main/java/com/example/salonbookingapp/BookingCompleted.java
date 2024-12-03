package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingCompleted extends AppCompatActivity {

    private TextView salonTextView, menuTextView, stylistTextView, dateTimeTextView;
    private Button myPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_completed);

        // Initialize TextViews and Button
        salonTextView = findViewById(R.id.booking_salon);
        menuTextView = findViewById(R.id.booking_menu);
        stylistTextView = findViewById(R.id.booking_stylist);
        dateTimeTextView = findViewById(R.id.booking_date_time);
        myPageButton = findViewById(R.id.mypage_button);

        // Get data from the Intent using Constants
        Intent intent = getIntent();
        String salon = intent.getStringExtra(Constants.EXTRA_BOOKING_SALON);
        String menu = intent.getStringExtra(Constants.EXTRA_BOOKING_MENU);
        String stylist = intent.getStringExtra(Constants.EXTRA_BOOKING_STYLIST);
        String date = intent.getStringExtra(Constants.EXTRA_BOOKING_DATE);
        String time = intent.getStringExtra(Constants.EXTRA_BOOKING_TIME);

        // Set the data to the TextViews
        salonTextView.setText(salon != null ? salon : "No salon");
        menuTextView.setText(menu != null ? menu : "No menu");
        stylistTextView.setText(stylist != null ? stylist : "No stylist");
        dateTimeTextView.setText((date != null ? date : "No date") + "\n" + (time != null ? time : "No time"));


        // My Page Button's Click Listener
        myPageButton.setOnClickListener(v -> {
//            // Toast Messages for debugging
//            Toast.makeText(BookingCompleted.this, "MyPage ボタンがクリックされました", Toast.LENGTH_SHORT).show();

            // Move to Mypage
            Intent myPageIntent = new Intent(BookingCompleted.this, MyPage.class);
            startActivity(myPageIntent);
            // Optionally, finish current activity
            // finish();
        });
    }
}
