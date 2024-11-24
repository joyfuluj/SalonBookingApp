package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        root = FirebaseDatabase.getInstance().getReference();
//        root.setValue("HelloWorld");
    }

    protected void onButton1Pressed(Bundle savedInstanceState){
        root.setValue("HelloWorld");
    }

    public void onClick (View view){
        EditText username = findViewById(R.id.username);
        String user = username.getText().toString().trim();
        if(user.equals("staff")){
            Intent intent = new Intent(this, MyPageforStaff.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
        }
    }
}