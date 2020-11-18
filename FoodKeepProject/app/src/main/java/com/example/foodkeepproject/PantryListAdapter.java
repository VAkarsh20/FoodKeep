package com.example.foodkeepproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryListAdapter extends RecyclerView.Adapter<PantryListAdapter.ViewHolder> {

    private List<PantryItem> pantryItems;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemCount;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.pantryItemName);
            itemCount = (TextView) itemView.findViewById(R.id.pantryItemCount);
        }
    }

    public PantryListAdapter(List<PantryItem> pantryList) {
        pantryItems = pantryList;
    }

    @Override
    public PantryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View pantryItemView = inflater.inflate(R.layout.pantry_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(pantryItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PantryListAdapter.ViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);

        TextView textView = holder.itemName;
        textView.setText(item.getName());
        textView = holder.itemCount;
        textView.setText(item.getCount() + " in Pantry");
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }
}
