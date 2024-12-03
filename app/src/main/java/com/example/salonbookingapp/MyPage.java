package com.example.salonbookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {

    private TextView userUsername, userEmail, userPhone;
    private Button buttonViewUpcomingBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // Initialize Views
        userUsername = findViewById(R.id.user_username);
        userEmail = findViewById(R.id.user_email);
        userPhone = findViewById(R.id.user_phone);
        buttonViewUpcomingBooking = findViewById(R.id.button_view_upcoming_booking);

        // TODO: 実際のユーザー情報を取得して表示します
        // ここでは例としてハードコードされた値を設定していますが、実際にはユーザー情報を取得してください
        // 例: SharedPreferences やデータベースから取得

        // 例として SharedPreferences を使用
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "No username");
        String email = sharedPreferences.getString("email", "No email");
        String phone = sharedPreferences.getString("phone", "No phone");

        userUsername.setText(username);
        userEmail.setText(email);
        userPhone.setText(phone);

        // Upcoming Booking ボタンのクリックリスナー
        buttonViewUpcomingBooking.setOnClickListener(v -> {
            Intent intent = new Intent(MyPage.this, UpcomingBookingsActivity.class);
            startActivity(intent);
        });
    }
}
