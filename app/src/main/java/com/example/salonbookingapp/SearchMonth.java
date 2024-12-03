package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SearchMonth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_month);

        Intent intent = getIntent();
        String staff = intent.getStringExtra("staff");
        TextView staffNum = findViewById(R.id.staff);
        staffNum.setText(staff);

        Spinner y = (Spinner) findViewById(R.id.year);
        Spinner m = (Spinner) findViewById(R.id.month);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        y.setAdapter(adapter2);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void search(View v) {
        Spinner y = (Spinner) findViewById(R.id.year);
        Spinner m = (Spinner) findViewById(R.id.month);
        String year = y.getSelectedItem().toString();
        String month = m.getSelectedItem().toString();
        CharSequence text ="";
        int duration = Toast.LENGTH_SHORT;

        if(year.equals("2025") || !month.equals("December")){
            text = "No data yet";
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
        else{
            Intent intent = getIntent();
            String staff = intent.getStringExtra("staff");
            String file = intent.getStringExtra("file");
            String fileT = intent.getStringExtra("fileT");
            Intent newintent = new Intent(SearchMonth.this, DailySchedule.class);
            newintent.putExtra("year", year);
            newintent.putExtra("staff", staff);
            newintent.putExtra("month", month);
            newintent.putExtra("file", file);
            newintent.putExtra("fileT", fileT);
            startActivity(newintent);
        }
    }
    public void back(View v){
        finish();
    }
}