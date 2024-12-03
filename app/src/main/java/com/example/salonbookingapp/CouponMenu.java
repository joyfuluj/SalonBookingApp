package com.example.salonbookingapp;

import android.content.Intent;
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

public class CouponMenu extends AppCompatActivity {

    private final List<String> coupons = Arrays.asList("Coupon A", "Coupon B", "Coupon C");
    private final List<String> menus = Arrays.asList("Menu A", "Menu B", "Menu C");

    private Button tabAll, tabCoupon, tabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Buttons
        tabAll = findViewById(R.id.tab_all);
        tabCoupon = findViewById(R.id.tab_coupon);
        tabMenu = findViewById(R.id.tab_menu);

        // Initial Condition ("ALL" is selected)
        updateList("ALL");
        updateButtonStyles(tabAll);

        // Click Listener
        tabAll.setOnClickListener(v -> {
            updateList("ALL");
            updateButtonStyles(tabAll);
        });

        tabCoupon.setOnClickListener(v -> {
            updateList("Coupon");
            updateButtonStyles(tabCoupon);
        });

        tabMenu.setOnClickListener(v -> {
            updateList("Menu");
            updateButtonStyles(tabMenu);
        });
    }


    private void updateList(String filter) {
        LinearLayout container = findViewById(R.id.itemContainer);
        container.removeAllViews();

        // Create a list of items to display
        List<String> items = new ArrayList<>();
        if (filter.equals("ALL")) {
            items.addAll(coupons);
            items.addAll(menus);
        } else if (filter.equals("Coupon")) {
            items.addAll(coupons);
        } else if (filter.equals("Menu")) {
            items.addAll(menus);
        }

        // Add items
        for (String item : items) {
            RelativeLayout layout = new RelativeLayout(this);
            layout.setPadding(16, 16, 16, 16);

            // item name (TextView)
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

            // Book button
            Button bookButton = new Button(this);
            bookButton.setText("Book");
            bookButton.setId(View.generateViewId());

            // Button colour
            bookButton.setBackgroundResource(R.color.light_green); // 背景色を light_green に設定
            bookButton.setTextColor(getResources().getColor(android.R.color.white)); // テキスト色を白に設定


            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            bookButton.setLayoutParams(buttonParams);

            // Book button;s click listener
            bookButton.setOnClickListener(v -> {
                // Intent for PickDateTime
                Intent intent = new Intent(CouponMenu.this, PickDateTime.class);

                // Pass selected coupon information (“Coupon A”)
                intent.putExtra("selected_coupon", item);

                // start activity
                startActivity(intent);
            });

            layout.addView(textView);
            layout.addView(bookButton);
            container.addView(layout);
        }
    }


    private void updateButtonStyles(Button selectedButton) {
        // Default button
        int defaultBackgroundColor = getResources().getColor(android.R.color.darker_gray);
        int defaultTextColor = getResources().getColor(android.R.color.white);

        // selected button
        int selectedBackgroundColor = getResources().getColor(android.R.color.holo_blue_light);
        int selectedTextColor = getResources().getColor(android.R.color.black);

        tabAll.setBackgroundColor(defaultBackgroundColor);
        tabAll.setTextColor(defaultTextColor);

        tabCoupon.setBackgroundColor(defaultBackgroundColor);
        tabCoupon.setTextColor(defaultTextColor);

        tabMenu.setBackgroundColor(defaultBackgroundColor);
        tabMenu.setTextColor(defaultTextColor);

        // emphisize selected button
        selectedButton.setBackgroundColor(selectedBackgroundColor);
        selectedButton.setTextColor(selectedTextColor);
    }



}
