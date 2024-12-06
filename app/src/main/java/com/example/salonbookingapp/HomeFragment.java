package com.example.salonbookingapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String username;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2, String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            username = getArguments().getString("username");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout mainLinearLayout = view.findViewById(R.id.frame_layout);
        TextView setUser = view.findViewById(R.id.textView);
        setUser.setText("Hi, " + username + "!");
        setUser.setTextSize(28f);
        String file = "reservations.txt";

        try {
            String line;
            FileInputStream fis = requireContext().openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",\\s*");
                String salonName = words[0];
                String menu = words[1];
                String stylist = words[2];
                String date = words[3];
                String time = words[4];
                String user = words[10];
                String price = words[9];

                if (user.equals(username)) {
                    count++;
                    LinearLayout horizontalLayout = new LinearLayout(requireContext());
                    horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    LinearLayout textLayout = new LinearLayout(requireContext());
                    textLayout.setOrientation(LinearLayout.VERTICAL);
                    textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                    ));

                    LinearLayout.LayoutParams leftTop = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    leftTop.setMargins(50, 40, 0, 0);


                    LinearLayout.LayoutParams left = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    left.setMargins(50, 0, 0, 0);

                    TextView salonTextView = new TextView(requireContext());
                    salonTextView.setText(salonName);
                    salonTextView.setTextSize(28f);
                    textLayout.addView(salonTextView);
                    salonTextView.setLayoutParams(leftTop);

                    TextView serviceLabelTextView = new TextView(requireContext());
                    serviceLabelTextView.setText("Menu/Coupon: " + menu);
                    serviceLabelTextView.setTextSize(20f);
                    textLayout.addView(serviceLabelTextView);
                    serviceLabelTextView.setLayoutParams(left);

                    TextView ratingTextView = new TextView(requireContext());
                    ratingTextView.setText("Stylist: " + stylist);
                    ratingTextView.setTextSize(20f);
                    textLayout.addView(ratingTextView);
                    ratingTextView.setLayoutParams(left);

                    TextView dateTextView = new TextView(requireContext());
                    dateTextView.setText("Date: " + date);
                    dateTextView.setTextSize(20f);
                    textLayout.addView( dateTextView);
                    dateTextView.setLayoutParams(left);

                    TextView timeTextView = new TextView(requireContext());
                    timeTextView.setText("time: " + time);
                    timeTextView.setTextSize(20f);
                    textLayout.addView(timeTextView);
                    timeTextView.setLayoutParams(left);

                    horizontalLayout.addView(textLayout);

                    LinearLayout rightLayout = new LinearLayout(requireContext());
                    rightLayout.setOrientation(LinearLayout.VERTICAL);
                    rightLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    LinearLayout.LayoutParams priceLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    priceLayout.setMargins(0, 40, 40, 0);

                    TextView priceTextView = new TextView(requireContext());
                    priceTextView.setText(price);
                    priceTextView.setTextSize(28f);
                    rightLayout.addView(priceTextView);
                    priceTextView.setLayoutParams(priceLayout);

                    Button detailButton = new Button(requireContext());
                    detailButton.setText("Cancel");
                    detailButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_red));
                    detailButton.setOnClickListener(v -> {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Cancel")
                                .setMessage("Are you sure you want to cancel this reservation?")
                                .setPositiveButton("Confirm", (dialog, which) -> {
                                    try {
                                        FileInputStream fis2 = requireContext().openFileInput(file);
                                        InputStreamReader isr2 = new InputStreamReader(fis2);
                                        BufferedReader br2 = new BufferedReader(isr2);

                                        StringBuilder updatedContent = new StringBuilder();
                                        String line2;
                                        while ((line2 = br2.readLine()) != null) {
                                            String[] words2 = line2.split(",\\s*");
                                            if (!(words2[0].equals(salonName) && words2[3].equals(date) &&
                                                    words2[4].equals(time) && words2[10].equals(username))) {
                                                updatedContent.append(line2).append("\n");
                                            }
                                        }
                                        br2.close();
                                        fis2.close();

                                        FileOutputStream fos = requireContext().openFileOutput(file, Context.MODE_PRIVATE);
                                        fos.write(updatedContent.toString().getBytes());
                                        fos.close();
                                        getActivity().recreate();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .show();
                    });

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 190, 40, 0);
                    detailButton.setLayoutParams(params);
                    rightLayout.addView(detailButton);
                    horizontalLayout.addView(rightLayout);
                    mainLinearLayout.addView(horizontalLayout);

                    LinearLayout.LayoutParams div = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,  // Match parent width
                            2  // Height of the divider
                    );
                    div.setMargins(0, 40, 0, 0);  // Set top margin

                    View divider = new View(requireContext());
                    divider.setLayoutParams(div);  // Apply layout parameters
                    divider.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black));

                    mainLinearLayout.addView(divider);

                }
            }
            if(count == 0){
                LinearLayout horizontalLayout = new LinearLayout(requireContext());
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                TextView salonTextView = new TextView(requireContext());
                salonTextView.setText("Haven't made a reservation yet?\n\n      Reserve your spot today!");
                salonTextView.setTextSize(20f);
                horizontalLayout.addView(salonTextView);
                mainLinearLayout.addView(horizontalLayout);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(150, 80, 0, 0);
                salonTextView.setLayoutParams(params);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button logoutButton = view.findViewById(R.id.button13);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);

            SharedPreferences preferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}