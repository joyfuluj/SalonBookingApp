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

    private String selectedStylist;
    private String selectedDate;
    private String selectedTime;
    private String menuName; // Added


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);

        // Generate random price between $35 and $65
        String price = generateRandomPrice(35, 65);

        // Display the random price in the TextView
        TextView priceTextView = findViewById(R.id.price);
        priceTextView.setText(price);

        // Retrieve data from PickDateTime
        Intent intent = getIntent();
        if (intent != null) {
            selectedStylist = intent.getStringExtra(Constants.EXTRA_SELECTED_STYLIST);
            selectedDate = intent.getStringExtra(Constants.EXTRA_SELECTED_DATE);
            selectedTime = intent.getStringExtra(Constants.EXTRA_SELECTED_TIME);
            menuName = intent.getStringExtra(Constants.EXTRA_MENU_NAME); // Added
        }

        // Display the selected stylist, date, and time
        TextView selectedStylistTextView = findViewById(R.id.selected_stylist);
        TextView selectedDateTextView = findViewById(R.id.selected_date);
        TextView selectedTimeTextView = findViewById(R.id.selected_time);

        if (selectedStylist != null) {
            selectedStylistTextView.setText(selectedStylist);
        }

        if (selectedDate != null) {
            selectedDateTextView.setText(selectedDate);
        }

        if (selectedTime != null) {
            selectedTimeTextView.setText(selectedTime);
        }

        // Initialize the review button
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
                String name = nameInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String request = requestInput.getText().toString().trim();

                // Validate inputs.
                if (TextUtils.isEmpty(name)) {
                    nameInput.setError("Name is required");
                    nameInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    phoneInput.setError("Phone number is required");
                    phoneInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Email is required");
                    emailInput.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Enter a valid email");
                    emailInput.requestFocus();
                    return;
                }

                // Move to "BookingConfirmation" page
                Intent confirmationIntent = new Intent(CustomerInformation.this, BookingConfirmation.class);

                // Pass input data and random price to the intent
                confirmationIntent.putExtra(Constants.EXTRA_CUSTOMER_NAME, name);
                confirmationIntent.putExtra(Constants.EXTRA_CUSTOMER_PHONE, phone);
                confirmationIntent.putExtra(Constants.EXTRA_CUSTOMER_EMAIL, email);
                confirmationIntent.putExtra(Constants.EXTRA_CUSTOMER_REQUEST, request);
                confirmationIntent.putExtra(Constants.EXTRA_CUSTOMER_PRICE, price);  // Add price to intent

                // Pass selected stylist, date, and time
                confirmationIntent.putExtra(Constants.EXTRA_SELECTED_STYLIST, selectedStylist);
                confirmationIntent.putExtra(Constants.EXTRA_SELECTED_DATE, selectedDate);
                confirmationIntent.putExtra(Constants.EXTRA_SELECTED_TIME, selectedTime);
                confirmationIntent.putExtra(Constants.EXTRA_MENU_NAME, menuName); // Added


                // Start "BookingConfirmation" activity
                startActivity(confirmationIntent);
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
