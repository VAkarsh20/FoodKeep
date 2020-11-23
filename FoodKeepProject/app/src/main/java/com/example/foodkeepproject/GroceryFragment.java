package com.example.foodkeepproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroceryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroceryFragment extends Fragment {

    final int ADD_GROCERY_ITEM = 1;
    final int REMOVE_GROCERY_ITEM = 2;
    final int SHOPPING_MODE = 3;
    private boolean directAddPantry = true;
    ArrayList<GroceryItem> groceryList;
    GroceryListAdapter adapter;
    GroceryListener callback;
    FloatingActionButton fab;
    FloatingActionButton fabShopping;

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
        groceryList.add(new GroceryItem("Apple"));
        groceryList.add(new GroceryItem("Banana"));
        GroceryItemClickListener listener = this::removeItem; // TODO

        RecyclerView groceryItemsList = (RecyclerView) view.findViewById(R.id.groceryList);
        adapter = new GroceryListAdapter(groceryList, listener);
        groceryItemsList.setAdapter(adapter);
        groceryItemsList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fab = (FloatingActionButton) view.findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GroceryItemAddActivity.class);
                startActivityForResult(intent, ADD_GROCERY_ITEM);
            }
        });

        fabShopping = (FloatingActionButton) view.findViewById(R.id.shopButton);
        fabShopping.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
        fabShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShoppingModeActivity.class);
                intent.putExtra("ItemList", groceryList);
                intent.putExtra("DirectAddPantry", directAddPantry);
                startActivityForResult(intent, SHOPPING_MODE);
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
                int index = indexGroceryList(itemName);

                RecyclerView groceryListView = (RecyclerView) getView().findViewById(R.id.groceryList);

                if (index != -1) {
                    adapter.notifyItemChanged(index);
                } else {
                    groceryList.add(0, new GroceryItem(itemName));
                    adapter.notifyItemInserted(0);
                    groceryListView.scrollToPosition(0);
                }
            }
        } else if (requestCode == REMOVE_GROCERY_ITEM) {
            if (resultCode == RESULT_OK) {
                String itemName = data.getStringExtra("name");

                int index = indexGroceryList(itemName);
                //GroceryItem item = groceryList.get(index);

                groceryList.remove(index);
                adapter.notifyItemChanged(index);
                adapter.notifyItemRangeRemoved(index, 1);
            }
        } else if (requestCode == SHOPPING_MODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> names = (ArrayList<String>) data.getSerializableExtra("NameList");
                ArrayList<Integer> counts = (ArrayList<Integer>) data.getSerializableExtra("CountList");
                ArrayList<Long> expiryDates = (ArrayList<Long>) data.getSerializableExtra("ExpiryDateList");

                for (String name : names) {
                    int index = indexGroceryList(name);
                    groceryList.remove(index);
                    adapter.notifyItemChanged(index);
                    adapter.notifyItemRangeRemoved(index, 1);
                }

                if (directAddPantry) {
                    callback.onPantryAdd(names, counts, expiryDates);
                }
            }
        }
    }

    public void setShoppingPantryAddSetting(boolean setting) {
        directAddPantry = setting;
    }

    private int indexGroceryList(String name) {
        for (int i = 0; i < groceryList.size(); i++) {
            if (groceryList.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

//    private void deleteItemGroceryList(String name) {
//        int index = indexGroceryList(name);
//        groceryList.remove(index);
//        adapter.notifyItemChanged(index);
//        adapter.notifyItemRangeRemoved(index, 1);
//    }

    private void removeItem(String name) {

        int index = indexGroceryList(name);
        groceryList.remove(index);
        adapter.notifyItemChanged(index);
        adapter.notifyItemRangeRemoved(index, 1);
    }

    public interface GroceryListener {
        public void onPantryAdd(ArrayList<String> names, ArrayList<Integer> counts, ArrayList<Long> expiryDates);
    }

    public void setGroceryListener(GroceryListener callback) {
        this.callback = callback;
    }

    public void enterRemoveMode() {
        fab.setVisibility(View.GONE);
        fabShopping.setVisibility(View.GONE);
        for (GroceryItem item : groceryList) {
            item.flipRemoveVisibility();
        }
        adapter.notifyDataSetChanged();
    }

    public void exitRemoveMode() {
        fab.setVisibility(View.VISIBLE);
        fabShopping.setVisibility(View.VISIBLE);
        for (GroceryItem item : groceryList) {
            item.flipRemoveVisibility();
        }
        adapter.notifyDataSetChanged();
    }

    public void updateCount(int count) {
        View view = getView();
    }
}