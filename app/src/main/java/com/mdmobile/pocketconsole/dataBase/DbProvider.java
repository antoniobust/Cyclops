package com.mdmobile.pocketconsole.dataBase;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DbProvider extends ContentProvider {

    //Uri matcher codes
    private final static int DEVICE = 0;
    private final static int DEVICE_VERBOSE = 1;
    private final static int ALL_DEVICES = 2;
    private final static int ALL_DEVICES_VERBOSE = 3;
    private final static int PROFILE = 4;
    private final static int PROFILE_VERBOSE = 5;
    private final static int ALL_PROFILES = 6;
    private final static int ALL_PROFILES_VERBOSE = 7;
    private final static int CUSTOM_DATA = 8;
    private final static int CUSTOM_DATA_DEVICE = 9;
    private final static int ALL_CUSTOM_DATA = 10;
    private final static int CUSTOM_ATTRIBUTE = 11;
    private final static int CUSTOM_ATTRIBUTE_DEVICE = 12;
    private final static int ALL_CUSTOM_ATTRIBUTE = 13;
    private final static int ALL_MANAGEMENT_SERVER = 14;
    private final static int MANAGEMENT_SERVER = 15;
    private final static int ALL_DEPLOYMENT_SERVER = 16;
    private final static int DEPLOYMENT_SERVER = 17;

    //Matcher
    private static final UriMatcher matcher = buildUriMatcher();

    //DB helper
    private DbHelper dbHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, DEVICE);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, DEVICE_VERBOSE);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, ALL_DEVICES);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, ALL_DEVICES_VERBOSE);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, PROFILE);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, PROFILE_VERBOSE);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, ALL_PROFILES);
        uriMatcher.addURI(authority, DbContract.DEVICE_TABLE_NAME, ALL_PROFILES_VERBOSE);
        uriMatcher.addURI(authority, DbContract.CUSTOM_DATA_TABLE_NAME, CUSTOM_DATA);
        uriMatcher.addURI(authority, DbContract.CUSTOM_DATA_DEVICE_TABLE_NAME, CUSTOM_DATA_DEVICE);
        uriMatcher.addURI(authority, DbContract.CUSTOM_DATA_TABLE_NAME, ALL_CUSTOM_DATA);
        uriMatcher.addURI(authority, DbContract.CUSTOM_ATTRIBUTE_TABLE_NAME, CUSTOM_ATTRIBUTE);
        uriMatcher.addURI(authority, DbContract.CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME, CUSTOM_ATTRIBUTE_DEVICE);
        uriMatcher.addURI(authority, DbContract.CUSTOM_DATA_TABLE_NAME, ALL_CUSTOM_ATTRIBUTE);
        uriMatcher.addURI(authority, DbContract.COMPLIANCE_ITEM_TABLE_NAME, ALL_MANAGEMENT_SERVER);
        uriMatcher.addURI(authority, DbContract.COMPLIANCE_ITEM_TABLE_NAME, MANAGEMENT_SERVER);
        uriMatcher.addURI(authority, DbContract.COMPLIANCE_ITEM_TABLE_NAME, ALL_DEPLOYMENT_SERVER);
        uriMatcher.addURI(authority, DbContract.COMPLIANCE_ITEM_TABLE_NAME, DEPLOYMENT_SERVER);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (matcher.match(uri)) {
            case DEVICE:
                return DbContract.Device.SINGLE_CONTENT_TYPE;
            case DEVICE_VERBOSE:
                return DbContract.Device.SINGLE_CONTENT_TYPE;
            case ALL_DEVICES:
                return DbContract.Device.DIR_CONTENT_TYPE;
            case ALL_DEVICES_VERBOSE:
                return DbContract.Device.DIR_CONTENT_TYPE;
            case PROFILE:
                return null;
            case PROFILE_VERBOSE:
                return null;
            case ALL_PROFILES:
                return null;
            case ALL_PROFILES_VERBOSE:
                return null;
            case CUSTOM_DATA:
                return DbContract.CustomData.SINGLE_CONTENT_TYPE;
            case CUSTOM_DATA_DEVICE:
                return DbContract.CustomData.DIR_CONTENT_TYPE;
            case ALL_CUSTOM_DATA:
                return DbContract.CustomData.DIR_CONTENT_TYPE;
            case CUSTOM_ATTRIBUTE:
                return DbContract.CustomAttribute.SINGLE_CONTENT_TYPE;
            case CUSTOM_ATTRIBUTE_DEVICE:
                return DbContract.CustomAttribute.DIR_CONTENT_TYPE;
            case ALL_CUSTOM_ATTRIBUTE:
                return DbContract.CustomAttribute.DIR_CONTENT_TYPE;
            case ALL_MANAGEMENT_SERVER:
                return DbContract.ManagementServer.DIR_CONTENT_TYPE;
            case MANAGEMENT_SERVER:
                return DbContract.ManagementServer.SINGLE_CONTENT_TYPE;
            case ALL_DEPLOYMENT_SERVER:
                return DbContract.DeploymentServer.DIR_CONTENT_TYPE;
            case DEPLOYMENT_SERVER:
                return DbContract.DeploymentServer.SINGLE_CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);


        }
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder, @Nullable CancellationSignal cancellationSignal) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
