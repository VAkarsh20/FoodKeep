package com.example.foodkeepproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements GroceryFragment.GroceryListener, PantryFragment.PantryListener, SettingsFragment.SettingsListener {

    final PantryFragment pantryFragment = new PantryFragment();
    final GroceryFragment groceryFragment = new GroceryFragment();
    final SettingsFragment settingsFragment = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = pantryFragment;

    private TextView headerText;
    private ImageButton pantryEnterConsume;
    private ImageButton pantryExitConsume;

    private ImageButton groceryEnterRemove;
    private ImageButton groceryExitRemove;

    private boolean consumeMode = false;
    private boolean removeMode = false;

    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.contentFrame, settingsFragment, "settings").hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.contentFrame, groceryFragment, "grocery").hide(groceryFragment).commit();
        fm.beginTransaction().add(R.id.contentFrame, pantryFragment, "pantry").commit();

        headerText = (TextView) findViewById(R.id.headerTitle);
        pantryEnterConsume = (ImageButton) findViewById(R.id.pantryEnterConsume);
        pantryExitConsume = (ImageButton) findViewById(R.id.pantryExitConsume);

        groceryEnterRemove = (ImageButton) findViewById(R.id.groceryEnterRemove);
        groceryExitRemove = (ImageButton) findViewById(R.id.groceryExitRemove);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigationBar);

        nav.setOnNavigationItemSelectedListener(
                item -> {
                    if (active == pantryFragment) {
                        if (consumeMode) {
                            pantryExitConsume.setVisibility(View.GONE);
                        } else {
                            pantryEnterConsume.setVisibility(View.GONE);
                        }
                    } else if (active == groceryFragment) {
                        if (removeMode) {
                            groceryExitRemove.setVisibility(View.GONE);
                        } else {
                            groceryEnterRemove.setVisibility(View.GONE);
                        }
                    }
                    switch (item.getItemId()) {
                        case R.id.pantry:
                            fm.beginTransaction().hide(active).show(pantryFragment).commit();
                            active = pantryFragment;
                            headerText.setText("Pantry");

                            if (consumeMode) {
                                pantryExitConsume.setVisibility(View.VISIBLE);
                            } else {
                                pantryEnterConsume.setVisibility(View.VISIBLE);
                            }

                            break;
                        case R.id.grocery:
                            fm.beginTransaction().hide(active).show(groceryFragment).commit();
                            active = groceryFragment;
                            headerText.setText("Grocery List");

                            if (removeMode) {
                                groceryExitRemove.setVisibility(View.VISIBLE);
                            } else {
                                groceryEnterRemove.setVisibility(View.VISIBLE);
                            }

                            break;
                        case R.id.settings:
                            fm.beginTransaction().hide(active).show(settingsFragment).commit();
                            active = settingsFragment;
                            headerText.setText("Settings");
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

    public void onPantryEnterConsumeClick(View view) {
        pantryEnterConsume.setVisibility(View.GONE);
        consumeMode = true;
        pantryFragment.enterConsumeMode();
        pantryExitConsume.setVisibility(View.VISIBLE);
    }

    public void onPantryExitConsumeClick(View view) {
        pantryExitConsume.setVisibility(View.GONE);
        consumeMode = false;
        pantryFragment.exitConsumeMode();
        pantryEnterConsume.setVisibility(View.VISIBLE);
    }

    public void onGroceryEnterRemoveClick(View view) {
        groceryEnterRemove.setVisibility(View.GONE);
        removeMode = true;
        groceryFragment.enterRemoveMode();
        groceryExitRemove.setVisibility(View.VISIBLE);
    }

    public void onGroceryExitRemoveClick(View view) {
        groceryExitRemove.setVisibility(View.GONE);
        removeMode = false;
        groceryFragment.exitRemoveMode();
        groceryEnterRemove.setVisibility(View.VISIBLE);
    }

    @Override
    public void onButtonClick() {
        count++;
        groceryFragment.updateCount(count);
        pantryFragment.updateCount(count);
        settingsFragment.updateCount(count);
    }
}