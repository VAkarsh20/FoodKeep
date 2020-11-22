package com.example.foodkeepproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    SettingsListener callback;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SwitchMaterial shoppingAddPantrySwitch = (SwitchMaterial) view.findViewById(R.id.shoppingAddPantrySwitch);
        shoppingAddPantrySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingAddPantrySwitch.isChecked()) {
                    callback.onSetShoppingPantryAddSetting(true);
                } else {
                    callback.onSetShoppingPantryAddSetting(false);
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public interface SettingsListener {
        public void onSetShoppingPantryAddSetting(boolean setting);
    }

    public void setSettingsListener(SettingsListener callback) {
        this.callback = callback;
    }

    public void updateCount(int count) {
        View view = getView();
        //((TextView) view.findViewById(R.id.settings_count)).setText(Integer.toString(count));
    }
}