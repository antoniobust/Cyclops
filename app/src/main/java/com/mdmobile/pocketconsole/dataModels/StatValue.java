package com.mdmobile.pocketconsole.dataModels;

import com.mdmobile.pocketconsole.R;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

/**
 * Java class that represent a single entry for a statistic
 */

public class StatValue {

    private int mCounter;
    private String groupLabel;

    public StatValue(String groupLabel, int counter) {
        this.mCounter = counter;
        this.groupLabel = groupLabel;
    }

    public int getCounter() {
        return mCounter;
    }

    public String getGroupLabel() {
        return groupLabel == null ? applicationContext.getString(R.string.unknown_label) : groupLabel;
    }
}
