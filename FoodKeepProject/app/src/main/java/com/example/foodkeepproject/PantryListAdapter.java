package com.example.foodkeepproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryListAdapter extends RecyclerView.Adapter<PantryListAdapter.ViewHolder> {

    private List<PantryItem> pantryItems;
    private PantryItemClickListener itemListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemCount;
        public Button removeButton;

        private PantryItemClickListener itemListener;

        public ViewHolder(View itemView, PantryItemClickListener itemListener) {
            super(itemView);

            this.itemListener = itemListener;
            itemName = (TextView) itemView.findViewById(R.id.pantryItemName);
            itemCount = (TextView) itemView.findViewById(R.id.pantryItemCount);
            removeButton = (Button) itemView.findViewById(R.id.itemRemove);
            removeButton.setOnClickListener(removeButtonListener);
        }

        private View.OnClickListener removeButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onClickDelete(itemName.getText().toString());
            }
        };
    }

    public PantryListAdapter(List<PantryItem> pantryList, PantryItemClickListener itemListener) {
        pantryItems = pantryList;
        this.itemListener = itemListener;
    }

    @Override
    public PantryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View pantryItemView = inflater.inflate(R.layout.pantry_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(pantryItemView, itemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PantryListAdapter.ViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);

        TextView textView = holder.itemName;
        textView.setText(item.getName());
        textView = holder.itemCount;
        textView.setText(item.getCount() + " in Pantry");

        Button button = holder.removeButton;
        if (item.getVisibility()) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }
}
