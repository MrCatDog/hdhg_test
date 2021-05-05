package com.example.jokeschucktest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.jokeschucktest.jokesfragment.JokesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public final static String TITLE_SAVE_TAG = "title";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationListener(this));
        if (savedInstanceState == null) {
            setTitle(JokesFragment.TITLE);
            changeFragment(new JokesFragment());
        } else {
            setTitle(savedInstanceState.getString(TITLE_SAVE_TAG));
        }
    }

    public int getSelectedMenuItem() {
        return bottomNavigationView.getSelectedItemId();
    }

    public void changeFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, newFragment).commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_SAVE_TAG, getTitle().toString());
    }

}
