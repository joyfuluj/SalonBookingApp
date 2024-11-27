package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);

        // button
        Button reviewButton = findViewById(R.id.btn_review_reservation);

        // button's click listener
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user input (LATER)
                EditText nameInput = findViewById(R.id.input_name);
                EditText phoneInput = findViewById(R.id.input_phone);
                EditText emailInput = findViewById(R.id.input_email);
                EditText requestInput = findViewById(R.id.input_request);

                // Converts user input to string
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String email = emailInput.getText().toString();
                String request = requestInput.getText().toString();

                // move to "BookingConfirmation" page
                Intent intent = new Intent(CustomerInformation.this, BookingConfirmation.class);

                // Pass input data to the intent
                intent.putExtra("CUSTOMER_NAME", name);
                intent.putExtra("CUSTOMER_PHONE", phone);
                intent.putExtra("CUSTOMER_EMAIL", email);
                intent.putExtra("CUSTOMER_REQUEST", request);

                // start "BookingConfirmation" activity
                startActivity(intent);
            }
        });
    }
}
