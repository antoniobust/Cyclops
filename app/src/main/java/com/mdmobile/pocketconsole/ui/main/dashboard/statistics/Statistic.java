package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for creating a new statistic and return data from DB
 */

public abstract class Statistic extends AsyncQueryHandler {
    public final static int COUNTER_STAT = 1;
    public final static int COUNTER_RANGE = 2;
    String mProperty;
    private ArrayList<StatValue> entries;
    private IStatisticReady listener;

    // - Constructor
    Statistic(ContentResolver cr, String property) {
        super(cr);
        mProperty = property;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }
        entries = statValuesFromCursor(cursor);
        cursor.close();
        Bundle data = new Bundle();
        data.putParcelableArrayList(mProperty, entries);
        listener.getData(data);
    }

    public abstract void initPoll();

    public void registerListener(IStatisticReady listener) {
        this.listener = listener;
    }

    public void unRegisterListener() {
        this.listener = null;
    }

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

    private ArrayList<StatValue> statValuesFromCursor(Cursor c) {
        ArrayList<StatValue> statValues = new ArrayList<>(c.getCount());
        c.moveToFirst();
        do{
            statValues.add(new StatValue(c.getString(1), c.getInt(0)));
        }while (c.moveToNext());
        return statValues;
    }

    public interface IStatisticReady {
        void getData(Bundle values);
    }
}
