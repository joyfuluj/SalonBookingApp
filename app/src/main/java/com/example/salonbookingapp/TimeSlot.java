package com.example.salonbookingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class TimeSlot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_slot);

        Intent intent = getIntent();
        String day = intent.getStringExtra("date");
        String year = intent.getStringExtra("year");
        String file = intent.getStringExtra("file");
        TextView date = findViewById(R.id.day);
        date.setText(year+"/"+day);

        LinearLayout mainLinearLayout = findViewById(R.id.status);

        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line=br.readLine();


            for (int i = 0; i < 15 ; i++){
                line = br.readLine();
                if(line == null){
                    break;
                }
                String[] words = line.split(",\\s*");

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

                TextView time = new TextView(this);
                Button bookingStatus = new Button(this);
                Switch ava = new Switch(this);

                bookingStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                bookingStatus.setLayoutParams(booking);
                ava.setLayoutParams(booking);

                time.setText(words[1]);
                time.setTextSize(28f);
                horizontalLayout.addView(time);

                if(words[3].equals("0")){
                    bookingStatus.setText("-");
                }
                else{bookingStatus.setText("⭕");}
                horizontalLayout.addView(bookingStatus);

                if(words[4].equals("1")) {
                    ava.setChecked(true);
                }
                else{ava.setChecked(false);}
                horizontalLayout.addView(ava);

                mainLinearLayout.addView(horizontalLayout);
                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2  // Height of the line (divider)
                ));
                divider.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                mainLinearLayout.addView(divider);

                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(words[3].equals("1")) {
                            Intent intent = new Intent(TimeSlot.this, BookingDetails.class);
                            intent.putExtra("date", day);
                            intent.putExtra("year", year);
                            intent.putExtra("time", words[1]);
                            startActivity(intent);
                        }
                        else{CharSequence text ="";
                            int duration = Toast.LENGTH_SHORT;
                            text = "No booking";
                            Toast toast = Toast.makeText(TimeSlot.this, text, duration);
                            toast.show();
                        }
                    }
                });
                /*bookingStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String status="";
                        String statusNum="";
                        if(words[3].equals("0")){
                            status = "⭕";
                            statusNum = "1";
                        }
                        else{
                            status = "-";
                            statusNum = "0";
                        }
                        bookingStatus.setText(status);
                        words[3]=statusNum;
                    }
                });*/
                ava.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String status="";
                        String statusNum="";
                        if(words[4].equals("0")){
                            ava.setChecked(true);
                            words[4]="1";
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TimeSlot.this);
                            builder.setCancelable(true);
                            builder.setTitle("Update");
                            builder.setMessage("Are you sure you want to set time slot unavailable?");

                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    ava.setChecked(false);
                                    bookingStatus.setText("-");
                                    words[4] = "0";
                                    words[3] = "0";
                                }
                            });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    ava.setChecked(true);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
            br.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void back(View v){
        finish();
    }
}