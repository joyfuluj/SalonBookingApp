package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseStaff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_staff);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
     public void one(View v){
        Intent intent = new Intent(this, SearchMonth.class);
        String file = "days_schedule1.txt";
        String fileT = "schedule1.txt";
        String staff = "Stylist A";
        intent.putExtra("file",file);
        intent.putExtra("fileT",fileT);
        intent.putExtra("staff",staff);
        startActivity(intent);
    }
    public void two(View v){
        Intent intent = new Intent(this, SearchMonth.class);
        String fileT = "schedule2.txt";
        String file = "days_schedule2.txt";
        intent.putExtra("fileT",fileT);
        String staff = "Stylist B";
        intent.putExtra("staff",staff);
        intent.putExtra("file",file);
        startActivity(intent);
    }
    public void three(View v){
        Intent intent = new Intent(this, SearchMonth.class);
        String fileT = "schedule3.txt";
        String file = "days_schedule3.txt";
        intent.putExtra("fileT",fileT);
        intent.putExtra("file",file);
        String staff = "Stylist C";
        intent.putExtra("staff",staff);
        startActivity(intent);
    }
    public void back(View v){
        finish();
    }
}
