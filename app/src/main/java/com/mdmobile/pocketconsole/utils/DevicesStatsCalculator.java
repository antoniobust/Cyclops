package com.mdmobile.pocketconsole.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.mdmobile.pocketconsole.provider.McContract;


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
        int onlineDeviceCounter = 0, totMemoryCounter = 0, androidCounter = 0, iosCounter = 0, windowsMobileCounter = 0,
                windowsDesktopCounter = 0, windowsModernCounter = 0, printersCounter = 0;
        String deviceFamily;
        Bundle stats = new Bundle();

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

    public interface Listener {
        void OnFinished(Bundle result);
    }
}
