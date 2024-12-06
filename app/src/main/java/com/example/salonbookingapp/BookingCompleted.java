package com.example.salonbookingapp;

import android.content.Context; // 追加
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream; // 追加
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.FileOutputStream; // 追加

public class BookingCompleted extends AppCompatActivity {

    private TextView salonTextView, menuTextView, stylistTextView, dateTimeTextView;
    private Button myPageButton;

    // 予約情報を保存する変数
    String customerName;
    String customerPhone;
    String customerEmail;
    String customerRequest;
    String price;
    String menuName;
    String selectedStylist;
    String selectedDate;
    String selectedTime;
    String salonName;
    String username;
    String file;

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
        salonName = intent.getStringExtra(Constants.EXTRA_BOOKING_SALON);
        menuName = intent.getStringExtra(Constants.EXTRA_BOOKING_MENU);
        selectedStylist = intent.getStringExtra(Constants.EXTRA_BOOKING_STYLIST);
        selectedDate = intent.getStringExtra(Constants.EXTRA_BOOKING_DATE);
        selectedTime = intent.getStringExtra(Constants.EXTRA_BOOKING_TIME);

        // お客様情報も取得
        customerName = intent.getStringExtra(Constants.EXTRA_CUSTOMER_NAME);
        customerPhone = intent.getStringExtra(Constants.EXTRA_CUSTOMER_PHONE);
        customerEmail = intent.getStringExtra(Constants.EXTRA_CUSTOMER_EMAIL);
        customerRequest = intent.getStringExtra(Constants.EXTRA_CUSTOMER_REQUEST);
        price = intent.getStringExtra(Constants.EXTRA_CUSTOMER_PRICE);
        username = intent.getStringExtra("username");
        file = intent.getStringExtra("fileName");

        // Set the data to the TextViews
        salonTextView.setText(salonName != null ? salonName : "No salon");
        menuTextView.setText(menuName != null ? menuName : "No menu");
        stylistTextView.setText(selectedStylist != null ? selectedStylist : "No stylist");
        dateTimeTextView.setText((selectedDate != null ? selectedDate : "No date") + "\n" + (selectedTime != null ? selectedTime : "No time"));

        // データをテキストファイルに保存
        saveReservationData();

        // My Page Button's Click Listener
        myPageButton.setOnClickListener(v -> {
            Intent toHome = new Intent(this, Search.class);
            toHome.putExtra("state", "home");
            toHome.putExtra("username", username);
            startActivity(toHome);
        });
    }

    /**
     * 予約情報をテキストファイルに保存するメソッド
     */
    private void saveReservationData() {
        String filename = "reservations.txt";

        // 保存するデータをCSV形式で作成
        String reservationData = salonName + "," + menuName + "," + selectedStylist + "," + selectedDate + "," + selectedTime + "," +

                customerName + "," + customerPhone + "," + customerEmail + "," + customerRequest + "," + price + "," + username +"\n";

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

        try {
            FileInputStream fis2 = openFileInput(file);
            InputStreamReader isr2 = new InputStreamReader(fis2);
            BufferedReader br2 = new BufferedReader(isr2);

            StringBuilder updatedContent = new StringBuilder();
            String line2;
            while ((line2 = br2.readLine()) != null) {
                String[] words2 = line2.split(",\\s*");
                if (words2[1].equals(selectedTime) && words2[0].equals(selectedDate)) {
                    updatedContent.append(selectedDate).append(",").append(selectedTime).append(",").append(words2[2]).append(",").append("1").append(",").append(words2[4]).append("\n");
                } else {
                    updatedContent.append(line2).append("\n");
                }
            }
            br2.close();
            fis2.close();

            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(updatedContent.toString().getBytes());
            fos.close();

            //Toast.makeText(TimeSlot.this, "Time status updated", Toast.LENGTH_SHORT).show();
            // recreate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}