package com.example.foodkeepproject;

import androidx.recyclerview.widget.SortedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class PantryItem implements Comparable {
    private String name;
    private int count;
    private boolean consumeVisible;
    private Date earliestExpiry;
    private List<Date> expiryDates = new ArrayList<>();
    private HashMap<Date, Integer> countByDate = new HashMap<>();

    public PantryItem(String name, int count, Date earliestExpiry) {
        this.name = name;
        this.count = count;
        this.consumeVisible = false;
        this.earliestExpiry = earliestExpiry;
        expiryDates.add(earliestExpiry);
        countByDate.put(earliestExpiry, count);
    }

    public void addCount(int add, Date expiryDate) {
        count += add;
        if (countByDate.containsKey(expiryDate)) {
            countByDate.put(expiryDate, countByDate.get(expiryDate) + add);
        } else {
            countByDate.put(expiryDate, add);
            expiryDates.add(expiryDate);
            Collections.sort(expiryDates);
            earliestExpiry = expiryDates.get(0);
        }
    }

    @Override
    public int compareTo(Object other) {
        Date otherExpiry = ((PantryItem) other).getEarliestExpiry();
        if (earliestExpiry.before(otherExpiry)) {
            return -1;
        } else if (otherExpiry.before(earliestExpiry)) {
            return 1;
        } else {
            return 0;
        }
    }

    public Date getEarliestExpiry() { return earliestExpiry; }

    public int getEarliestExpiryAmount() { return countByDate.get(earliestExpiry); }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean getConsumeVisibility() { return consumeVisible; }

    public void flipConsumeVisibility() {
        consumeVisible = !consumeVisible;
    }

    public boolean consume(int consumeNum) {
        count -= consumeNum;
        while (consumeNum > 0) {
            int earliestExpiryAmount = countByDate.get(earliestExpiry);
            if (earliestExpiryAmount > consumeNum) {
                countByDate.put(earliestExpiry, earliestExpiryAmount - consumeNum);
                consumeNum = 0;
            } else {
                consumeNum -= earliestExpiryAmount;
                countByDate.remove(earliestExpiry);
                expiryDates.remove(0);

                if (expiryDates.size() > 0) {
                    earliestExpiry = expiryDates.get(0);
                }
            }
        }

        return count <= 0;
    }
}
