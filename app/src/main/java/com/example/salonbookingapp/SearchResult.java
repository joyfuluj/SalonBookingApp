package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchResult extends AppCompatActivity {
    String username;
    String salonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        String[] hairServices = {"Cut", "Perm", "Hair Styling", "Haircuts"};
        String[] nailServices = {"Nail", "Manicure"};
        String[] EyelashServices = {"Eyelash"};
        String[] relaxationServices = {"Relaxation"};
        String searchTerm = getIntent().getStringExtra("search");

        TextView search_result = findViewById(R.id.textView2);
        search_result.setText(searchTerm);
        String[] serviceArr;

        switch (searchTerm) {
            case "Hair":
                serviceArr = hairServices;
                break;
            case "Nail":
                serviceArr = nailServices;
                break;
            case "Eyelash":
                serviceArr = EyelashServices;
                break;
            case "Relaxation":
                serviceArr = relaxationServices;
                break;
            default:
                serviceArr = new String[]{searchTerm};
                break;
        }

        LinearLayout mainLinearLayout = findViewById(R.id.firstLayout);
        String file = "salon.txt";
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {

                String[] words = line.split(",\\s*");
                salonName = words[0];
                String[] servicesArray = words[3].split(";");
                String services = String.join(", ", servicesArray);
                String rating = words[5];



                if(!salonName.equals("Name")) {
                    if(checkServiceArray(servicesArray, serviceArr)) {
                        LinearLayout horizontalLayout = new LinearLayout(this);
                        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        horizontalLayoutParams.setMargins(16, 20, 16, 20);
                        horizontalLayout.setLayoutParams(horizontalLayoutParams);

                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(R.drawable.baseline_person_24);
                        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(120, 130);
                        imageParams.setMargins(0, 0, 16, 0);
                        imageView.setLayoutParams(imageParams);
                        horizontalLayout.addView(imageView);

                        LinearLayout textLayout = new LinearLayout(this);
                        textLayout.setOrientation(LinearLayout.VERTICAL);
                        textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                        ));


                        TextView salonTextView = new TextView(this);
                        salonTextView.setText(salonName);
                        salonTextView.setTextSize(28f);
                        textLayout.addView(salonTextView);

                        TextView serviceLabelTextView = new TextView(this);
                        serviceLabelTextView.setText("Service:");
                        serviceLabelTextView.setTextSize(20f);
                        textLayout.addView(serviceLabelTextView);

                        TextView servicesTextView = new TextView(this);
                        servicesTextView.setText(services);
                        servicesTextView.setTextSize(25f);
                        textLayout.addView(servicesTextView);

                        horizontalLayout.addView(textLayout);

                        LinearLayout rightLayout = new LinearLayout(this);
                        rightLayout.setOrientation(LinearLayout.VERTICAL);
                        rightLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        TextView ratingTextView = new TextView(this);
                        ratingTextView.setText("⭐️" + rating);
                        ratingTextView.setTextSize(30f);
                        rightLayout.addView(ratingTextView);

                        Button detailButton = new Button(this);
                        detailButton.setText("detail");
                        detailButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 80, 0, 0);
                        detailButton.setLayoutParams(params);

                        final String currentSalonName = salonName;
                        detailButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchResult.this, SalonDetail.class);
                                intent.putExtra("salonName", currentSalonName);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                        });
                        rightLayout.addView(detailButton);
                        horizontalLayout.addView(rightLayout);
                        mainLinearLayout.addView(horizontalLayout);
                        View divider = new View(this);
                        divider.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                2  // Height of the line (divider)
                        ));
                        divider.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                        mainLinearLayout.addView(divider);
                    }
                }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean checkServiceArray(String[] services, String[] keyArr) {
        for (String service : services) {
            for (String key : keyArr) {
                if (service.equalsIgnoreCase(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void back(View v){
        finish();
    }

}