package com.example.jokeschucktest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.jokeschucktest.jokesfragment.JokesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationListener(this));
        if(savedInstanceState == null) {
            changeFragment(new JokesFragment());
        }
    }

    public int getSelectedMenuItem() {
        return bottomNavigationView.getSelectedItemId();
    }

    public void changeFragment(Fragment newFragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment_view, newFragment).commit();
    }

}
