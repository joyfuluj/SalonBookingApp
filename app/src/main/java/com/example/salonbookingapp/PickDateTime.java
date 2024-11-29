package com.example.salonbookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PickDateTime extends AppCompatActivity {

    private Button selectedStylistButton; // Button for the currently selected stylist
    private String selectedStylist = "A"; // Default stylist is A
    private GridLayout timeSlotGrid; // Grid for displaying time slots
    private Map<String, Map<String, Boolean>> stylistAvailability; // Availability data for each stylist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date_time);

        // Get the selected coupon/menu from the previous activity
        String selectedCoupon = getIntent().getStringExtra("selected_coupon");
        TextView selectedCouponTextView = findViewById(R.id.selected_coupon);
        if (selectedCoupon != null) {
            selectedCouponTextView.setText("Selected Coupon / Menu: " + selectedCoupon);
        }

        // Initialize stylist buttons
        Button stylistA = findViewById(R.id.stylist_a);
        Button stylistB = findViewById(R.id.stylist_b);
        Button stylistC = findViewById(R.id.stylist_c);

        // Default stylist selection
        updateStylistSelection(stylistA);

        // Set click listeners for stylist buttons
        stylistA.setOnClickListener(v -> {
            updateStylistSelection(stylistA);
            selectedStylist = "A";
            displayAvailability();
        });
        stylistB.setOnClickListener(v -> {
            updateStylistSelection(stylistB);
            selectedStylist = "B";
            displayAvailability();
        });
        stylistC.setOnClickListener(v -> {
            updateStylistSelection(stylistC);
            selectedStylist = "C";
            displayAvailability();
        });

        // Grid layout for time slots
        timeSlotGrid = findViewById(R.id.time_slot_grid);

        // Load stylist availability data from the text file
        loadStylistAvailabilityData();

        // Display availability for the default stylist (A)
        displayAvailability();
    }

    private void loadStylistAvailabilityData() {
        stylistAvailability = new HashMap<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.stylist_availability);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String stylist = parts[0].trim();
                    String timeSlot = parts[1].trim();
                    boolean available = parts[2].trim().equals("O");

                    // Add the stylist and their availability data
                    stylistAvailability.putIfAbsent(stylist, new HashMap<>());
                    stylistAvailability.get(stylist).put(timeSlot, available);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load stylist availability data", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAvailability() {
        // Clear previous time slots
        timeSlotGrid.removeAllViews();

        if (!stylistAvailability.containsKey(selectedStylist)) return;

        Map<String, Boolean> availability = stylistAvailability.get(selectedStylist);
        int columnCount = 8; // Set the number of columns
        String[] timeSlots = {"11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"}; // Time slots

        // Create headers for the days of the week
        for (int i = 0; i < timeSlots.length; i++) {
            TextView timeSlotView = new TextView(this);
            timeSlotView.setText(timeSlots[i]);
            timeSlotView.setPadding(16, 16, 16, 16);
            timeSlotView.setGravity(Gravity.CENTER);
            timeSlotView.setBackgroundColor(Color.LTGRAY); // Light gray for headers
            timeSlotView.setTextColor(Color.BLACK);

            // Add to grid layout (first column for time slots)
            timeSlotGrid.addView(timeSlotView);
        }

        // Add availability slots for the selected stylist
        for (Map.Entry<String, Boolean> entry : availability.entrySet()) {
            String timeSlot = entry.getKey();
            boolean isAvailable = entry.getValue();

            TextView timeSlotView = new TextView(this);
            timeSlotView.setText(isAvailable ? "⭕" : "❌");
            timeSlotView.setPadding(16, 16, 16, 16);
            timeSlotView.setGravity(Gravity.CENTER);
            timeSlotView.setBackgroundColor(isAvailable ? Color.GREEN : Color.RED);
            timeSlotView.setTextColor(Color.WHITE);

            // Enable click only for available slots
            if (isAvailable) {
                timeSlotView.setOnClickListener(v -> {
                    Intent intent = new Intent(PickDateTime.this, CustomerInformation.class);
                    intent.putExtra("SELECTED_TIME", timeSlot);
                    startActivity(intent);
                });
            }

            // Add the time slot to the grid (use the correct column for the time)
            timeSlotGrid.addView(timeSlotView);
        }
    }



    private void updateStylistSelection(Button selectedButton) {
        // Reset the background color of the previously selected button
        if (selectedStylistButton != null) {
            selectedStylistButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            selectedStylistButton.setTextColor(getResources().getColor(android.R.color.black));
        }

        // Highlight the currently selected button
        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
        selectedStylistButton = selectedButton;
    }
}
