package com.example.foodkeepproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantryFragment extends Fragment {

    final int ADD_PANTRY_ITEM = 1;
    ArrayList<PantryItem> pantryList;
    PantryListAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);

        pantryList = new ArrayList<>();
        pantryList.add(new PantryItem("Apple", 5));
        RecyclerView pantryItemsList = (RecyclerView) view.findViewById(R.id.pantryList);
        adapter = new PantryListAdapter(pantryList);
        pantryItemsList.setAdapter(adapter);
        pantryItemsList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PantryItemAddActivity.class);
                startActivityForResult(intent, ADD_PANTRY_ITEM);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        MainActivity mainActivity = (MainActivity) getActivity();
        View view = getView();

        Button addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PantryItemAddActivity.class);
                startActivityForResult(intent, ADD_PANTRY_ITEM);
            }
        });
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PANTRY_ITEM) {
            if (resultCode == RESULT_OK) {
                String itemName = data.getStringExtra("name");
                int itemCount = data.getIntExtra("count", 0);

                int index = indexPantryList(itemName);

                RecyclerView pantryListView = (RecyclerView) getView().findViewById(R.id.pantryList);

                if (index != -1) {
                    pantryList.get(index).addCount(itemCount);
                    adapter.notifyItemChanged(index);
                } else {
                    pantryList.add(0, new PantryItem(itemName, itemCount));
                    adapter.notifyItemInserted(0);
                    pantryListView.scrollToPosition(0);
                }
            }
        }
    }

    private int indexPantryList(String name) {
        for (int i = 0; i < pantryList.size(); i++) {
            if (pantryList.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public interface PantryListener {
        public void onButtonClick();
    }

    public void setPantryListener(PantryListener callback) {
        this.callback = callback;
    }

    public void updateCount(int count) {
        View view = getView();
        //((TextView) view.findViewById(R.id.pantry_count)).setText(Integer.toString(count));
    }
}