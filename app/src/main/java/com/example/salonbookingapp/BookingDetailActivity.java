package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingDetailActivity extends AppCompatActivity {

    private TextView detailSalonName, detailMenu, detailStylist, detailDateTime;
    private Button buttonViewSalonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Views の初期化
        detailSalonName = findViewById(R.id.detail_salon_name);
        detailMenu = findViewById(R.id.detail_menu);
        detailStylist = findViewById(R.id.detail_stylist);
        detailDateTime = findViewById(R.id.detail_date_time);
        buttonViewSalonInfo = findViewById(R.id.button_view_salon_info);

        // Intent からデータを取得
        Intent intent = getIntent();
        String salon = intent.getStringExtra(Constants.EXTRA_BOOKING_SALON);
        String menu = intent.getStringExtra(Constants.EXTRA_BOOKING_MENU);
        String stylist = intent.getStringExtra(Constants.EXTRA_BOOKING_STYLIST);
        String date = intent.getStringExtra(Constants.EXTRA_BOOKING_DATE);
        String time = intent.getStringExtra(Constants.EXTRA_BOOKING_TIME);

        // TextViews にデータをセット
        detailSalonName.setText("Salon: " + (salon != null ? salon : "No salon"));
        detailMenu.setText("Menu: " + (menu != null ? menu : "No menu"));
        detailStylist.setText("Stylist: " + (stylist != null ? stylist : "No stylist"));
        detailDateTime.setText("Date & Time: " + ((date != null ? date : "No date") + " " + (time != null ? time : "No time")));

        // Salon Info ボタンのクリックリスナー
        buttonViewSalonInfo.setOnClickListener(v -> {
            Intent salonInfoIntent = new Intent(BookingDetailActivity.this, SalonInfoActivity.class);
            salonInfoIntent.putExtra(Constants.EXTRA_BOOKING_SALON, salon);
            startActivity(salonInfoIntent);
        });
    }
}
