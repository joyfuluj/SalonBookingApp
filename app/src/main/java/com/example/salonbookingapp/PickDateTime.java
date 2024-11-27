package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PickDateTime extends AppCompatActivity {

    private Button selectedStylistButton; // Button for currently selected stylist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date_time);


        // Get selected coupon information from Intent
        String selectedCoupon = getIntent().getStringExtra("selected_coupon");

        // Show selected coupons
        TextView selectedCouponTextView = findViewById(R.id.selected_coupon);
        if (selectedCoupon != null) {
            selectedCouponTextView.setText("Selected Coupon / Menu: " + selectedCoupon);
        }

        // stylist buttons
        Button stylistA = findViewById(R.id.stylist_a);
        Button stylistB = findViewById(R.id.stylist_b);
        Button stylistC = findViewById(R.id.stylist_c);

        // Select stylist A initially
        updateStylistSelection(stylistA);

        // Set click listeners for each button
        stylistA.setOnClickListener(v -> updateStylistSelection(stylistA));
        stylistB.setOnClickListener(v -> updateStylistSelection(stylistB));
        stylistC.setOnClickListener(v -> updateStylistSelection(stylistC));



        // Set up a click listener (circle icon at 11:00 on Fridays)
        TextView time_slot_1100_Fri = findViewById(R.id.time_slot_1100_Fri);

        // Set Cliclk listener
        time_slot_1100_Fri.setOnClickListener(v -> {
            Intent intent = new Intent(PickDateTime.this, CustomerInformation.class);
            intent.putExtra("SELECTED_TIME", "2024/11/02(Sat) 11:00");
            startActivity(intent);
        });

    }

    private void updateStylistSelection(Button selectedButton) {
        // Restore to the default background color of the previously selected button
        if (selectedStylistButton != null) {
            selectedStylistButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // gray
            selectedStylistButton.setTextColor(getResources().getColor(android.R.color.black)); // black
        }

        // change the background color of the selected button
        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light)); // blue
        selectedButton.setTextColor(getResources().getColor(android.R.color.white)); // white
        selectedStylistButton = selectedButton; // update the selected button
    }
}
