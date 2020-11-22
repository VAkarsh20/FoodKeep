package com.example.foodkeepproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {

    private List<GroceryItem> groceryItems;
    private GroceryItemClickListener itemListener;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public Button removeButton;

        private GroceryItemClickListener itemListener;

        public ViewHolder(View itemView, GroceryItemClickListener itemListener) {
            super(itemView);

            this.itemListener = itemListener;

            itemName = (TextView) itemView.findViewById(R.id.groceryItemName);
            removeButton = (Button) itemView.findViewById(R.id.itemRemove);
            removeButton.setOnClickListener(removeButtonListener);
        }

        private View.OnClickListener removeButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onClickRemove(itemName.getText().toString());
            }
        };
    }

    public GroceryListAdapter(List<GroceryItem> groceryList, GroceryItemClickListener itemListener) {
        groceryItems = groceryList;
        this.itemListener = itemListener;
    }

    @Override
    public GroceryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View groceryItemView = inflater.inflate(R.layout.grocery_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(groceryItemView, itemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryListAdapter.ViewHolder holder, int position) {
        GroceryItem item = groceryItems.get(position);

        TextView textView = holder.itemName;
        textView.setText(item.getName());

        Button button = holder.removeButton;
        if (item.getRemoveVisibility()) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }
}
