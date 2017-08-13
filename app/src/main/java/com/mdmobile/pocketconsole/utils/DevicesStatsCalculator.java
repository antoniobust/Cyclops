package com.mdmobile.pocketconsole.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.ArrayMap;

import com.mdmobile.pocketconsole.provider.McContract;

import java.util.ArrayList;


public class DevicesStatsCalculator extends AsyncTask<Cursor, Void, ArrayList<Object>> {

    private Listener mCallback;

    @Override
    protected ArrayList<Object> doInBackground(Cursor... cursors) {
        return fetchDeviceData(cursors[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Object> arrayList) {
        if (mCallback != null) {
            mCallback.OnFinished(arrayList);
        }
    }

    public void registerListener(Listener listener) {
        this.mCallback = listener;
    }

    private ArrayList<Object> fetchDeviceData(Cursor mCursor) {
        int onlineDeviceCounter = 0, totMemoryCounter = 0, androidCounter = 0, iosCounter = 0, windowsMobileCounter = 0,
                windowsDesktopCounter = 0, windowsModernCounter = 0, printersCounter = 0;
        String deviceFamily;
        ArrayMap<String, Integer> platformCounter = new ArrayMap<>(6);
        ArrayList<Object> result = new ArrayList<>();

        if (!mCursor.moveToFirst()) {
            return null;
        }

        do {
            if (mCursor.getInt(mCursor.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1) {
                onlineDeviceCounter++;
            }

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

        result.add(onlineDeviceCounter);
        result.add(mCursor.getCount() - onlineDeviceCounter);

        if (androidCounter > 0) {
            platformCounter.put("Android", androidCounter);
        }
        if (iosCounter > 0) {
            platformCounter.put("Apple", iosCounter);
        }
        if (windowsMobileCounter > 0) {
            platformCounter.put("WindowsCE", windowsMobileCounter);
        }
        if (windowsDesktopCounter > 0) {
            platformCounter.put("WindowsDesktop", windowsDesktopCounter);
        }
        if (windowsModernCounter > 0) {
            platformCounter.put("WindowsModern", windowsModernCounter);
        }
        if (printersCounter > 0) {
            platformCounter.put("Printer", printersCounter);
        }

        ArrayMap<String, Integer> platforms = new ArrayMap<>();
        platforms.put("Android", androidCounter);
        platforms.put("iOS", iosCounter);
        platforms.put("WindowsCE", windowsMobileCounter);
        platforms.put("Desktop", windowsDesktopCounter);
        platforms.put("Windows CE / Mobile", windowsMobileCounter);
        platforms.put("Zebra Printers", printersCounter);

        result.add(platforms);

        return result;
    }

    public interface Listener {
        void OnFinished(ArrayList<Object> result);
    }
}
