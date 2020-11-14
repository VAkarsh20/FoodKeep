package com.example.foodkeepproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements GroceryFragment.GroceryListener, PantryFragment.PantryListener, SettingsFragment.SettingsListener {

    final PantryFragment pantryFragment = new PantryFragment();
    final GroceryFragment groceryFragment = new GroceryFragment();
    final SettingsFragment settingsFragment = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = pantryFragment;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.contentFrame, settingsFragment, "settings").hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.contentFrame, groceryFragment, "grocery").hide(groceryFragment).commit();
        fm.beginTransaction().add(R.id.contentFrame, pantryFragment, "pantry").commit();

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigationBar);

        nav.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.pantry:
                            fm.beginTransaction().hide(active).show(pantryFragment).commit();
                            active = pantryFragment;
                            break;
                        case R.id.grocery:
                            fm.beginTransaction().hide(active).show(groceryFragment).commit();
                            active = groceryFragment;
                            break;
                        case R.id.settings:
                            fm.beginTransaction().hide(active).show(settingsFragment).commit();
                            active = settingsFragment;
                            break;
                    }
                    return true;
                }
        );
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof GroceryFragment) {
            GroceryFragment groceryFragment = (GroceryFragment) fragment;
            groceryFragment.setGroceryListener(this);
        }

        if (fragment instanceof PantryFragment) {
            PantryFragment pantryFragment = (PantryFragment) fragment;
            pantryFragment.setPantryListener(this);
        }

        if (fragment instanceof SettingsFragment) {
            SettingsFragment settingsFragment = (SettingsFragment) fragment;
            settingsFragment.setSettingsListener(this);
        }
    }

    @Override
    public void onButtonClick() {
        count++;
        groceryFragment.updateCount(count);
        pantryFragment.updateCount(count);
        settingsFragment.updateCount(count);
    }
}