package com.mdmobile.pocketconsole.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.mdmobile.pocketconsole.provider.McContract;

import java.util.HashMap;


public class DevicesStatsCalculator extends AsyncTask<Cursor, Void, Bundle> {

    private Listener mCallback;

    @Override
    protected Bundle doInBackground(Cursor... cursors) {
        return fetchDeviceData(cursors[0]);
    }

    @Override
    protected void onPostExecute(Bundle arrayList) {
        if (mCallback != null) {
            mCallback.OnFinished(arrayList);
        }
    }

    public void registerListener(Listener listener) {
        this.mCallback = listener;
    }

    private Bundle fetchDeviceData(Cursor mCursor) {
        int onlineDeviceCounter = 0, androidCounter = 0, iosCounter = 0, windowsMobileCounter = 0,
                windowsDesktopCounter = 0, windowsModernCounter = 0, printersCounter = 0;
        HashMap<String, Integer> batteryCounter = new HashMap<>();
        batteryCounter.put("Unknown", 0);
        batteryCounter.put("10+", 0);
        batteryCounter.put("20+", 0);
        batteryCounter.put("40+", 0);
        batteryCounter.put("60+", 0);
        batteryCounter.put("80+", 0);
        String deviceFamily;
        Bundle stats = new Bundle();
        String extraInfo;
        String batteryStatus;
        int batteryValue;

        if (!mCursor.moveToFirst()) {
            return null;
        }

        do {
            extraInfo = mCursor.getString(mCursor.getColumnIndex(McContract.Device.COLUMN_EXTRA_INFO));
            batteryStatus = DbData.getDeviceExtraInfo(extraInfo).getString("tBatteryStatus");
            if (batteryStatus == null) {
                batteryCounter.put("Unknown", batteryCounter.get("Unknown") + 1);
            } else {
                batteryValue = Integer.valueOf(batteryStatus);
                if (batteryValue >= 0 && batteryValue <= 10) {
                    batteryCounter.put("0+", batteryCounter.get("Unknown") + 1);
                } else if (batteryValue >= 10 && batteryValue <= 20) {
                    batteryCounter.put("10+", batteryCounter.get("Unknown") + 1);
                } else if (batteryValue >= 20 && batteryValue <= 40) {
                    batteryCounter.put("20+", batteryCounter.get("Unknown") + 1);
                } else if (batteryValue >= 40 && batteryValue <= 60) {
                    batteryCounter.put("40+", batteryCounter.get("Unknown") + 1);
                } else if (batteryValue >= 60 && batteryValue <= 80) {
                    batteryCounter.put("60+", batteryCounter.get("Unknown") + 1);
                } else if (batteryValue >= 80) {
                    batteryCounter.put("Unknown", batteryCounter.get("Unknown") + 1);
                }
            }

            getOnlineStatus(mCursor);

            deviceFamily = mCursor.getString(mCursor.getColumnIndex(McContract.Device.COLUMN_FAMILY));
            if (deviceFamily.startsWith("Android")) {
                androidCounter++;
            } else if (deviceFamily.equals("Apple")) {
                iosCounter++;
            } else if (deviceFamily.equals("WindowsCE")) {
                windowsMobileCounter++;
            } else if (deviceFamily.equals("WindowsDesktop")) {
                windowsDesktopCounter++;
            } else if (deviceFamily.equals("WindowsPhone") || deviceFamily.equals("WindowsRuntime")) {
                windowsModernCounter++;
            } else if (deviceFamily.equals("Printer")) {
                printersCounter++;
            }
        } while (mCursor.moveToNext());

        stats.putInt("OnlineDevs", onlineDeviceCounter);
        stats.putInt("OfflineDevs", mCursor.getCount() - onlineDeviceCounter);


        if (androidCounter > 0) {
            stats.putInt("Android", androidCounter);
        }
        if (iosCounter > 0) {
            stats.putInt("Apple", iosCounter);
        }
        if (windowsMobileCounter > 0) {
            stats.putInt("WindowsCE", windowsMobileCounter);
        }
        if (windowsDesktopCounter > 0) {
            stats.putInt("WindowsDesktop", windowsDesktopCounter);
        }
        if (windowsModernCounter > 0) {
            stats.putInt("WindowsModern", windowsModernCounter);
        }
        if (printersCounter > 0) {
            stats.putInt("Printer", printersCounter);
        }
        return stats;
    }

    private int getOnlineStatus(Cursor mCursor) {
        return mCursor.getInt(mCursor.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE));

    }

    public interface Listener {
        void OnFinished(Bundle result);
    }
}
