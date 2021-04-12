package com.example.noteapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(HomeFragment.newInstance(), true);
    }

    void replaceFragment(Fragment fragment, Boolean isTransition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(isTransition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }
}