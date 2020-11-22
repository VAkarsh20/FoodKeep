package com.example.foodkeepproject;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<GroceryItem> groceryItems;
    private ShoppingItemClickListener itemListener;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public ImageButton checkButton;

        private ShoppingItemClickListener itemListener;

        public ViewHolder(View itemView, ShoppingItemClickListener itemListener) {
            super(itemView);

            this.itemListener = itemListener;

            itemName = (TextView) itemView.findViewById(R.id.groceryItemName);
            checkButton = (ImageButton) itemView.findViewById(R.id.checkButton);
            checkButton.setOnClickListener(checkButtonListener);
        }

        private View.OnClickListener checkButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onClickCheck(itemName.getText().toString(), checkButton.getBackgroundTintList().equals(ColorStateList.valueOf(Color.GREEN)));
            }
        };
    }

    public ShoppingListAdapter(List<GroceryItem> groceryList, ShoppingItemClickListener itemListener) {
        groceryItems = groceryList;
        this.itemListener = itemListener;
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View groceryItemView = inflater.inflate(R.layout.shopping_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(groceryItemView, itemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ViewHolder holder, int position) {
        GroceryItem item = groceryItems.get(position);

        TextView textView = holder.itemName;
        textView.setText(item.getName());

        ImageButton button = holder.checkButton;

        if (item.getRemoveVisibility()) {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }

    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }
}
