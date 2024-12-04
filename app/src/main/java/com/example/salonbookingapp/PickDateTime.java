package com.example.salonbookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PickDateTime extends AppCompatActivity {
    private String menuType;
    private String menuName;
    private String menuDescription;
    private String menuPrice;
    private String salonName;
    private String username;
    private TextView salon;

    private Button selectedStylistButton;
    private String selectedStylist = "Stylist A"; // デフォルトのスタイリスト名
    private Map<String, Map<String, Map<String, String>>> stylistAvailability;

    // 週ごとの日付配列（MM/DD 形式）
    private final String[][] weeks = {
            {"12/01", "12/02", "12/03", "12/04", "12/05", "12/06", "12/07"}, // Week 1
            {"12/08", "12/09", "12/10", "12/11", "12/12", "12/13", "12/14"}  // Week 2
    };

    private int selectedWeekIndex = 0; // デフォルトはWeek 1

    private final String[] fixedTimeSlots = {
            "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
            "16:00", "16:30"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date_time);


        // Intent からデータを取得
        Intent intent = getIntent();
        menuType = intent.getStringExtra("MENU_TYPE");
        menuName = intent.getStringExtra("MENU_NAME");
        menuDescription = intent.getStringExtra("MENU_DESCRIPTION");
        menuPrice = intent.getStringExtra("MENU_PRICE");
        salonName = intent.getStringExtra("salonName");
        username = intent.getStringExtra("username");

        salon = findViewById(R.id.salon_name);
        salon.setText(salonName);

        // デバッグ用ログ
        System.out.println("Received Menu Type: " + menuType);
        System.out.println("Received Menu Name: " + menuName);
        System.out.println("Received Menu Description: " + menuDescription);
        System.out.println("Received Menu Price: " + menuPrice);

        // 選択されたメニュー情報を表示
        displaySelectedMenuInfo();

        // スタイリストボタンの初期化
        Button stylistA = findViewById(R.id.stylist_a);
        Button stylistB = findViewById(R.id.stylist_b);
        Button stylistC = findViewById(R.id.stylist_c);

        // 週切り替えボタンの初期化
        Button buttonWeek1 = findViewById(R.id.button_week1);
        Button buttonWeek2 = findViewById(R.id.button_week2);


        // デフォルトの週選択（Week 1 をハイライト）
        highlightWeekButton(buttonWeek1, buttonWeek2);

        // スタイリストボタンの初期スタイルを設定
        Button[] allStylistButtons = {stylistA, stylistB, stylistC};
        for (Button button : allStylistButtons) {
            button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            button.setTextColor(getResources().getColor(android.R.color.black));
        }

        // デフォルトのスタイリスト選択
        updateStylistSelection(stylistA);

        // スタイリストボタンのクリックリスナー設定
        stylistA.setOnClickListener(v -> {
            updateStylistSelection(stylistA);
            selectedStylist = "Stylist A"; // スタイリストを更新
            setupAvailabilityTable(); // テーブルを再描画
        });

        stylistB.setOnClickListener(v -> {
            updateStylistSelection(stylistB);
            selectedStylist = "Stylist B"; // スタイリストを更新
            setupAvailabilityTable(); // テーブルを再描画
        });

        stylistC.setOnClickListener(v -> {
            updateStylistSelection(stylistC);
            selectedStylist = "Stylist C"; // スタイリストを更新
            setupAvailabilityTable(); // テーブルを再描画
        });

        // 週切り替えボタンのクリックリスナー設定
        buttonWeek1.setOnClickListener(v -> {
            selectedWeekIndex = 0; // Week 1
            highlightWeekButton(buttonWeek1, buttonWeek2);
            setupAvailabilityTable();
        });

        buttonWeek2.setOnClickListener(v -> {
            selectedWeekIndex = 1; // Week 2
            highlightWeekButton(buttonWeek2, buttonWeek1);
            setupAvailabilityTable();
        });

        // スタイリストの空き状況データをロード
        loadStylistAvailabilityData();

        // 空き状況テーブルをセットアップ
        setupAvailabilityTable();
    }

    /**
     * 選択されたメニュー情報を画面に表示するメソッド
     */
    private void displaySelectedMenuInfo() {
        TextView selectedCouponTextView = findViewById(R.id.selected_coupon);

        if (selectedCouponTextView != null) {
            if (menuName != null && !menuName.isEmpty()) {
                selectedCouponTextView.setText("Selected Coupon / Menu: " + menuName);
            } else {
                selectedCouponTextView.setText("Selected Coupon / Menu: None");
            }
        } else {
            System.out.println("TextView with id 'selected_coupon' not found.");
        }
    }

    /**
     * 週切り替えボタンのハイライトを管理するメソッド
     */
    private void highlightWeekButton(Button selectedButton, Button otherButton) {
        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));

        otherButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        otherButton.setTextColor(getResources().getColor(android.R.color.black));
    }

    /**
     * スタイリストの空き状況データをロードするメソッド
     */
    private void loadStylistAvailabilityData() {
        stylistAvailability = new HashMap<>();

        // スタイリスト名とスケジュールファイルの対応付け
        Map<String, String> stylistScheduleFiles = new HashMap<>();
        stylistScheduleFiles.put("Stylist A", "schedule1.txt");
        stylistScheduleFiles.put("Stylist B", "schedule2.txt");
        stylistScheduleFiles.put("Stylist C", "schedule3.txt");

        try {
            for (String stylist : stylistScheduleFiles.keySet()) {
                String resourceFile = stylistScheduleFiles.get(stylist);
                FileInputStream fis = openFileInput(resourceFile);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String line;

                // ヘッダー行をスキップ
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",", -1); // day,time,dayAva,bookingStatus,timeAva
                    if (parts.length == 5) {
                        String date = parts[0].trim();      // 例: "12/01"
                        String timeSlot = parts[1].trim();  // 例: "10:00"
                        String dayAva = parts[2].trim();    // "1" または "0"
                        String bookingStatus = parts[3].trim(); // "1" または "0"
                        String timeAva = parts[4].trim();   // "1" または "0"

                        // 空き状況の判定
                        String available;
                        if ("1".equals(dayAva) && "0".equals(bookingStatus) && "1".equals(timeAva)) {
                            available = "○";
                        } else {
                            available = "×";
                        }

                        // マップにデータを格納
                        stylistAvailability.putIfAbsent(stylist, new HashMap<>());
                        stylistAvailability.get(stylist).putIfAbsent(date, new HashMap<>());
                        stylistAvailability.get(stylist).get(date).put(timeSlot, available);

                        // デバッグ用ログ
                        System.out.println("Loaded availability: Stylist=" + stylist + ", Date=" + date + ", TimeSlot=" + timeSlot + ", Available=" + available);
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load stylist availability data", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 空き状況テーブルをセットアップするメソッド
     */
    private void setupAvailabilityTable() {
        TableLayout tableLayout = findViewById(R.id.table_availability);
        tableLayout.removeAllViews(); // 既存のビューをクリア

        String[] currentWeekDates = weeks[selectedWeekIndex];

        // ヘッダー行（日付を表示）
        TableRow headerRow = new TableRow(this);
        TextView emptyCell = new TextView(this); // 左上隅の空セル
        emptyCell.setPadding(8, 8, 8, 8);
        headerRow.addView(emptyCell);

        for (String date : currentWeekDates) {
            TextView dateHeader = new TextView(this);
            String displayDate = date; // 日付をそのまま表示
            dateHeader.setText(displayDate);
            dateHeader.setPadding(8, 8, 8, 8);
            dateHeader.setGravity(Gravity.CENTER);
            dateHeader.setTypeface(null, Typeface.BOLD);
            dateHeader.setBackgroundColor(Color.LTGRAY); // 見やすさのため背景色を追加
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
            timeLabel.setBackgroundColor(Color.LTGRAY); // 見やすさのため背景色を追加
            timeRow.addView(timeLabel);

            // 各日付の空き状況セル
            for (String date : currentWeekDates) {
                TextView availabilityCell = new TextView(this);
                String availability = getAvailability(selectedStylist, date, timeSlot);

                // シンボルのマッピング
                if ("○".equals(availability)) {
                    availability = "⭕️";
                } else if ("×".equals(availability)) {
                    availability = "-";
                } else {
                    availability = "-"; // デフォルト
                }

                availabilityCell.setText(availability);
                availabilityCell.setPadding(8, 8, 8, 8);
                availabilityCell.setGravity(Gravity.CENTER);
                availabilityCell.setTypeface(null, Typeface.BOLD);

                String fileName;

                if(selectedStylist.equals("Stylist A")){
                    fileName = "schedule1.txt";
                }
                else if(selectedStylist.equals("Stylist B")){
                    fileName = "schedule2.txt";
                }
                else fileName = "schedule3.txt";

                // 予約可能なセルのみクリック可能にする
                if ("⭕️".equals(availability)) {
                    availabilityCell.setClickable(true);
                    availabilityCell.setOnClickListener(v -> {
                        Intent intent = new Intent(PickDateTime.this, CustomerInformation.class);
                        intent.putExtra(Constants.EXTRA_MENU_NAME, menuName); // menuName を渡す
                        intent.putExtra(Constants.EXTRA_SELECTED_STYLIST, selectedStylist);
                        intent.putExtra(Constants.EXTRA_SELECTED_DATE, date);
                        intent.putExtra(Constants.EXTRA_SELECTED_TIME, timeSlot);
                        intent.putExtra("salonName", salonName);
                        intent.putExtra("username", username);
                        intent.putExtra("fileName", fileName);
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

        // スタイリストメッセージの表示を更新
        TextView messageTextView = findViewById(R.id.message_text);
        messageTextView.setText(getStylistMessage(selectedStylist));
    }

    /**
     * スタイリストごとのメッセージを取得するメソッド
     */
    private String getStylistMessage(String stylist) {
        switch (stylist) {
            case "Stylist A":
                return "Hello! I’m your stylist. Let’s make you shine today!";
            case "Stylist B":
                return "Hi there! Ready to give you a fresh new look.";
            case "Stylist C":
                return "Greetings! Let’s create a style you love.";
            default:
                return "Hello! I’m your stylist.";
        }
    }

    /**
     * 指定されたスタイリスト、日付、時間スロットの空き状況を取得するメソッド
     */
    private String getAvailability(String stylist, String date, String timeSlot) {
        if (stylistAvailability.containsKey(stylist)) {
            Map<String, Map<String, String>> dateMap = stylistAvailability.get(stylist);
            if (dateMap.containsKey(date)) {
                Map<String, String> timeSlotMap = dateMap.get(date);
                if (timeSlotMap.containsKey(timeSlot)) {
                    String availability = timeSlotMap.get(timeSlot);
                    // デバッグ用ログ
                    System.out.println("getAvailability: Stylist=" + stylist + ", Date=" + date + ", TimeSlot=" + timeSlot + ", Availability=" + availability);
                    return availability; // "○" または "×"
                } else {
                    System.out.println("getAvailability: TimeSlot not found - Stylist=" + stylist + ", Date=" + date + ", TimeSlot=" + timeSlot);
                }
            } else {
                System.out.println("getAvailability: Date not found - Stylist=" + stylist + ", Date=" + date);
            }
        } else {
            System.out.println("getAvailability: Stylist not found - Stylist=" + stylist);
        }
        return "×"; // デフォルトは空きなし
    }

    /**
     * スタイリストボタンの選択状態を更新するメソッド
     */
    private void updateStylistSelection(Button selectedButton) {
        if (selectedStylistButton != null) {
            selectedStylistButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            selectedStylistButton.setTextColor(getResources().getColor(android.R.color.black));
        }

        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
        selectedStylistButton = selectedButton;
    }
    public void back(View view) {
        finish(); // back to previous page
    }
}
