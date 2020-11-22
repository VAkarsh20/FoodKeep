package com.example.foodkeepproject;

import java.io.Serializable;

public class GroceryItem implements Serializable {
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
