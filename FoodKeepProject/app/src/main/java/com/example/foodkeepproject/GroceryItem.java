package com.example.foodkeepproject;

public class GroceryItem {
    private String name;
    private boolean removeVisible;

    public GroceryItem(String name) {
        this.name = name;
        this.removeVisible = false;
    }

    public String getName() { return name; }

    public boolean getRemoveVisibility() { return removeVisible; }

    public void flipRemoveVisibility() {
        removeVisible = !removeVisible;
    }
}
