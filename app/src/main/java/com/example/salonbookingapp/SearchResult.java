package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchResult extends AppCompatActivity {
    String username;
    String salonName;
    String rating;

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
        String file2 = "review.txt";
        try {
            // Open the salon file once
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {

                String[] words = line.split(",\\s*");
                salonName = words[0];
                String[] servicesArray = words[3].split(";");
                String services = String.join(", ", servicesArray);


                if (!salonName.equals("Name")) {
                    if (checkServiceArray(servicesArray, serviceArr)) {
                        LinearLayout horizontalLayout = new LinearLayout(this);
                        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        horizontalLayoutParams.setMargins(16, 20, 16, 20);
                        horizontalLayout.setLayoutParams(horizontalLayoutParams);

                        String imageName = getSalonImage(salonName, file);
                        ImageView imageView = new ImageView(this);
                        int resId = getResources().getIdentifier(imageName.replace(".jpeg", ""), "drawable", getPackageName());
                        if (resId != 0) {
                            imageView.setImageResource(resId);
                        } else {
                            imageView.setImageResource(R.drawable.default_salon);
                        }
                        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(200, 250);
                        imageParams.setMargins(0, 50, 16, 0);
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


                        String rating = calculateRating(salonName, file2);
                      
                        TextView ratingTextView = new TextView(this);
                        ratingTextView.setText("⭐️" + rating);
                        ratingTextView.setTextSize(30f);
                        rightLayout.addView(ratingTextView);

                        Button detailButton = new Button(this);
                        detailButton.setText("Detail");
                        detailButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green));
                        detailButton.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

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

    public String getSalonImage(String salonName, String file) {
        String imageName = "";
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                if (words[0].equals(salonName)) {
                    imageName = words[4];
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageName;
    }

    public String calculateRating(String salonName, String filename) {
        float sum = 0f;
        int count = 0;
        String line;

        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");

                if (words[0].equals(salonName)) {
                    sum += Float.parseFloat(words[1]);
                    count++;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (count == 0) {
            return "NA";
        }

        return String.format("%.1f", sum / count);
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
