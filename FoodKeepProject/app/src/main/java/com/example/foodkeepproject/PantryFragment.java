package com.example.foodkeepproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantryFragment extends Fragment {

    PantryListener callback;

    public PantryFragment() {
        // Required empty public constructor
    }

    public static PantryFragment newInstance(String param1, String param2) {
        PantryFragment fragment = new PantryFragment();
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
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        View view = getView();
        ((TextView) view.findViewById(R.id.pantry_count)).setText(Integer.toString(mainActivity.count));

        Button button = view.findViewById(R.id.pantry_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callback.onButtonClick();
            }
        });
    }

    public interface PantryListener {
        public void onButtonClick();
    }

    public void setPantryListener(PantryListener callback) {
        this.callback = callback;
    }

    public void updateCount(int count) {
        View view = getView();
        ((TextView) view.findViewById(R.id.pantry_count)).setText(Integer.toString(count));
    }
}