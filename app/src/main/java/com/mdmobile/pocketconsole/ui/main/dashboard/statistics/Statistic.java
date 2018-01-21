package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for creating a new statistic and return data from DB
 */

public abstract class Statistic {
    public final static int COUNTER_STAT = 1;
    public final static int COUNTER_RANGE = 2;
    protected List<StatValue> entries;
    String mProperty;

    // - Constructor
    public Statistic(String property) {
        mProperty = property;
    }

    public abstract void initPoll(Context context);

    public List<StatValue> getData() {
        return entries;
    }

    public int getPopulationSize() {
        if (entries.isEmpty()) {
            return entries.size();
        }
        int counter = 0;
        for (StatValue entry : entries) {
            counter += entry.getValue();
        }
        return counter;
    }

    public String[] getGroupsLabels() {
        if (entries.isEmpty()) {
            return null;
        }
        ArrayList<String> label = new ArrayList<>(entries.size());
        for (StatValue entry : entries) {
            label.add(entry.getLabel());
        }
        return label.toArray(new String[label.size()]);
    }

    public int getGroupsCount() {
        return entries.size();
    }

    List<StatValue> statValuesFromCursor(Cursor c) {
        if (!c.moveToFirst()) {
            return null;
        }

        ArrayList<StatValue> statValues = new ArrayList<>(c.getCount());

        while (!c.isLast()) {
            statValues.add(new StatValue(c.getString(1), c.getInt(0)));
            c.moveToNext();
        }
        return statValues;
    }
}
