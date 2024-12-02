package com.example.salonbookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PickDateTime extends AppCompatActivity {

    private Button selectedStylistButton;
    private String selectedStylist = "Stylist A";
    private Map<String, Map<String, Map<String, String>>> stylistAvailability;

    private final String[] fixedDates = {
            "2024-12-01", "2024-12-02", "2024-12-03",
            "2024-12-04", "2024-12-05", "2024-12-06", "2024-12-07"
    };

    private final String[] fixedTimeSlots = {
            "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
            "16:00", "16:30"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date_time);

        // スタイリストボタンの初期化
        Button stylistA = findViewById(R.id.stylist_a);
        Button stylistB = findViewById(R.id.stylist_b);
        Button stylistC = findViewById(R.id.stylist_c);

        // デフォルトのスタイリスト選択
        updateStylistSelection(stylistA);

        stylistA.setOnClickListener(v -> {
            updateStylistSelection(stylistA);
            selectedStylist = "Stylist A";
            setupAvailabilityTable();
        });

        stylistB.setOnClickListener(v -> {
            updateStylistSelection(stylistB);
            selectedStylist = "Stylist B";
            setupAvailabilityTable();
        });

        stylistC.setOnClickListener(v -> {
            updateStylistSelection(stylistC);
            selectedStylist = "Stylist C";
            setupAvailabilityTable();
        });

        // スタイリストの空き状況データをロード
        loadStylistAvailabilityData();

        // 空き状況テーブルをセットアップ
        setupAvailabilityTable();
    }

    private void loadStylistAvailabilityData() {
        stylistAvailability = new HashMap<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.stylist_availability);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4); // Stylist, Date, Time, Availability
                if (parts.length == 4) {
                    String stylist = parts[0].trim();  // e.g., "Stylist A"
                    String date = parts[1].trim();     // e.g., "2024-12-01"
                    String timeSlot = parts[2].trim(); // e.g., "11:00"
                    String available = parts[3].trim(); // "○" or "×"

                    // 格納処理
                    stylistAvailability.putIfAbsent(stylist, new HashMap<>());
                    stylistAvailability.get(stylist).putIfAbsent(date, new HashMap<>());
                    stylistAvailability.get(stylist).get(date).put(timeSlot, available);

                    // デバッグ用ログ
                    System.out.println("Loaded availability: Stylist=" + stylist + ", Date=" + date + ", Time=" + timeSlot + ", Available=" + available);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load stylist availability data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupAvailabilityTable() {
        TableLayout tableLayout = findViewById(R.id.table_availability);
        tableLayout.removeAllViews(); // 既存のビューをクリア

        // ヘッダー行（日付を表示）
        TableRow headerRow = new TableRow(this);
        TextView emptyCell = new TextView(this); // 左上隅の空セル
        emptyCell.setPadding(8, 8, 8, 8);
        headerRow.addView(emptyCell);

        for (String date : fixedDates) {
            TextView dateHeader = new TextView(this);
            String displayDate = date.substring(8); // 日付部分を取得（"01", "02", etc.）
            dateHeader.setText(displayDate);
            dateHeader.setPadding(8, 8, 8, 8);
            dateHeader.setGravity(Gravity.CENTER);
            dateHeader.setTypeface(null, Typeface.BOLD);
            headerRow.addView(dateHeader);
        }
        tableLayout.addView(headerRow);

        // 時間スロットごとの行
        for (String timeSlot : fixedTimeSlots) {
            TableRow timeRow = new TableRow(this);

            // 時間スロットのラベル
            TextView timeLabel = new TextView(this);
            timeLabel.setText(timeSlot);
            timeLabel.setPadding(8, 8, 8, 8);
            timeLabel.setGravity(Gravity.CENTER);
            timeLabel.setTypeface(null, Typeface.BOLD);
            timeRow.addView(timeLabel);

            // 各日付の空き状況セル
            for (String date : fixedDates) {
                TextView availabilityCell = new TextView(this);
                String availability = getAvailability(selectedStylist, date, timeSlot);

                availabilityCell.setText(availability);
                availabilityCell.setPadding(8, 8, 8, 8);
                availabilityCell.setGravity(Gravity.CENTER);
                availabilityCell.setBackgroundColor(Color.WHITE);

                if ("○".equals(availability)) {
                    availabilityCell.setClickable(true);
                    availabilityCell.setOnClickListener(v -> {
                        Intent intent = new Intent(PickDateTime.this, CustomerInformation.class);
                        intent.putExtra("SELECTED_STYLIST", selectedStylist);
                        intent.putExtra("SELECTED_DATE", date);
                        intent.putExtra("SELECTED_TIME", timeSlot);
                        startActivity(intent);
                    });
                } else {
                    availabilityCell.setClickable(false);
                }

                timeRow.addView(availabilityCell);

                // デバッグ用ログ
                System.out.println("Stylist: " + selectedStylist + ", Date: " + date + ", TimeSlot: " + timeSlot + ", Availability: " + availability);
            }
            tableLayout.addView(timeRow);
        }

        // スタイリスト名の表示を更新
        TextView stylistNameTextView = findViewById(R.id.stylist_name);
        stylistNameTextView.setText(selectedStylist);
    }

    private String getAvailability(String stylist, String date, String timeSlot) {
        if (stylistAvailability.containsKey(stylist)) {
            Map<String, Map<String, String>> dateMap = stylistAvailability.get(stylist);
            if (dateMap.containsKey(date)) {
                Map<String, String> timeSlotMap = dateMap.get(date);
                if (timeSlotMap.containsKey(timeSlot)) {
                    return timeSlotMap.get(timeSlot); // "○" or "×"
                }
            }
        }
        return "×"; // デフォルトは空きなし
    }

    private void updateStylistSelection(Button selectedButton) {
        if (selectedStylistButton != null) {
            selectedStylistButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            selectedStylistButton.setTextColor(getResources().getColor(android.R.color.black));
        }

        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
        selectedStylistButton = selectedButton;
    }
}
