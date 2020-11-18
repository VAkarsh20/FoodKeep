package com.example.foodkeepproject;

public class PantryItem {
    private String name;
    private int count;

    public PantryItem(String name, int count) {
        this.name = name;
        this.count = count;
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
}
