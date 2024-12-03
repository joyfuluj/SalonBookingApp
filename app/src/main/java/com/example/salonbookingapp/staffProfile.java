package com.example.salonbookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class staffProfile extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        LinearLayout mainLinearLayout = findViewById(R.id.frame_layout);
        TextView setUser = findViewById(R.id.textView);
        setUser.setText("Hi, " + username + "!");
        setUser.setTextSize(28f);
        String file = "reservations.txt";

        try {
            String line;
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                String salonName = words[0];
                String menu = words[1];
                String stylist = words[2];
                String date = words[3];
                String time = words[4];
                String price = words[9];

                if (stylist.equals(username)) {
                    count++;
                    LinearLayout horizontalLayout = new LinearLayout(this);
                    horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    LinearLayout textLayout = new LinearLayout(this);
                    textLayout.setOrientation(LinearLayout.VERTICAL);
                    textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                    ));

                    LinearLayout.LayoutParams leftTop = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    leftTop.setMargins(50, 40, 0, 0);


                    LinearLayout.LayoutParams left = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    left.setMargins(50, 0, 0, 0);


                    TextView serviceLabelTextView = new TextView(this);
                    serviceLabelTextView.setText("Menu/Coupon: \n" + menu);
                    serviceLabelTextView.setTextSize(28f);
                    textLayout.addView(serviceLabelTextView);
                    serviceLabelTextView.setLayoutParams(leftTop);

                    TextView dateTextView = new TextView(this);
                    dateTextView.setText("Date: " + date);
                    dateTextView.setTextSize(28f);
                    textLayout.addView( dateTextView);
                    dateTextView.setLayoutParams(left);

                    TextView timeTextView = new TextView(this);
                    timeTextView.setText("time: " + time);
                    timeTextView.setTextSize(28f);
                    textLayout.addView(timeTextView);
                    timeTextView.setLayoutParams(left);

                    horizontalLayout.addView(textLayout);

                    LinearLayout rightLayout = new LinearLayout(this);
                    rightLayout.setOrientation(LinearLayout.VERTICAL);
                    rightLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    LinearLayout.LayoutParams priceLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    priceLayout.setMargins(0, 40, 40, 0);

                    TextView priceTextView = new TextView(this);
                    priceTextView.setText(price);
                    priceTextView.setTextSize(28f);
                    rightLayout.addView(priceTextView);
                    priceTextView.setLayoutParams(priceLayout);

                    Button detailButton = new Button(this);
                    detailButton.setText("Delete");
                    detailButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red));
                    detailButton.setOnClickListener(v -> {
                        new AlertDialog.Builder(this)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to delete this reservation?")
                                .setPositiveButton("Confirm", (dialog, which) -> {
                                    try {
                                        FileInputStream fis2 = this.openFileInput(file);
                                        InputStreamReader isr2 = new InputStreamReader(fis2);
                                        BufferedReader br2 = new BufferedReader(isr2);

                                        StringBuilder updatedContent = new StringBuilder();
                                        String line2;
                                        while ((line2 = br2.readLine()) != null) {
                                            String[] words2 = line2.split(",\\s*");
                                            if (!(words2[0].equals(salonName) && words2[3].equals(date) &&
                                                    words2[4].equals(time) && words2[10].equals(username))) {
                                                updatedContent.append(line2).append("\n");
                                            }
                                        }
                                        br2.close();
                                        fis2.close();

                                        FileOutputStream fos = this.openFileOutput(file, Context.MODE_PRIVATE);
                                        fos.write(updatedContent.toString().getBytes());
                                        fos.close();
                                        recreate();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .show();
                    });

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 90, 40, 0);
                    detailButton.setLayoutParams(params);
                    rightLayout.addView(detailButton);
                    horizontalLayout.addView(rightLayout);
                    mainLinearLayout.addView(horizontalLayout);

                    LinearLayout.LayoutParams div = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,  // Match parent width
                            2  // Height of the divider
                    );
                    div.setMargins(0, 40, 0, 0);  // Set top margin

                    View divider = new View(this);
                    divider.setLayoutParams(div);  // Apply layout parameters
                    divider.setBackgroundColor(ContextCompat.getColor(this, R.color.black));

                    mainLinearLayout.addView(divider);

                }
            }
            if(count == 0){
                LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                TextView salonTextView = new TextView(this);
                salonTextView.setText("No reservations yet, but you're doing great!\n\n         Get ready for your first booking!");
                salonTextView.setTextSize(20f);
                horizontalLayout.addView(salonTextView);
                mainLinearLayout.addView(horizontalLayout);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(20, 80, 0, 0);
                salonTextView.setLayoutParams(params);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button logoutButton = findViewById(R.id.button13);
        logoutButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, MainActivity.class);

            SharedPreferences preferences = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            startActivity(intent2);
            finish();
        });
    }
}