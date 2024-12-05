package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Review extends AppCompatActivity {
    String salonName;
    String salonNameFile;
    String username;
    String serviceName;
    String all;
    String customer;
    String price;
    String serviceQuality;
    String environment;
    String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        salonName = intent.getStringExtra("salonName");
        username = intent.getStringExtra("username");

        TextView salon = findViewById(R.id.textView7);
        salon.setText(salonName);

        TextView overall1 = findViewById(R.id.textView6);
        RatingBar customerRating = findViewById(R.id.ratingBar6);
        TextView allCusService = findViewById(R.id.textView8);
        TextView allPrice = findViewById(R.id.textView9);
        TextView allserviceQuality = findViewById(R.id.textView10);
        TextView allEnvironment = findViewById(R.id.textView11);
        float sumAll = 0f;
        float sumService = 0f;
        float sumPrice = 0f;
        float sumQuality = 0f;
        float sumEnvironment = 0f;
        int count = 0;
        String reviewUser;

        LinearLayout mainLinearLayout = findViewById(R.id.firstLayout);

        String file = "review.txt";
        try {
            FileInputStream fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {

                String[] words = line.split(",\\s*");
                salonNameFile = words[0];
                if(salonNameFile.equals(salonName)) {
                    all = words[1];
                    customer = words[2];
                    price = words[3];
                    serviceQuality = words[4];
                    environment = words[5];
                    reviewUser = words[6];
                    comment = words[7];
                    serviceName = words[8];


                    LinearLayout verticalLayout = new LinearLayout(this);
                    verticalLayout.setOrientation(LinearLayout.VERTICAL);
                    verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    LinearLayout horizontal1Layout = new LinearLayout(this);
                    horizontal1Layout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams horizontal1LayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    horizontal1LayoutParams.setMargins(60, 20, 16, 20);
                    horizontal1Layout.setLayoutParams(horizontal1LayoutParams);

                    TextView usernameView = new TextView(this);
                    usernameView.setText("username: " + reviewUser);
                    usernameView.setTextSize(15f);
                    horizontal1Layout.addView(usernameView);

                    LinearLayout.LayoutParams ratingParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    ratingParams.setMargins(550, 0, 0, 0);

                    TextView ratingTextView = new TextView(this);
                    ratingTextView.setText("⭐️" + all);
                    ratingTextView.setTextSize(15f);
                    ratingTextView.setLayoutParams(ratingParams);
                    horizontal1Layout.addView(ratingTextView);

                    verticalLayout.addView(horizontal1Layout);

                    LinearLayout.LayoutParams serviceNameParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    serviceNameParams.setMargins(60, 20, 16, 20);

                    TextView serviceNameView = new TextView(this);
                    serviceNameView.setText("Service Name: " + serviceName);
                    serviceNameView.setTextSize(15f);
                    serviceNameView.setLayoutParams(serviceNameParams);
                    verticalLayout.addView(serviceNameView);

                    LinearLayout horizontal2Layout = new LinearLayout(this);
                    horizontal2Layout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams horizontal2LayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    horizontal2LayoutParams.setMargins(60, 20, 16, 20);
                    horizontal2Layout.setLayoutParams(horizontal2LayoutParams);

                    TextView customerServiceView = new TextView(this);
                    customerServiceView.setText("Customer Service: " + customer);
                    customerServiceView.setTextSize(15f);
                    horizontal2Layout.addView(customerServiceView);

                    LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    priceParams.setMargins(250, 0, 0, 0);

                    TextView priceView = new TextView(this);
                    priceView.setText("Price: " + price);
                    priceView.setTextSize(15f);
                    priceView.setLayoutParams(priceParams);
                    horizontal2Layout.addView(priceView);

                    verticalLayout.addView(horizontal2Layout);

                    LinearLayout horizontal3Layout = new LinearLayout(this);
                    horizontal3Layout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams horizontal3LayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    horizontal3LayoutParams.setMargins(60, 20, 16, 20);
                    horizontal3Layout.setLayoutParams(horizontal3LayoutParams);

                    TextView serviceQualityView = new TextView(this);
                    serviceQualityView.setText("Service Quality: " + serviceQuality);
                    serviceQualityView.setTextSize(15f);
                    horizontal3Layout.addView(serviceQualityView);

                    LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    rightParams.setMargins(300, 0, 0, 0);

                    TextView environmentView = new TextView(this);
                    environmentView.setText("Environment: " + environment);
                    environmentView.setTextSize(15f);
                    environmentView.setLayoutParams(rightParams);
                    horizontal3Layout.addView(environmentView);

                    verticalLayout.addView(horizontal3Layout);

                    LinearLayout.LayoutParams commentParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    commentParams.setMargins(60, 20, 16, 20);

                    TextView commentView = new TextView(this);
                    commentView.setText("Comment: \n" + comment);
                    commentView.setTextSize(15f);
                    commentView.setLayoutParams(commentParams);
                    verticalLayout.addView(commentView);

                    mainLinearLayout.addView(verticalLayout);

                    View divider = new View(this);
                    divider.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2  // Height of the line (divider)
                    ));
                    divider.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                    mainLinearLayout.addView(divider);

                    sumAll += Float.parseFloat(words[1]);
                    sumService += Float.parseFloat(words[2]);
                    sumPrice += Float.parseFloat(words[3]);
                    sumQuality += Float.parseFloat(words[4]);
                    sumEnvironment += Float.parseFloat(words[5]);
                    count++;
                }
            }
            if (count > 0) {
                customerRating.setRating(Float.parseFloat(String.format("%.1f", sumAll / count)));
                overall1.setText(String.format("%.1f", sumAll / count));
                allCusService.setText("Customer Service: " + String.format("%.1f", sumService / count));
                allPrice.setText("Price: " + String.format("%.1f",sumPrice / count));
                allserviceQuality.setText("Service Quality: " + String.format("%.1f",sumQuality / count));
                allEnvironment.setText("Environment: " + String.format("%.1f",sumEnvironment / count));

            } else {
                customerRating.setRating(0f);
                overall1.setText("N/A");
            }

            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void back(View v){
        finish();
    }

    public void onClickWrite(View v){
        Intent intent = new Intent(this, WriteReview.class);
        intent.putExtra("username", username);
        intent.putExtra("salonName", salonName);
        startActivity(intent);
    }
}
