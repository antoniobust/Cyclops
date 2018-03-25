package com.mdmobile.cyclopes.ui.main.dashboard.statistics;

import android.os.Parcel;
import android.os.Parcelable;

import com.mdmobile.cyclopes.R;

import static com.mdmobile.cyclopes.ApplicationLoader.applicationContext;

/**
 * Java class that represent a single statsData for a statistic
 */

public class StatValue implements Parcelable {

    private int mValue;
    private String label;

    public StatValue(String label, int counter) {
        this.mValue = counter;
        this.label = label;
    }

    protected StatValue(Parcel in) {
        mValue = in.readInt();
        label = in.readString();
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mValue);
        dest.writeString(label);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StatValue> CREATOR = new Creator<StatValue>() {
        @Override
        public StatValue createFromParcel(Parcel in) {
            return new StatValue(in);
        }

        @Override
        public StatValue[] newArray(int size) {
            return new StatValue[size];
        }
    };

    public int getValue() {
        return mValue;
    }

    public String getLabel() {
        return label == null ? applicationContext.getString(R.string.unknown_label) : label;
    }
}
