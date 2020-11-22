package com.example.foodkeepproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingModeActivity extends AppCompatActivity {

    final int ADD_PANTRY = 1;
    private boolean directAddPantry;
    ArrayList<GroceryItem> shoppingList = new ArrayList<GroceryItem>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();
    ArrayList<Long> expiryDates = new ArrayList<>();
    ShoppingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_mode);
        shoppingList = (ArrayList<GroceryItem>) getIntent().getSerializableExtra("ItemList");
        directAddPantry = getIntent().getBooleanExtra("DirectAddPantry", true);

        for (GroceryItem item : shoppingList) {
            if (item.getRemoveVisibility()) {
                item.flipRemoveVisibility();
            }
        }

        ShoppingItemClickListener listener = this::itemChecked;

        RecyclerView groceryItemsList = (RecyclerView) findViewById(R.id.shoppingList);
        adapter = new ShoppingListAdapter(shoppingList, listener);
        groceryItemsList.setAdapter(adapter);
        groceryItemsList.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PANTRY) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                int count = data.getIntExtra("count", 0);
                long expiryDate = data.getLongExtra("expiryDate", 0);

                names.add(name);
                counts.add(count);
                expiryDates.add(expiryDate);



                int index = indexShoppingList(name);
                shoppingList.get(index).flipRemoveVisibility();
                adapter.notifyItemChanged(index);
            }
        }
    }

    private int indexShoppingList(String name) {
        for (int i = 0; i < shoppingList.size(); i++) {
            if (shoppingList.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    private void itemChecked(String name, boolean checked) {
        if (directAddPantry) {
            if (!checked) {
                Intent intent = new Intent(this, PantryItemShoppingAddActivity.class);
                intent.putExtra("name", name);
                startActivityForResult(intent, ADD_PANTRY);
            } else {
                int indexStored = names.indexOf(name);
                names.remove(indexStored);
                counts.remove(indexStored);
                expiryDates.remove(indexStored);

                int index = indexShoppingList(name);
                shoppingList.get(index).flipRemoveVisibility();
                adapter.notifyItemChanged(index);
            }
        } else {
            int index = indexShoppingList(name);
            shoppingList.get(index).flipRemoveVisibility();
            adapter.notifyItemChanged(index);
            if (!checked) {
                names.add(name);
            } else {
                int indexStored = names.indexOf(name);
                names.remove(indexStored);
            }

        }
    }

    public void finishShopping(View view) {
        Intent intent = new Intent();

        if (names.size() == 0) {
            setResult(RESULT_CANCELED);
        } else {
            intent.putExtra("NameList", names);
            intent.putExtra("CountList", counts);
            intent.putExtra("ExpiryDateList", expiryDates);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}