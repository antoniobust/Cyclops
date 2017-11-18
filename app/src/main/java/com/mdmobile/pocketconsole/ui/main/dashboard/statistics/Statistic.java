package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import android.content.Context;
import android.database.Cursor;

import com.mdmobile.pocketconsole.dataModels.StatValue;

import java.util.ArrayList;

/**
 * Class responsible for creating a new statistic and return data from DB
 */

public abstract class Statistic implements IStatisticFactory {
    public final static int DEVICE_STAT = 1;

    String element;
    protected StatValue[] entries;

    // - Constructor
    public Statistic(String element) {
        this.element = element;
    }

    // - Stat factory interface method
    @Override
    public Statistic createStatistic(int statisticType, String element) {
        if (statisticType == DEVICE_STAT) {
            return new DeviceStat(element);
        }
        return null;
    }


    public abstract void initPoll(Context context);

    public StatValue[] getData() {
        return entries;
    }


    public int getPopulationSize() {
        if (entries.length > 0) {
            int counter = 0;
            for (StatValue entry : entries) {
                counter += entry.getCounter();
            }
            return counter;
        }
        return entries.length;
    }

    public String[] getGroupsLabels() {
        if (entries.length > 0) {
            ArrayList<String> label = new ArrayList<>(entries.length);

            for (StatValue entry : entries) {
                label.add(entry.getGroupLabel());
            }
            return label.toArray(new String[label.size()]);
        }
        return null;
    }

    StatValue[] statValuesFromCursor(Cursor c) {
        if (!c.moveToFirst()) {
            return null;
        }
        ArrayList<StatValue> statValues = new ArrayList<>(c.getCount());

        while (!c.isLast()) {
            statValues.add(new StatValue(c.getString(1), c.getInt(0)));
            c.moveToNext();
        }
        return statValues.toArray(new StatValue[statValues.size()]);
    }
}
