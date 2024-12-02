package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickDateTime extends AppCompatActivity {

    private Button selectedStylistButton; // Currently selected stylist button
    private String selectedStylist = "A"; // Default stylist is A
    private Map<String, Map<String, Boolean>> stylistAvailability; // Availability data for each stylist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date_time);

        // Get the selected coupon/menu from the previous activity
        String selectedMenu = getIntent().getStringExtra("MENU");
        TextView selectedCouponTextView = findViewById(R.id.selected_coupon);
        if (selectedMenu != null) {
            selectedCouponTextView.setText("Selected Coupon / Menu: " + selectedMenu);
        }

        TextView messageTextView = findViewById(R.id.message_text);
        // Stylist's default message
        messageTextView.setText("Hello! I’m your stylist. Let’s make you shine today!");

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

        // Load stylist availability data from the text file
        loadStylistAvailabilityData();

        // Setup date RecyclerView (horizontal scroll)
        setupDateRecyclerView();

        // Setup time slot RecyclerView (grid)
        setupTimeSlotRecyclerView();

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
                    boolean available = parts[2].trim().equals("⭕");

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

    private void setupDateRecyclerView() {
        RecyclerView dateRecyclerView = findViewById(R.id.recycler_view_dates);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Sample data for dates
        List<String> dates = new ArrayList<>();
        dates.add("1 Fri");
        dates.add("2 Sat");
        dates.add("3 Sun");
        dates.add("4 Mon");
        dates.add("5 Tue");
        dates.add("6 Wed");
        dates.add("7 Thu");

        DateAdapter dateAdapter = new DateAdapter(dates, selectedDate -> {
            Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            // Optionally update time slots based on selected date
        });
        dateRecyclerView.setAdapter(dateAdapter);
    }

    private void setupTimeSlotRecyclerView() {
        RecyclerView timeSlotRecyclerView = findViewById(R.id.recycler_view_availability);
        timeSlotRecyclerView.setLayoutManager(new GridLayoutManager(this, 7)); // 7 columns (1 for each day)

        // Sample data for time slots
        List<String> timeSlots = new ArrayList<>();
        String[] times = {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"};
        for (String time : times) {
            timeSlots.add(time);
        }

        TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(timeSlots, stylistAvailability.get(selectedStylist), timeSlot -> {
            Intent intent = new Intent(PickDateTime.this, CustomerInformation.class);
            intent.putExtra("SELECTED_TIME", timeSlot);
            startActivity(intent);
        });
        timeSlotRecyclerView.setAdapter(timeSlotAdapter);
    }

    private void displayAvailability() {
        // Update the availability in the RecyclerView if needed
        setupTimeSlotRecyclerView();
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
