package com.example.salonbookingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.salonbookingapp.databinding.ActivitySearchBinding;

public class Search extends AppCompatActivity {
    ActivitySearchBinding binding;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the username passed via the Intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Initialize and replace the fragment only once
        if (savedInstanceState == null) {
            // Initially load the SearchFragment
            SearchFragment fragment = SearchFragment.newInstance("param1Value", "param2Value", username);
            replaceFragment(fragment);
        }

        // Set up bottom navigation listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.search:
                    selectedFragment = new SearchFragment();  // Show SearchFragment
                    break;
                case R.id.hotpick:
                    selectedFragment = new HotPickFragment();  // Show HotPickFragment
                    break;
                case R.id.home:
                    selectedFragment = new HomeFragment();  // Show HomeFragment
                    break;
            }

            // Replace the fragment in the container
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }

            return true;
        });
    }

    // Method to replace the fragment in the container
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
