package com.example.salonbookingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu extends AppCompatActivity {

    // データリスト
    private final List<String> coupons = Arrays.asList("Coupon A", "Coupon B", "Coupon C");
    private final List<String> menus = Arrays.asList("Menu A", "Menu B", "Menu C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // あなたのXMLレイアウトを適用

        // 初期表示でALLを選択
        updateList("ALL");

        // ボタンのクリックイベントを設定
        Button tabAll = findViewById(R.id.tab_all);
        Button tabCoupon = findViewById(R.id.tab_coupon);
        Button tabMenu = findViewById(R.id.tab_menu);

        tabAll.setOnClickListener(v -> updateList("ALL"));
        tabCoupon.setOnClickListener(v -> updateList("Coupon"));
        tabMenu.setOnClickListener(v -> updateList("Menu"));
    }

    // リストを更新するメソッド
    private void updateList(String filter) {
        LinearLayout container = findViewById(R.id.itemContainer);
        container.removeAllViews(); // コンテナをクリア

        List<String> items = new ArrayList<>();
        if (filter.equals("ALL")) {
            items.addAll(coupons);
            items.addAll(menus);
        } else if (filter.equals("Coupon")) {
            items.addAll(coupons);
        } else if (filter.equals("Menu")) {
            items.addAll(menus);
        }

        // アイテムをコンテナに追加
        for (String item : items) {
            RelativeLayout layout = new RelativeLayout(this);
            layout.setPadding(16, 16, 16, 16);

            // アイテム名
            TextView textView = new TextView(this);
            textView.setText(item);
            textView.setTextSize(18);
            textView.setId(View.generateViewId());

            RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            textParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            textView.setLayoutParams(textParams);

            // ブックマークボタン
            Button bookButton = new Button(this);
            bookButton.setText("Book");
            bookButton.setId(View.generateViewId());

            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            bookButton.setLayoutParams(buttonParams);

            bookButton.setOnClickListener(v ->
                    Toast.makeText(this, item + " booked!", Toast.LENGTH_SHORT).show());

            layout.addView(textView);
            layout.addView(bookButton);
            container.addView(layout);
        }
    }
}
