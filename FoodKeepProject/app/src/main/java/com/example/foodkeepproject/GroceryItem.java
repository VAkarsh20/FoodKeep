package com.example.foodkeepproject;

import java.io.Serializable;
import java.util.Date;

public class GroceryItem implements Serializable, Comparable {
    final private String name;
    private boolean removeVisible;
    final private boolean recurring;

    public GroceryItem(String name, boolean recurring) {
        this.name = name;
        this.removeVisible = false;
        this.recurring = recurring;
    }

    public String getName() { return name; }

    public boolean getRecurring() { return recurring; }

    public boolean getRemoveVisibility() { return removeVisible; }

    public void flipRemoveVisibility() {
        removeVisible = !removeVisible;
    }

    @Override
    public int compareTo(Object other) {
        String otherName = ((GroceryItem) other).getName();
        return name.compareTo(otherName);
    }
}
