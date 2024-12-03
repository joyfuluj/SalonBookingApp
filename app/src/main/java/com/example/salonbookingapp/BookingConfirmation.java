package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class BookingConfirmation extends AppCompatActivity {

    private String selectedStylist;
    private String selectedDate;
    private String selectedTime;
    private String price;
    private String name;
    private String phone;
    private String email;
    private String request;
    private String menuName; // Added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        // Retrieve data from CustomerInformation
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra(Constants.EXTRA_CUSTOMER_NAME);
            phone = intent.getStringExtra(Constants.EXTRA_CUSTOMER_PHONE);
            email = intent.getStringExtra(Constants.EXTRA_CUSTOMER_EMAIL);
            request = intent.getStringExtra(Constants.EXTRA_CUSTOMER_REQUEST);
            price = intent.getStringExtra(Constants.EXTRA_CUSTOMER_PRICE);

            selectedStylist = intent.getStringExtra(Constants.EXTRA_SELECTED_STYLIST);
            selectedDate = intent.getStringExtra(Constants.EXTRA_SELECTED_DATE);
            selectedTime = intent.getStringExtra(Constants.EXTRA_SELECTED_TIME);

            menuName = intent.getStringExtra(Constants.EXTRA_MENU_NAME); // Added
        }

        // Display data in TextViews
        TextView nameTextView = findViewById(R.id.customer_name);
        TextView phoneTextView = findViewById(R.id.customer_phone);
        TextView emailTextView = findViewById(R.id.customer_email);
        TextView requestTextView = findViewById(R.id.customer_request);
        TextView priceTextView = findViewById(R.id.customer_price);
        TextView stylistTextView = findViewById(R.id.booking_stylist);
        TextView dateTimeTextView = findViewById(R.id.booking_date_time);
        TextView menuTextView = findViewById(R.id.booking_menu); // 追加

        nameTextView.setText(name != null ? name : "No name");
        phoneTextView.setText(phone != null ? phone : "No phone");
        emailTextView.setText(email != null ? email : "No email");
        requestTextView.setText((request != null && !request.isEmpty()) ? request : "No request");
        priceTextView.setText(price != null ? price : "$0.00");
        stylistTextView.setText(selectedStylist != null ? selectedStylist : "No stylist");
        dateTimeTextView.setText((selectedDate != null ? selectedDate : "No date") + " " + (selectedTime != null ? selectedTime : "No time"));
        menuTextView.setText(menuName != null ? menuName : "No menu"); // 追加

        // バックボタンの初期化
        TextView backButton = findViewById(R.id.backButton); // TextView の ID を使用

        // バックボタンのクリックリスナーを設定
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(view);
            }
        });

        // Confirm Button
        Button confirmButton = findViewById(R.id.btn_confirm_reservation);

        // Confirm Button's Click Listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to "BookingCompleted" page with necessary data
                Intent completedIntent = new Intent(BookingConfirmation.this, BookingCompleted.class);
                completedIntent.putExtra(Constants.EXTRA_BOOKING_SALON, "Salon A"); // Assuming "Salon A" is fixed or retrieve dynamically
                completedIntent.putExtra(Constants.EXTRA_BOOKING_MENU, menuName); // Updated to retrieve dynamic menu
                completedIntent.putExtra(Constants.EXTRA_BOOKING_STYLIST, selectedStylist);
                completedIntent.putExtra(Constants.EXTRA_BOOKING_DATE, selectedDate);
                completedIntent.putExtra(Constants.EXTRA_BOOKING_TIME, selectedTime);

                // お客様情報も渡す
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_NAME, name);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_PHONE, phone);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_EMAIL, email);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_REQUEST, request);
                completedIntent.putExtra(Constants.EXTRA_CUSTOMER_PRICE, price);

                // 不要な startActivity(intent); を削除

                // Start "BookingCompleted" activity
                startActivity(completedIntent);

                // Optionally, finish current activity to prevent back navigation
                // finish();
            }
        });
    }

    /**
     * バックボタンが押されたときに呼び出されるメソッド
     */
    public void back(View v) {
        finish();
    }
}
