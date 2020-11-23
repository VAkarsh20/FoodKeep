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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantryFragment extends Fragment {

    final int ADD_PANTRY_ITEM = 1;
    final int CONSUME_PANTRY_ITEM = 2;
    ArrayList<PantryItem> pantryList;
    PantryListAdapter adapter;
    PantryListener callback;
    FloatingActionButton fab;

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
        pantryList.add(new PantryItem("Apple", 5, new GregorianCalendar(2020, Calendar.NOVEMBER, 25).getTime()));
        pantryList.add(new PantryItem("Banana", 4, new GregorianCalendar(2020, Calendar.NOVEMBER, 10).getTime()));

        Collections.sort(pantryList);

        PantryItemClickListener listener = this::consumeItem;

        RecyclerView pantryItemsList = (RecyclerView) view.findViewById(R.id.pantryList);
        adapter = new PantryListAdapter(pantryList, listener);
        pantryItemsList.setAdapter(adapter);
        pantryItemsList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fab = (FloatingActionButton) view.findViewById(R.id.addButton);
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PANTRY_ITEM) {
            if (resultCode == RESULT_OK) {
                /*
                String itemName = data.getStringExtra("name");
                int itemCount = data.getIntExtra("count", 0);
                Date expiryDate = new Date(data.getLongExtra("expiryDate", 0));
                */
                addItem(data.getStringExtra("name"), data.getIntExtra("count", 0), data.getLongExtra("expiryDate", 0));
            }
        } else if (requestCode == CONSUME_PANTRY_ITEM) {
            if (resultCode == RESULT_OK) {
                String itemName = data.getStringExtra("name");
                int itemCount = data.getIntExtra("count", 0);

                int index = indexPantryList(itemName);
                PantryItem item = pantryList.get(index);
                if (item.consume(itemCount)) {
                    pantryList.remove(index);
                    adapter.notifyItemChanged(index);
                    adapter.notifyItemRangeRemoved(index, 1);
                } else {
                    adapter.notifyItemChanged(index);
                }
            }
        }
    }

    private void addItem(String itemName, int itemCount, long expiryDateLong) {
        Date expiryDate = new Date(expiryDateLong);

        int index = indexPantryList(itemName);

        RecyclerView pantryListView = (RecyclerView) getView().findViewById(R.id.pantryList);

        if (index != -1) {
            pantryList.get(index).addCount(itemCount, expiryDate);
            adapter.notifyItemChanged(index);
        } else {
            pantryList.add(0, new PantryItem(itemName, itemCount, expiryDate));
            adapter.notifyItemInserted(0);
        }

        Collections.sort(pantryList);
        adapter.notifyItemRangeChanged(0, pantryList.size());
        pantryListView.scrollToPosition(0);
    }

    private int indexPantryList(String name) {
        for (int i = 0; i < pantryList.size(); i++) {
            if (pantryList.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    private void deleteItemPantryList(String name) {
        int index = indexPantryList(name);
        pantryList.remove(index);
        adapter.notifyItemChanged(index);
        adapter.notifyItemRangeRemoved(index, 1);
    }

    public void onGroceryPantryAdd(ArrayList<String> names, ArrayList<Integer> counts, ArrayList<Long> expiryDates) {
        for (int i = 0; i < names.size(); i++) {
            addItem(names.get(i), counts.get(i), expiryDates.get(i));
        }
    }

    private void consumeItem(String name) {
        int index = indexPantryList(name);
        PantryItem item = pantryList.get(index);
        Intent intent = new Intent(getActivity(), ConsumeItemActivity.class);
        intent.putExtra("ITEM_NAME", item.getName());
        intent.putExtra("ITEM_COUNT", item.getCount());
        startActivityForResult(intent, CONSUME_PANTRY_ITEM);
    }

    public interface PantryListener {
        public void onButtonClick();
    }

    public void setPantryListener(PantryListener callback) {
        this.callback = callback;
    }

    public void enterConsumeMode() {
        fab.setVisibility(View.GONE);
        for (PantryItem item : pantryList) {
            item.flipConsumeVisibility();
        }
        adapter.notifyDataSetChanged();
    }

    public void exitConsumeMode() {
        fab.setVisibility(View.VISIBLE);
        for (PantryItem item : pantryList) {
            item.flipConsumeVisibility();
        }
        adapter.notifyDataSetChanged();
    }

    public void updateCount(int count) {
        View view = getView();
        //((TextView) view.findViewById(R.id.pantry_count)).setText(Integer.toString(count));
    }
}