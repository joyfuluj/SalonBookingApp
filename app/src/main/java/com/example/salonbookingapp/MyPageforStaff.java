package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyPageforStaff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_pagefor_staff);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void salonPage(View v){
        Intent intent = new Intent(this, SalonDetail.class);
        startActivity(intent);
    }
    public void schedule(View v){
        Intent intent = new Intent(this, ChooseStaff.class);
        startActivity(intent);
    }
    public void profile(View v){
        Intent intent = new Intent(this, staffProfile.class);
        startActivity(intent);
    }
    public void back(View v){
        finish();
    }
}