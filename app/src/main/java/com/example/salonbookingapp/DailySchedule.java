package com.example.salonbookingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DailySchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_schedule);

        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String file = intent.getStringExtra("file");

        TextView m = findViewById(R.id.month);
        m.setText(month);

        LinearLayout mainLinearLayout = findViewById(R.id.status);


        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                final String dayMonth = words[0];
                final String dayStatus = words[1];

                LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                horizontalLayoutParams.setMargins(16, 20, 16, 20);
                horizontalLayout.setLayoutParams(horizontalLayoutParams);

                LinearLayout.LayoutParams b = new LinearLayout.LayoutParams(
                        300, 150
                );
                LinearLayout.LayoutParams s = new LinearLayout.LayoutParams(
                        300, 150
                );
                b.setMargins(180, 20, 5, 20);
                s.setMargins(35, 20, 10, 20);
                TextView day = new TextView(this);
                Button TimeSlot = new Button(this);
                Switch TimeOff = new Switch(this);

                TimeSlot.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                TimeSlot.setLayoutParams(b);
                TimeOff.setLayoutParams(s);

                day.setText(words[0]);
                day.setTextSize(28f);
                horizontalLayout.addView(day);

                if (dayStatus.equals("0")) {
                    TimeSlot.setText("-");
                } else {
                    TimeSlot.setText("TIME SLOT");
                }
                TimeSlot.setTextSize(20f);
                horizontalLayout.addView(TimeSlot);

                if (words[1].equals("1")) {
                    TimeOff.setChecked(true);
                } else {
                    TimeOff.setChecked(false);
                }
                horizontalLayout.addView(TimeOff);

                mainLinearLayout.addView(horizontalLayout);
                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2  // Height of the line (divider)
                ));
                divider.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                mainLinearLayout.addView(divider);

                TimeSlot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dayStatus.equals("0")) {
                            CharSequence text = "No data yet";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(DailySchedule.this, text, duration);
                            toast.show();
                        } else {
                            String fileT = intent.getStringExtra("fileT");
                            Intent intent = new Intent(DailySchedule.this, TimeSlot.class);
                            intent.putExtra("date", dayMonth);
                            intent.putExtra("year", year);
                            intent.putExtra("fileT", fileT);
                            startActivity(intent);
                        }
                    }
                });

                TimeOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DailySchedule.this);
                        builder.setCancelable(true);
                        builder.setTitle("Update");
                        if(words[1].equals("1")){
                            builder.setMessage("Are you sure you want to set this day unavailable?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TimeOff.setChecked(false);
                                    TimeSlot.setText("-");

                                    try {
                                        FileInputStream fis2 = openFileInput(file);
                                        InputStreamReader isr2 = new InputStreamReader(fis2);
                                        BufferedReader br2 = new BufferedReader(isr2);

                                        StringBuilder updatedContent = new StringBuilder();
                                        String line2;
                                        while ((line2 = br2.readLine()) != null) {
                                            String[] words2 = line2.split(",\\s*");
                                            if (words2[0].equals(dayMonth)) {
                                                updatedContent.append(words2[0]).append(",").append("0").append("\n");
                                            } else {
                                                updatedContent.append(line2).append("\n");
                                            }
                                        }
                                        br2.close();
                                        fis2.close();

                                        FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
                                        fos.write(updatedContent.toString().getBytes());
                                        fos.close();

                                        Toast.makeText(DailySchedule.this, "Day status updated", Toast.LENGTH_SHORT).show();
                                        recreate();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TimeOff.setChecked(true);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            builder.setMessage("Are you sure you want to set this day available?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TimeOff.setChecked(true);
                                    TimeSlot.setText("TIME SLOT");

                                    try {
                                        FileInputStream fis2 = openFileInput(file);
                                        InputStreamReader isr2 = new InputStreamReader(fis2);
                                        BufferedReader br2 = new BufferedReader(isr2);

                                        StringBuilder updatedContent = new StringBuilder();
                                        String line2;
                                        while ((line2 = br2.readLine()) != null) {
                                            String[] words2 = line2.split(",\\s*");
                                            if (words2[0].equals(dayMonth)) {
                                                updatedContent.append(words2[0]).append(",").append("1").append("\n");
                                            } else {
                                                updatedContent.append(line2).append("\n");
                                            }
                                        }
                                        br2.close();
                                        fis2.close();

                                        FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
                                        fos.write(updatedContent.toString().getBytes());
                                        fos.close();

                                        Toast.makeText(DailySchedule.this, "Day status updated", Toast.LENGTH_SHORT).show();
                                        recreate();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TimeOff.setChecked(true);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void back (View v){
        finish();
    }
}