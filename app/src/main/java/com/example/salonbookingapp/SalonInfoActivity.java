package com.example.salonbookingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SalonInfoActivity extends AppCompatActivity {

    private TextView salonAddress, salonContact;
    private Button buttonCloseSalonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_info);

        // Views の初期化
        salonAddress = findViewById(R.id.salon_address);
        salonContact = findViewById(R.id.salon_contact);
        buttonCloseSalonInfo = findViewById(R.id.button_close_salon_info);

        // Intent からサロン名を取得
        String salonName = getIntent().getStringExtra(Constants.EXTRA_BOOKING_SALON);

        // TODO: 実際のサロン情報を取得して表示します
        // 現在は指定されたデータを表示しているけど、実際には salonName を基にデータを取得する予定
        salonAddress.setText("Address: 123 Main Street, City, Country");
        salonContact.setText("Contact: 987-654-3210");

        // Close ボタンのクリックリスナー
        buttonCloseSalonInfo.setOnClickListener(v -> {
            finish(); // 現在のアクティビティを終了して前の画面に戻る
        });
    }
}
