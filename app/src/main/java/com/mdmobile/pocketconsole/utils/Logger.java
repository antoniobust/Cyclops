package com.mdmobile.pocketconsole.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.BuildConfig;

/**
 * Helper class to organise logs
 */

public class Logger {

    public static void log(@NonNull String logTag, @NonNull String msg,
                    @IntRange(from = Log.VERBOSE, to = Log.ERROR) int priority) {

        switch (priority) {

            case Log.VERBOSE:
                if (BuildConfig.DEBUG) {
                    Log.v(logTag, msg);
                }
                return;

            case Log.DEBUG:
                if (BuildConfig.DEBUG) {
                    Log.d(logTag, msg);
                }
                return;

            case Log.ERROR:
                Log.e(logTag, msg);
                return;

            case Log.INFO:
                Log.i(logTag, msg);
                return;

            case Log.WARN:
                Log.w(logTag, msg);
        }


    }


}
