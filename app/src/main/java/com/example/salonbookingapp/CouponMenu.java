package com.example.salonbookingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
//import android.view.MenuItem;  // causing 12 error. Never Import MenuItem!!!
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.salonbookingapp.Constants;


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

//        // List item click listener
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            MenuItem selectedItem = menuItems.get(position);
//            Toast.makeText(this, "Selected: " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
//
//            // Pass selected item to next activity
//            Intent intent = new Intent(CouponMenu.this, PickDateTime.class);
//            intent.putExtra("MENU", selectedItem.getName());
//            startActivity(intent);
//        });
//    }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            MenuItem selectedItem = (MenuItem) menuAdapter.getItem(position);

            // Pass selected item to next activity
            Intent intent = new Intent(CouponMenu.this, PickDateTime.class);
            intent.putExtra("MENU_TYPE", selectedItem.getType());
            intent.putExtra("MENU_NAME", selectedItem.getName());
            intent.putExtra("MENU_DESCRIPTION", selectedItem.getDescription());
            intent.putExtra("MENU_PRICE", selectedItem.getPrice());
            startActivity(intent);
        });
    }

        private void loadCouponMenuData() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.coupons_menus);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            // ヘッダー行をスキップ
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    String type = parts[0].trim();
                    String name = parts[1].trim();
                    String description = parts[2].trim();
                    String price = parts[3].trim();

                    menuItems.add(new MenuItem(type, name, description, price));
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

    public void back(View view) {
        finish(); // back to previous page
    }

    // MenuItem class
    private static class MenuItem {
        private final String type;
        private final String name;
        private final String description;
        private final String price;

        public MenuItem(String type, String name, String description, String price) {
            this.type = type;
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
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
            TextView description = convertView.findViewById(R.id.item_description);
            TextView price = convertView.findViewById(R.id.item_price);

            MenuItem item = data.get(position);

            // Set title text
            name.setText(item.getName());
            name.setTypeface(null, Typeface.BOLD);

            // Set description text
            description.setText(item.getDescription());

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
