package com.example.foodkeepproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {

    private List<GroceryItem> groceryItems;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemCount;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.groceryItemName);
            itemCount = (TextView) itemView.findViewById(R.id.groceryItemCount);
        }
    }

    public GroceryListAdapter(List<GroceryItem> groceryList) {
        groceryItems = groceryList;
    }

    @Override
    public GroceryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View groceryItemView = inflater.inflate(R.layout.grocery_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(groceryItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryListAdapter.ViewHolder holder, int position) {
        GroceryItem item = groceryItems.get(position);

        TextView textView = holder.itemName;
        textView.setText(item.getName());
        textView = holder.itemCount;
        textView.setText(item.getCount() + " in Grocery");
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }
}
