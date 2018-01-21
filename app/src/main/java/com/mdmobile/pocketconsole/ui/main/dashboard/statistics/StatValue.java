package com.mdmobile.pocketconsole.ui.main.dashboard.statistics;

import com.mdmobile.pocketconsole.R;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

/**
 * Java class that represent a single data for a statistic
 */

public class StatValue {

    private int mValue;
    private String label;

    public StatValue(String label, int counter) {
        this.mValue = counter;
        this.label = label;
    }

    public int getValue() {
        return mValue;
    }

    public String getLabel() {
        return label == null ? applicationContext.getString(R.string.unknown_label) : label;
    }
}
