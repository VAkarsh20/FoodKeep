package com.example.foodkeepproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final PantryFragment pantryFragment = new PantryFragment();
    final GroceryFragment groceryFragment = new GroceryFragment();
    final SettingsFragment settingsFragment = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = pantryFragment;

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
}