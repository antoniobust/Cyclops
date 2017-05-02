package com.mdmobile.pocketconsole.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdmobile.pocketconsole.utils.Logging;

public class McProvider extends ContentProvider {

    private final static String LOG_TAG = McProvider.class.getSimpleName();

    //Matcher
    private static McUriMatcher matcher;

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
        return super.bulkInsert(uri, values);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (contentValues != null) {
            Logging.log(LOG_TAG, "insert( uri: " + uri.toString() + " , values: " + contentValues.toString() + ")", Log.VERBOSE);
        } else {
            Logging.log(LOG_TAG, "insert( uri: " + uri.toString() + " , values: null )", Log.VERBOSE);
        }

        final SQLiteDatabase db = mcHelper.getWritableDatabase();

        McEnumUri mcEnumUri = matcher.matchUri(uri);
        long newRowID = -1;

        if (mcEnumUri.tableName != null) {
            newRowID = db.insertWithOnConflict(McContract.DEVICE_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            getContext().getContentResolver().notifyChange(uri, null);
        }

        switch(mcEnumUri){
            case DEVICES:
                return McContract.Device.buildUriWithID(newRowID);
            case CUSTOM_ATTRIBUTE:
            case CUSTOM_ATTRIBUTE_DEVICE:
            case CUSTOM_DATA:
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
