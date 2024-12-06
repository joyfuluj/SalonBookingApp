package com.example.salonbookingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_details);

        Intent intent = getIntent();
        String day = intent.getStringExtra("date");
        String year = intent.getStringExtra("year");
        String time = intent.getStringExtra("time");
        String staff = intent.getStringExtra("staff");
        TextView date = findViewById(R.id.day);
        TextView timeView = findViewById(R.id.time);
        date.setText(year+"/"+day);
        timeView.setText(time);

        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);
        TextView phone = findViewById(R.id.phone);
        TextView menu = findViewById(R.id.menu);
        TextView stylist = findViewById(R.id.stylist);
        TextView remark = findViewById(R.id.special);

        try {
            String file = "reservations.txt";
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line ;

            while((line = br.readLine()) !=null){
                String[] words = line.split(",\\s*");
                if(words[3].equals(day) && words[4].equals(time) && words[2].equals(staff)){
                    name.setText("Name: "+ words[5]);
                    email.setText("Email: "+ words[7]);
                    phone.setText("Phone Number: "+ words[6]);
                    menu.setText("Menu: "+ words[1]);
                    stylist.setText("Stylist: "+ staff);
                    if(words[8].equals("")){
                        remark.setText("Special Remark: N/A");
                    }
                    else
                        remark.setText("Special Remark: "+ words[8]);
                }
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
    public void delete(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("DELETE");
        builder.setMessage("Are you sure you want to delete this booking?");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = getIntent();
                    String day = intent.getStringExtra("date");
                    String time = intent.getStringExtra("time");
                    String fileT = intent.getStringExtra("fileT");
                    String staff = intent.getStringExtra("staff");
                    String year = intent.getStringExtra("year");
                    String file = "reservations.txt";

                    FileInputStream fis3 = openFileInput(file);
                    InputStreamReader isr3 = new InputStreamReader(fis3);
                    BufferedReader br3 = new BufferedReader(isr3);
                    String line3;

                    StringBuilder updatedContent3 = new StringBuilder();
                    while ((line3 = br3.readLine()) != null){
                        String[] words3 = line3.split(",\\s*");
                        if(words3[3].equals(day) && words3[4].equals(time) && words3[2].equals(staff)){
                        }
                        else{
                            updatedContent3.append(line3).append("\n");
                        }
                    }
                    br3.close();
                    fis3.close();

                    FileOutputStream fos3 = openFileOutput(file, Context.MODE_PRIVATE);
                    fos3.write(updatedContent3.toString().getBytes());
                    fos3.close();

                    FileInputStream fis2 = openFileInput(fileT);
                    InputStreamReader isr2 = new InputStreamReader(fis2);
                    BufferedReader br2 = new BufferedReader(isr2);

                    StringBuilder updatedContent = new StringBuilder();
                    String line2;
                    while ((line2 = br2.readLine()) != null) {
                        String[] words2 = line2.split(",\\s*");
                        if (words2[1].equals(time) && words2[0].equals(day)) {
                            updatedContent.append(day).append(",").append(time).append(",").append(words2[2]).append(",").append("0").append(",").append(words2[4]).append("\n");
                        } else {
                            updatedContent.append(line2).append("\n");
                        }
                    }
                    br2.close();
                    fis2.close();

                    FileOutputStream fos = openFileOutput(fileT, Context.MODE_PRIVATE);
                    fos.write(updatedContent.toString().getBytes());
                    fos.close();

                    Toast.makeText(BookingDetails.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();

                    Intent intent2 = new Intent(BookingDetails.this, MyPageforStaff.class);
                    intent2.putExtra("username", staff);
                    startActivity(intent2);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
