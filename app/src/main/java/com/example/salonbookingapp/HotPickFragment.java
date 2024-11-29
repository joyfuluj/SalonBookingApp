package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;

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
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotPickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotPickFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String username;

    public HotPickFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotPickFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotPickFragment newInstance(String param1, String param2, String username) {
        HotPickFragment fragment = new HotPickFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_pick, container, false);
        LinearLayout mainLinearLayout = view.findViewById(R.id.firstLayout);
        String file = "salon.txt";

        String[] high = {"0", "0", "0"};
        String[] salon = new String[3];

        try (FileInputStream fis = requireContext().openFileInput(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] words3 = line.split(",\\s*");
                String rating = words3[5];

                if (!rating.equals("NA") && !rating.equals("rating")) {
                    float ratingValue = Float.parseFloat(rating);

                    for (int i = 0; i < 3; i++) {
                        if (ratingValue > Float.parseFloat(high[i])) {
                            for (int j = 2; j > i; j--) {
                                high[j] = high[j - 1];
                                salon[j] = salon[j - 1];
                            }
                            high[i] = rating;
                            salon[i] = words3[0];
                            break;
                        }
                    }
                }
            }


        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        try {

            String line;
            for (int i = 0; i < 3; i++) {
                FileInputStream fis = requireContext().openFileInput(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(",\\s*");
                    String salonName = words[0];
                    String[] servicesArray = words[3].split(";");
                    String services = String.join(", ", servicesArray);

                    if (salonName.equals(salon[i])) {
                        LinearLayout horizontalLayout = new LinearLayout(requireContext());
                        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        ImageView imageView = new ImageView(requireContext());
                        imageView.setImageResource(R.drawable.baseline_person_24);
                        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(120, 130);
                        imageParams.setMargins(0, 0, 16, 0);
                        imageView.setLayoutParams(imageParams);
                        horizontalLayout.addView(imageView);

                        LinearLayout textLayout = new LinearLayout(requireContext());
                        textLayout.setOrientation(LinearLayout.VERTICAL);
                        textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                        ));

                        TextView salonTextView = new TextView(requireContext());
                        salonTextView.setText(salonName);
                        salonTextView.setTextSize(28f);
                        textLayout.addView(salonTextView);

                        TextView serviceLabelTextView = new TextView(requireContext());
                        serviceLabelTextView.setText("Service:");
                        serviceLabelTextView.setTextSize(20f);
                        textLayout.addView(serviceLabelTextView);

                        TextView servicesTextView = new TextView(requireContext());
                        servicesTextView.setText(services);
                        servicesTextView.setTextSize(25f);
                        textLayout.addView(servicesTextView);

                        horizontalLayout.addView(textLayout);

                        LinearLayout rightLayout = new LinearLayout(requireContext());
                        rightLayout.setOrientation(LinearLayout.VERTICAL);
                        rightLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        TextView ratingTextView = new TextView(requireContext());
                        ratingTextView.setText("⭐️" + high[i]);
                        ratingTextView.setTextSize(30f);
                        textLayout.addView(ratingTextView);

                        Button detailButton = new Button(requireContext());
                        detailButton.setText("detail");
                        detailButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_green));

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
                                Intent intent = new Intent(getActivity(), SalonDetail.class);
                                intent.putExtra("salonName", currentSalonName);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                        });
                        rightLayout.addView(detailButton);
                        horizontalLayout.addView(rightLayout);
                        mainLinearLayout.addView(horizontalLayout);
                        if(i != 2){
                            View divider = new View(requireContext());
                            divider.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    2  // Height of the line (divider)
                            ));
                            divider.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black));
                            mainLinearLayout.addView(divider);
                        }
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }


}