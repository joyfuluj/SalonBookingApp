package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import android.text.TextUtils;
import android.util.Patterns;



import androidx.appcompat.app.AppCompatActivity;

public class CustomerInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);

        // Generate random price between $35 and $65
        String price = generateRandomPrice(35, 65);

        // Display the random price in the TextView
        TextView priceTextView = findViewById(R.id.price);
        priceTextView.setText(price);


        // button
        Button reviewButton = findViewById(R.id.btn_review_reservation);

        // Button's click listener
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get user input
                EditText nameInput = findViewById(R.id.input_name);
                EditText phoneInput = findViewById(R.id.input_phone);
                EditText emailInput = findViewById(R.id.input_email);
                EditText requestInput = findViewById(R.id.input_request);

                // Convert user input to string
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String email = emailInput.getText().toString();
                String request = requestInput.getText().toString();


                // Validate inputs.
                if (TextUtils.isEmpty(name)) {
                    nameInput.setError("Name is required");
                    nameInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    phoneInput.setError("Phone is required");
                    phoneInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Email is required");
                    emailInput.requestFocus();
                    return;
                }



                // Move to "BookingConfirmation" page
                Intent intent = new Intent(CustomerInformation.this, BookingConfirmation.class);

                // Pass input data and random price to the intent
                intent.putExtra("CUSTOMER_NAME", name);
                intent.putExtra("CUSTOMER_PHONE", phone);
                intent.putExtra("CUSTOMER_EMAIL", email);
                intent.putExtra("CUSTOMER_REQUEST", request);
                intent.putExtra("CUSTOMER_PRICE", price);  // Add price to intent

                // Start "BookingConfirmation" activity
                startActivity(intent);
            }
        });
    }

    // Function to generate a random price between a given min and max
    private String generateRandomPrice(int min, int max) {
        Random random = new Random();
        int randomPrice = random.nextInt((max - min) + 1) + min; // Generate a random price between min and max
        return "$" + randomPrice + ".00";  // Format as a price (e.g., "$45.00")
    }
}



