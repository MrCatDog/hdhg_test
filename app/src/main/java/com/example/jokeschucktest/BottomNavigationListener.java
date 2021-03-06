package com.example.jokeschucktest;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.jokeschucktest.browserfragment.BrowserFragment;
import com.example.jokeschucktest.jokesfragment.JokesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final MainActivity mainActivity;

    BottomNavigationListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int previousItem = mainActivity.getSelectedMenuItem();
        final int nextItem = item.getItemId();
        Fragment fragment;
        if (previousItem != nextItem) {
            switch (nextItem) {
                case R.id.page_1:
                    fragment = new JokesFragment();
                    mainActivity.setTitle(JokesFragment.TITLE);
                    break;
                case R.id.page_2:
                    fragment = new BrowserFragment();
                    mainActivity.setTitle(BrowserFragment.TITLE);
                    break;
                default:
                    return false;
            }
            mainActivity.changeFragment(fragment);
        }
        return true;
    }
}
