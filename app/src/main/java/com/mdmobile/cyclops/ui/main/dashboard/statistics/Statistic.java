package com.mdmobile.cyclops.ui.main.dashboard.statistics;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.mdmobile.cyclops.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for creating a new statistic and return statsData from DB
 */

public abstract class Statistic extends AsyncQueryHandler {
    public final static int COUNTER_STAT = 1;
    public final static int COUNTER_RANGE = 2;
    private final int MAX_POPULATION = 7;
    private final String LOG_TAG = Statistic.class.getSimpleName();
    List<String> mProperties;
    private Bundle statsData = new Bundle();
    private IStatisticReady listener;

    // - Constructor
    Statistic(ContentResolver cr, List<String> properties) {
        super(cr);
        mProperties = properties;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }
        ArrayList<StatDataEntry> entries = statValuesFromCursor(cursor);
        cursor.close();

        //If collection is bigger than 6 different values we will just show "others" with the sum of other entries
        if (entries != null && entries.size() > MAX_POPULATION) {
            StatDataEntry other = new StatDataEntry("Other", 0);
            for (int i = entries.size(); i > 5; i--) {
                other.value = (other.value + entries.get(i - 1).value);
                entries.remove(i - 1);
            }
            entries.add(other);
        }
        statsData.putParcelableArrayList(cookie.toString(), entries);
        Logger.log(LOG_TAG, ++token + "/" + mProperties.size() + " poll (" + cookie.toString() + ") complete", Log.VERBOSE);
        if (token == mProperties.size()) {
            listener.getData(token, statsData);
        }
    }

    public abstract void initPoll();

    public void registerListener(IStatisticReady listener) {
        this.listener = listener;
    }

    public void unRegisterListener() {
        this.listener = null;
    }

    private ArrayList<StatDataEntry> statValuesFromCursor(Cursor c) {
        ArrayList<StatDataEntry> statDataEntries = new ArrayList<>(c.getCount());
        if (!c.moveToFirst()) {
            return null;
        }
        do {
            statDataEntries.add(new StatDataEntry(c.getString(1), c.getInt(0)));
        } while (c.moveToNext());
        return statDataEntries;
    }

    public interface IStatisticReady {
        void getData(int statId, Bundle values);
    }
}
