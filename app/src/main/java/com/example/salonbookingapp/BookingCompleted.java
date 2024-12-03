package com.example.salonbookingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileOutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class BookingCompleted extends AppCompatActivity {

    private TextView salonTextView, menuTextView, stylistTextView, dateTimeTextView;
    private Button myPageButton;

    // 予約情報を保存する変数
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerRequest;
    private String price;
    private String menuName;
    private String selectedStylist;
    private String selectedDate;
    private String selectedTime;
    private String salonName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_completed);

        // Initialize TextViews and Button
        salonTextView = findViewById(R.id.booking_salon);
        menuTextView = findViewById(R.id.booking_menu);
        stylistTextView = findViewById(R.id.booking_stylist);
        dateTimeTextView = findViewById(R.id.booking_date_time);
        myPageButton = findViewById(R.id.mypage_button);

        // Get data from the Intent using Constants
        Intent intent = getIntent();
        String salon = intent.getStringExtra(Constants.EXTRA_BOOKING_SALON);
        String menu = intent.getStringExtra(Constants.EXTRA_BOOKING_MENU);
        String stylist = intent.getStringExtra(Constants.EXTRA_BOOKING_STYLIST);
        String date = intent.getStringExtra(Constants.EXTRA_BOOKING_DATE);
        String time = intent.getStringExtra(Constants.EXTRA_BOOKING_TIME);

        // Set the data to the TextViews
        salonTextView.setText(salon != null ? salon : "No salon");
        menuTextView.setText(menu != null ? menu : "No menu");
        stylistTextView.setText(stylist != null ? stylist : "No stylist");
        dateTimeTextView.setText((date != null ? date : "No date") + "\n" + (time != null ? time : "No time"));


        // My Page Button's Click Listener
        myPageButton.setOnClickListener(v -> {
//            // Toast Messages for debugging
//            Toast.makeText(BookingCompleted.this, "MyPage ボタンがクリックされました", Toast.LENGTH_SHORT).show();

            // Move to Mypage
            Intent myPageIntent = new Intent(BookingCompleted.this, MyPage.class);
            startActivity(myPageIntent);
            // Optionally, finish current activity
            // finish();
        });

        // お客様情報も取得
        customerName =intent.getStringExtra(Constants.EXTRA_CUSTOMER_NAME);
        customerPhone =intent.getStringExtra(Constants.EXTRA_CUSTOMER_PHONE);
        customerEmail =intent.getStringExtra(Constants.EXTRA_CUSTOMER_EMAIL);
        customerRequest =intent.getStringExtra(Constants.EXTRA_CUSTOMER_REQUEST);
        price =intent.getStringExtra(Constants.EXTRA_CUSTOMER_PRICE);

        // データをテキストファイルに保存
        saveReservationData();
}
        /**
         * 予約情報をテキストファイルに保存するメソッド
         */
        private void saveReservationData() {
            String filename = "reservations.txt";

            // 保存するデータをCSV形式で作成
            String reservationData = salonName + "," + menuName + "," + selectedStylist + "," + selectedDate + "," + selectedTime + "," +
                    customerName + "," + customerPhone + "," + customerEmail + "," + customerRequest + "," + price + "\n";

            FileOutputStream outputStream;

            try {
                // ファイルにデータを追記（存在しない場合は新規作成）
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                outputStream.write(reservationData.getBytes());
                outputStream.close();

                // 保存成功のメッセージを表示
                Toast.makeText(this, "Reservation saved successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                // 保存失敗のメッセージを表示
                Toast.makeText(this, "Failed to save reservation.", Toast.LENGTH_SHORT).show();
            }


        }
        public void login(View v){
            Intent completedIntent = new Intent(BookingCompleted.this, MainActivity.class);
            startActivity(completedIntent);
        }
}
