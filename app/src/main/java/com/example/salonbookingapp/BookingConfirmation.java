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

        // Get data from the Intent
        String name = getIntent().getStringExtra("CUSTOMER_NAME");
        String phone = getIntent().getStringExtra("CUSTOMER_PHONE");
        String email = getIntent().getStringExtra("CUSTOMER_EMAIL");
        String request = getIntent().getStringExtra("CUSTOMER_REQUEST");
        String price = getIntent().getStringExtra("CUSTOMER_PRICE");

        // Set the data to the TextViews
        TextView nameTextView = findViewById(R.id.customer_name);
        TextView phoneTextView = findViewById(R.id.customer_phone);
        TextView emailTextView = findViewById(R.id.customer_email);
        TextView requestTextView = findViewById(R.id.customer_request);
        TextView priceTextView = findViewById(R.id.customer_price);

        nameTextView.setText("Name: " + name);
        phoneTextView.setText("Phone: " + phone);
        emailTextView.setText("Email: " + email);
        requestTextView.setText("Request: " + request);
        priceTextView.setText("Price: " + price);

        // Confirm Button
        Button confirmButton = findViewById(R.id.btn_confirm_reservation);

        // Confirm Button's Click Listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to "BookingCompleted" page
                Intent intent = new Intent(BookingConfirmation.this, BookingCompleted.class);
                startActivity(intent);
            }
        });
    }
}
