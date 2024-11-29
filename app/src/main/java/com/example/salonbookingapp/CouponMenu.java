package com.example.salonbookingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CouponMenu extends AppCompatActivity {

    private Button tabAll, tabCoupon, tabMenu;
    private ListView listView;
    private List<MenuItem> menuItems;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Initialize views
        tabAll = findViewById(R.id.tab_all);
        tabCoupon = findViewById(R.id.tab_coupon);
        tabMenu = findViewById(R.id.tab_menu);
        listView = findViewById(R.id.listView);

        // Load data from text file
        menuItems = new ArrayList<>();
        loadCouponMenuData();

        // Initialize adapter and set to ListView
        menuAdapter = new MenuAdapter(menuItems);
        listView.setAdapter(menuAdapter);

        // Initial setup for "ALL"
        filterItems("ALL");
        updateButtonStyles(tabAll);

        // Tab click listeners
        tabAll.setOnClickListener(v -> {
            filterItems("ALL");
            updateButtonStyles(tabAll);
        });

        tabCoupon.setOnClickListener(v -> {
            filterItems("Coupon");
            updateButtonStyles(tabCoupon);
        });

        tabMenu.setOnClickListener(v -> {
            filterItems("Menu");
            updateButtonStyles(tabMenu);
        });

        // List item click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            MenuItem selectedItem = menuItems.get(position);
            Toast.makeText(this, "Selected: " + selectedItem.getName(), Toast.LENGTH_SHORT).show();

            // Pass selected item to next activity
            Intent intent = new Intent(CouponMenu.this, PickDateTime.class);
            intent.putExtra("selected_coupon", selectedItem.getName());
            startActivity(intent);
        });
    }

    private void loadCouponMenuData() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.coupons_menus);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String type = parts[0].trim();
                    String name = parts[1].trim();
                    String price = parts[2].trim();

                    // Skip "Name" row
                    if (name.equalsIgnoreCase("Name")) continue;

                    menuItems.add(new MenuItem(type, name, price));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load coupon menu data", Toast.LENGTH_SHORT).show();
        }
    }


    private void filterItems(String filter) {
        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (filter.equals("ALL") || item.getType().equalsIgnoreCase(filter)) {
                filteredItems.add(item);
            }
        }
        menuAdapter.updateData(filteredItems);
    }

    private void updateButtonStyles(Button selectedButton) {
        int defaultColor = getResources().getColor(android.R.color.darker_gray);
        int selectedColor = getResources().getColor(android.R.color.holo_blue_light);

        tabAll.setBackgroundColor(defaultColor);
        tabCoupon.setBackgroundColor(defaultColor);
        tabMenu.setBackgroundColor(defaultColor);

        selectedButton.setBackgroundColor(selectedColor);
    }

    // MenuItem class
    private static class MenuItem {
        private final String type;
        private final String name;
        private final String price;

        public MenuItem(String type, String name, String price) {
            this.type = type;
            this.name = name;
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
    }

    // Custom Adapter
    private class MenuAdapter extends BaseAdapter {
        private List<MenuItem> data;

        public MenuAdapter(List<MenuItem> data) {
            this.data = data;
        }

        public void updateData(List<MenuItem> newData) {
            this.data = newData;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(CouponMenu.this).inflate(R.layout.item_menu, parent, false);
            }

            TextView name = convertView.findViewById(R.id.item_name);
            TextView price = convertView.findViewById(R.id.item_price);

            MenuItem item = data.get(position);

            // Set title text
            name.setText(item.getName());
            name.setTypeface(null, Typeface.BOLD);

            // Set price text
            if (item.getPrice() == null || item.getPrice().isEmpty()) {
                price.setVisibility(View.GONE);
            } else {
                price.setVisibility(View.VISIBLE);
                price.setText(item.getPrice());
            }

            return convertView;
        }

    }
}
