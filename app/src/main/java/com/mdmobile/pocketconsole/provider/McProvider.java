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

import java.util.Arrays;

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
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        Logger.log(LOG_TAG, "Query( uri:" + uri.toString() + ", data selected: " + Arrays.toString(projection)
                + " selection parameters: " + selection + " values:" + Arrays.toString(selectionArgs), Log.VERBOSE);
        //Get DB is an expensive operation check if we already have opened it
        if (database == null) {
            database = mcHelper.getWritableDatabase();
        }
        McEnumUri mcEnumUri = matcher.matchUri(uri);
        Cursor c;

        switch (mcEnumUri) {
            case DEVICES:
                c = database.query(McContract.DEVICE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case DEVICES_ID:
                String devID = McContract.Device.getDeviceIdFromUri(uri);
                c = database.query(McContract.DEVICE_TABLE_NAME,
                        projection,
                        McContract.Device.COLUMN_DEVICE_ID + "=?",
                        new String[]{devID}, null, null, sortOrder);
                break;

            case INSTALLED_APPLICATIONS_ON_DEVICE:
                String devId = McContract.InstalledApplications.getDeviceIdFromUri(uri);
                c = database.query(McContract.INSTALLED_APPLICATION_TABLE_NAME, projection,
                        McContract.InstalledApplications.DEVICE_ID + "=?", new String[]{devId}, null, null, sortOrder);
                break;

            case INSTALLED_APPLICATION_PKG_NAME:
                String packageName = McContract.InstalledApplications.getAppPackageNameFromUri(uri);
                c = database.query(McContract.INSTALLED_APPLICATION_TABLE_NAME, projection,
                        McContract.InstalledApplications.APPLICATION_ID + "=?", new String[]{packageName}, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported URI: " + uri.toString());
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        Logger.log(LOG_TAG, "insert( uri:" + uri.toString() + " , objects: " + values.length + ")", Log.VERBOSE);

        //Get DB is an expensive operation check if we already have opened it
        if (database == null) {
            database = mcHelper.getWritableDatabase();
        }
        McEnumUri mcEnumUri = matcher.matchUri(uri);
        int dataInserted = 0;

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

                    return dataInserted;
                } else {
                    Logger.log(LOG_TAG, "Device Bulk insert didn't insert devices correctly", Log.ERROR);
                    return dataInserted;
                }
            case INSTALLED_APPLICATION_PKG_NAME:
                for (ContentValues contentValues : values) {
                    if (database.insertWithOnConflict(McContract.INSTALLED_APPLICATION_TABLE_NAME,
                            null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0) {
                        //if data was inserted correctly increment data inserted value
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " devices in DB", Log.VERBOSE);

                    return dataInserted;
                } else {
                    Logger.log(LOG_TAG, "Device Bulk insert didn't insert devices correctly", Log.ERROR);
                    return dataInserted;
                }

            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri.toString());
        }
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
                return null;
            case CUSTOM_DATA:
                return null;

            case INSTALLED_APPLICATION_ID:
                newRowID = database.insertWithOnConflict(McContract.INSTALLED_APPLICATION_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert application in DB", Log.ERROR);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return McContract.InstalledApplications.buildUriWithId(newRowID);


            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri.toString());
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Logger.log(LOG_TAG, "Delete(uri: " + uri.toString() + " selection: " + selection + " values: " + Arrays.toString(selectionArgs), Log.VERBOSE);
        SQLiteDatabase database = mcHelper.getWritableDatabase();

        McEnumUri mcEnumUri = matcher.matchUri(uri);
        int deleted = 0;

        switch (mcEnumUri) {
            case DEVICES:
                deleted = database.delete(McContract.DEVICE_TABLE_NAME, null, null);
                if (deleted > 0) {
                    Logger.log(LOG_TAG, "Devices deleted:" + deleted, Log.VERBOSE);
//                    getContext().getContentResolver().notifyChange(uri, null);
                } else {
                    Logger.log(LOG_TAG, "No device deleted", Log.VERBOSE);
                }
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        Logger.log(LOG_TAG, "Update(uri: " + uri.toString() + " selection: " + selection + " values: " + Arrays.toString(selectionArgs), Log.VERBOSE);

        SQLiteDatabase database = mcHelper.getWritableDatabase();

        McEnumUri mcEnumUri = matcher.matchUri(uri);
        int updated;

        switch (mcEnumUri) {
            case DEVICES_ID:
                String devId = McContract.Device.getDeviceIdFromUri(uri);
                updated = database.update(McContract.DEVICE_TABLE_NAME, values, McContract.Device.COLUMN_DEVICE_ID + "=?", new String[]{devId});
                Logger.log(LOG_TAG, "Devices Updated:" + updated, Log.VERBOSE);
                if (updated > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri.toString());

        }

        return updated;
    }
}
