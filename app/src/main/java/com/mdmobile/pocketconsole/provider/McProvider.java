package com.mdmobile.pocketconsole.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdmobile.pocketconsole.utils.Logger;

import static android.R.attr.data;

public class McProvider extends ContentProvider {

    private final static String LOG_TAG = McProvider.class.getSimpleName();

    //Matcher
    private static McUriMatcher matcher;
    SQLiteDatabase database;
    //DB helper
    private McHelper mcHelper;

    @Override
    public boolean onCreate() {
        mcHelper = new McHelper(getContext());
        matcher = new McUriMatcher();
        return true;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return matcher.matchUri(uri).contentType;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        Logger.log(LOG_TAG, "insert( uri:" + uri.toString() + " , objects: " + values.length+")", Log.VERBOSE);

        //Get DB is an expensive operation check if we already have opened it
        if (database == null) {
            database = mcHelper.getWritableDatabase();
        }
        McEnumUri mcEnumUri = matcher.matchUri(uri);
        int dataInserted = 0;

        if (mcEnumUri.tableName != null) {
            switch (mcEnumUri) {
                case DEVICES:
                    for (ContentValues contentValues : values) {
                        if (database.insertWithOnConflict(McContract.DEVICE_TABLE_NAME,
                                null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0) {
                            //if data was inserted correctly increment data inserted value
                            dataInserted++;
                        }
                    }
                    if (dataInserted == values.length) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " devices in DB", Log.VERBOSE);

                        return 1;
                    } else {
                        Logger.log(LOG_TAG, "Device Bulk insert didn't insert devices correctly", Log.ERROR);
                    }
            }
        }
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        if (contentValues != null) {
            Logger.log(LOG_TAG, "insert( uri: " + uri.toString() + " , values: " + contentValues.size() + ")", Log.VERBOSE);
        } else {
            Logger.log(LOG_TAG, "insert( uri: " + uri.toString() + " , values: null )", Log.VERBOSE);
        }
        //Get DB is an expensive operation check if we already have opened it
        if (database == null) {
            database = mcHelper.getWritableDatabase();
        }

        McEnumUri mcEnumUri = matcher.matchUri(uri);
        long newRowID;

        if (mcEnumUri.tableName != null) {
            switch (mcEnumUri) {
                case DEVICES:
                    newRowID = database.insertWithOnConflict(McContract.DEVICE_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    if (newRowID < 1) {
                        Logger.log(LOG_TAG, "Impossible to insert device in DB", Log.ERROR);
                        return null;
                    }
                    getContext().getContentResolver().notifyChange(uri, null);

                    return McContract.Device.buildUriWithID(newRowID);
                case CUSTOM_ATTRIBUTE:
                case CUSTOM_DATA:
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
