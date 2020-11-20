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
 * Use the {@link GroceryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroceryFragment extends Fragment {

    final int ADD_GROCERY_ITEM = 1;
    ArrayList<GroceryItem> groceryList;
    GroceryListAdapter adapter;
    GroceryListener callback;

    public GroceryFragment() {
        // Required empty public constructor
    }

    public static GroceryFragment newInstance(String param1, String param2) {
        GroceryFragment fragment = new GroceryFragment();
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
        View view = inflater.inflate(R.layout.fragment_grocery, container, false);

        groceryList = new ArrayList<>();
        groceryList.add(new GroceryItem("Apple", 5));
        RecyclerView groceryItemsList = (RecyclerView) view.findViewById(R.id.groceryList);
        adapter = new GroceryListAdapter(groceryList);
        groceryItemsList.setAdapter(adapter);
        groceryItemsList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GroceryItemAddActivity.class);
                startActivityForResult(intent, ADD_GROCERY_ITEM);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_GROCERY_ITEM) {
            if (resultCode == RESULT_OK) {
                String itemName = data.getStringExtra("name");
                int itemCount = data.getIntExtra("count", 0);

                int index = indexGroceryList(itemName);

                RecyclerView groceryListView = (RecyclerView) getView().findViewById(R.id.groceryList);

                if (index != -1) {
                    adapter.notifyItemChanged(index);
                } else {
                    groceryList.add(0, new GroceryItem(itemName, itemCount));
                    adapter.notifyItemInserted(0);
                    groceryListView.scrollToPosition(0);
                }
            }
        }
    }

    private int indexGroceryList(String name) {
        for (int i = 0; i < groceryList.size(); i++) {
            if (groceryList.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public interface GroceryListener {
        public void onButtonClick();
    }

    public void setGroceryListener(GroceryListener callback) {
        this.callback = callback;
    }

    public void updateCount(int count) {
        View view = getView();
    }
}