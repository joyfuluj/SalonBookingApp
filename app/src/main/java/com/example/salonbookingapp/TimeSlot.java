package com.example.salonbookingapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class TimeSlot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_slot);

        Intent intent = getIntent();
        String day = intent.getStringExtra("date");
        String year = intent.getStringExtra("year");
        String fileT = intent.getStringExtra("fileT");
        String staff = intent.getStringExtra("staff");
        TextView date = findViewById(R.id.day);
        date.setText(year+"/"+day);

        LinearLayout mainLinearLayout = findViewById(R.id.status);
        try {
            FileInputStream fis = openFileInput(fileT);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line ;

            while((line = br.readLine()) !=null){
                String[] words = line.split(",\\s*");

                if(!words[0].trim().equals(day)){continue;}

                final String time = words[1];
                final String bookStatus = words[3];
                final String ava = words[4];

                LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                horizontalLayoutParams.setMargins(16, 20, 16, 20);
                horizontalLayout.setLayoutParams(horizontalLayoutParams);

                LinearLayout.LayoutParams booking = new LinearLayout.LayoutParams(
                        150,150
                );
                booking.setMargins(170, 20, 16, 20);


                TextView timeV = new TextView(this);
                Button bookingStatus = new Button(this);
                Switch avaV = new Switch(this);


                bookingStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                bookingStatus.setLayoutParams(booking);
                avaV.setLayoutParams(booking);


                timeV.setText(words[1]);
                timeV.setTextSize(28f);
                horizontalLayout.addView(timeV);


                if (bookStatus.equals("0")) {
                    bookingStatus.setText("-");
                }
                else{bookingStatus.setText("⭕");}


                bookingStatus.setTextSize(20f);
                horizontalLayout.addView(bookingStatus);


                if (ava.equals("1")) {
                    avaV.setChecked(true);
                } else {
                    avaV.setChecked(false);
                }
                horizontalLayout.addView(avaV);


                mainLinearLayout.addView(horizontalLayout);
                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2  // Height of the line (divider)
                ));
                divider.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                mainLinearLayout.addView(divider);


                bookingStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bookStatus.equals("0")){Toast.makeText(TimeSlot.this, "No booking", Toast.LENGTH_SHORT).show();}
                        else {
                            Intent intent = new Intent(TimeSlot.this, BookingDetails.class);
                            intent.putExtra("date", day);
                            intent.putExtra("year", year);
                            intent.putExtra("time", time);
                            intent.putExtra("staff", staff);
                            intent.putExtra("fileT", fileT);
                            startActivity(intent);
                        }
                    }
                });

                avaV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TimeSlot.this);
                        builder.setCancelable(true);
                        builder.setTitle("Update");
                        if(ava.equals("1")){
                            builder.setMessage("Are you sure you want to set this time slot unavailable?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        FileInputStream fis2 = openFileInput(fileT);
                                        InputStreamReader isr2 = new InputStreamReader(fis2);
                                        BufferedReader br2 = new BufferedReader(isr2);

                                        StringBuilder updatedContent = new StringBuilder();
                                        String line2;
                                        while ((line2 = br2.readLine()) != null) {
                                            String[] words2 = line2.split(",\\s*");
                                            if (words2[1].equals(time) && words2[0].equals(day)) {
                                                updatedContent.append(day).append(",").append(time).append(",").append(words2[2]).append(",").append("0").append(",").append("0").append("\n");
                                            } else {
                                                updatedContent.append(line2).append("\n");
                                            }
                                        }
                                        br2.close();
                                        fis2.close();

                                        FileOutputStream fos = openFileOutput(fileT, Context.MODE_PRIVATE);
                                        fos.write(updatedContent.toString().getBytes());
                                        fos.close();



                                        avaV.setChecked(false);
                                        timeV.setText("-");
                                        Toast.makeText(TimeSlot.this, "Time status updated", Toast.LENGTH_SHORT).show();
                                        recreate();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    avaV.setChecked(true);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            builder.setMessage("Are you sure you want to set this time slot available?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    avaV.setChecked(true);

                                    try {
                                        FileInputStream fis2 = openFileInput(fileT);
                                        InputStreamReader isr2 = new InputStreamReader(fis2);
                                        BufferedReader br2 = new BufferedReader(isr2);


                                        StringBuilder updatedContent = new StringBuilder();
                                        String line2;
                                        while ((line2 = br2.readLine()) != null) {
                                            String[] words2 = line2.split(",\\s*");
                                            if (words2[1].equals(time)  && words2[0].equals(day)) {
                                                updatedContent.append(day).append(",").append(time).append(",").append(words2[2]).append(",").append("0").append(",").append("1").append("\n");

                                            } else {
                                                updatedContent.append(line2).append("\n");
                                            }
                                        }
                                        br2.close();
                                        fis2.close();

                                        FileOutputStream fos = openFileOutput(fileT, Context.MODE_PRIVATE);
                                        fos.write(updatedContent.toString().getBytes());
                                        fos.close();

                                        Toast.makeText(TimeSlot.this, "Time status updated", Toast.LENGTH_SHORT).show();
                                        recreate();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    avaV.setChecked(false);
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


    public void back(View v){
        finish();
    }
}