package com.example.foodkeepproject;

public class PantryItem {
    private String name;
    private int count;
    private boolean visible;

    public PantryItem(String name, int count) {
        this.name = name;
        this.count = count;
        this.visible = visible = false;
    }

    public void addCount(int add) {
        count += add;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean getVisibility() { return visible; }

    public void flipVisibility() {
        visible = !visible;
    }
}
