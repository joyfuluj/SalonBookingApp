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
        String file = "dayschedule1.txt";
        String staff = "for staff 1";
        intent.putExtra("file",file);
        intent.putExtra("staff",staff);
        startActivity(intent);
    }
    public void two(View v){
        Intent intent = new Intent(this, SearchMonth.class);
        String file = "schedule2.txt";
        intent.putExtra("file",file);
        String staff = "for staff 2";
        intent.putExtra("staff",staff);
        startActivity(intent);
    }
    public void three(View v){
        Intent intent = new Intent(this, SearchMonth.class);
        String file = "schedule3.txt";
        intent.putExtra("file",file);
        String staff = "for staff 3";
        intent.putExtra("staff",staff);
        startActivity(intent);
    }
    public void back(View v){
        finish();
    }
}