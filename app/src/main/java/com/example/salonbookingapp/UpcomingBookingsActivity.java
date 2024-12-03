package com.example.salonbookingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UpcomingBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUpcomingBookings;
    private UpcomingBookingsAdapter adapter;
    private ArrayList<Booking> upcomingBookingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_bookings);

        // RecyclerView の初期化
        recyclerViewUpcomingBookings = findViewById(R.id.recycler_view_upcoming_bookings);
        recyclerViewUpcomingBookings.setLayoutManager(new LinearLayoutManager(this));

        // 予約データの準備
        upcomingBookingsList = new ArrayList<>();
        upcomingBookingsList.add(new Booking("Salon A", "Coupon A", "Stylist A", "2024/11/03(Sun)", "11:00am - 12:00pm"));
        upcomingBookingsList.add(new Booking("Salon B", "Coupon B", "Stylist B", "2024/11/10(Sun)", "02:00pm - 03:00pm"));

        // アダプターの設定
        adapter = new UpcomingBookingsAdapter(this, upcomingBookingsList);
        recyclerViewUpcomingBookings.setAdapter(adapter);
    }
}
