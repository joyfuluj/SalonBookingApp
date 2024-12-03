package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        // Confirm Button
        Button confirmButton = findViewById(R.id.btn_confirm_reservation);

        // Confirm Button;s Click Listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to "BookingCompleted" page
                Intent intent = new Intent(BookingConfirmation.this, BookingCompleted.class);


<<<<<<< Updated upstream
                startActivity(intent);
=======

                // お客様情報も渡す
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_NAME, name);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_PHONE, phone);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_EMAIL, email);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_REQUEST, request);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_PRICE, price);


                // Optionally, pass other data if needed
                startActivity(completedIntent);
                // Optionally, finish current activity to prevent back navigation
                // finish();
>>>>>>> Stashed changes
            }
        });
    }
}