package com.mdmobile.cyclops.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.QueryUtility;

import java.util.Arrays;
import java.util.HashMap;

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

        String groupBy = "";

        if (database == null) {
            database = mcHelper.getWritableDatabase();
        }

        SQLiteQueryBuilder mQueryBuilder = new SQLiteQueryBuilder();
        McEnumUri mcEnumUri = matcher.matchUri(uri);
        Cursor c;
        String devId;

        switch (mcEnumUri) {
            case DEVICES: {
                mQueryBuilder.setTables(McContract.DEVICE_TABLE_NAME);
                break;
            }
            case DEVICES_ID: {
                devId = McContract.Device.getDeviceIdFromUri(uri);
                mQueryBuilder.setTables(McContract.DEVICE_TABLE_NAME);
                mQueryBuilder.appendWhere(McContract.Device.COLUMN_DEVICE_ID + "='" + devId + "'");
                break;
            }

            case DEVICES_BY_SERVER: {
                String serverName = McContract.Device.getServerNameFromUri(uri);
                String join = McContract.DEVICE_TABLE_NAME + " INNER JOIN " + McContract.SERVER_INFO_TABLE_NAME + " ON "
                        + McContract.SERVER_INFO_TABLE_NAME + "." + McContract.ServerInfo._ID + " = "
                        + McContract.DEVICE_TABLE_NAME + "." + McContract.Device.COLUMN_SERVER_ID;
                mQueryBuilder.setTables(join);
                mQueryBuilder.appendWhere(McContract.SERVER_INFO_TABLE_NAME + "." + McContract.ServerInfo.NAME + "='" + serverName + "'");
                break;
            }

            case APPLICATIONS_ON_DEVICE: {
                devId = McContract.InstalledApplications.getDeviceIdFromUri(uri);
                mQueryBuilder.setTables(McContract.INSTALLED_APPLICATION_TABLE_NAME);
                mQueryBuilder.appendWhere(McContract.InstalledApplications.DEVICE_ID + "='" + devId + "'");
                break;
            }

            case APPLICATION_PKG_NAME: {
                String packageName = McContract.InstalledApplications.getAppPackageNameFromUri(uri);
                mQueryBuilder.setTables(McContract.INSTALLED_APPLICATION_TABLE_NAME);
                mQueryBuilder.appendWhere(McContract.InstalledApplications.APPLICATION_ID + "=" + packageName);
                break;
            }

            case SCRIPTS: {
                mQueryBuilder.setTables(McContract.SCRIPT_TABLE_NAME);
                break;
            }

            case SCRIPT_ID: {
                String scriptId = McContract.Script.getScriptIdFromUri(uri);
                mQueryBuilder.setTables(McContract.SCRIPT_TABLE_NAME);
                mQueryBuilder.appendWhere(McContract.Script._ID + "=" + scriptId);
                break;
            }

            case MS_LIST: {
                mQueryBuilder.setTables(McContract.MANAGEMENT_SERVER_TABLE_NAME);
                break;
            }

            case MS_BY_SERVER: {
                String serverID = McContract.getServerIdFromUri(uri);
                mQueryBuilder.setTables(QueryUtility.buildServerInfoInnerJoin(McContract.MANAGEMENT_SERVER_TABLE_NAME));
                mQueryBuilder.appendWhere(McContract.MANAGEMENT_SERVER_TABLE_NAME + "." + McContract.MsInfo.SERVER_ID + "='" + serverID + "'");
                break;
            }
            case DS_LIST:
                mQueryBuilder.setTables(McContract.DEPLOYMENT_SERVER_TABLE_NAME);
                break;

            case DS_BY_SERVER: {
                String serverID = McContract.getServerIdFromUri(uri);
                mQueryBuilder.setTables(QueryUtility.buildServerInfoInnerJoin(McContract.DEPLOYMENT_SERVER_TABLE_NAME));
                mQueryBuilder.appendWhere(McContract.DEPLOYMENT_SERVER_TABLE_NAME + "." + McContract.DsInfo.SERVER_ID + "='" + serverID + "'");
                break;
            }

            case USERS_BY_SERVER: {
                String serverID = McContract.getServerIdFromUri(uri);
                mQueryBuilder.setTables(QueryUtility.buildServerInfoInnerJoin(McContract.USER_TABLE_NAME));
                mQueryBuilder.appendWhere(McContract.USER_TABLE_NAME + "." + McContract.UserInfo.SERVER_ID + "='" + serverID + "'");
                break;
            }

            case PROFILE_DEVICE_ID: {
                devId = McContract.Profile.getUriId(uri);
                String join = McContract.PROFILE_TABLE_NAME + " INNER JOIN "
                        + McContract.PROFILE_DEVICE_TABLE_NAME + " ON " + McContract.PROFILE_TABLE_NAME + "." + McContract.Profile._ID
                        + " = " + McContract.PROFILE_DEVICE_TABLE_NAME + "." + McContract.ProfileDevice.PROFILE_ID
                        + " INNER JOIN " + McContract.DEVICE_TABLE_NAME + " ON " + McContract.DEVICE_TABLE_NAME + "." + McContract.Device.COLUMN_DEVICE_ID
                        + " = " + McContract.PROFILE_DEVICE_TABLE_NAME + "." + McContract.ProfileDevice.DEVICE_ID;

                mQueryBuilder.setTables(join);
                HashMap<String, String> map = new HashMap<>();
                map.put(McContract.Profile.NAME, McContract.Profile.NAME);
                mQueryBuilder.setProjectionMap(null);
                mQueryBuilder.appendWhere(McContract.DEVICE_TABLE_NAME + "." + McContract.Device.COLUMN_DEVICE_ID + " = '" + devId + "'");
                break;
            }
            case SERVERS: {
                mQueryBuilder.setTables(McContract.SERVER_INFO_TABLE_NAME);
                break;
            }
            case SERVER_BY_NAME: {
                mQueryBuilder.setTables(McContract.SERVER_INFO_TABLE_NAME);
                mQueryBuilder.appendWhere(McContract.ServerInfo.NAME + "='" + McContract.ServerInfo.getServerNameFromUri(uri) + "'");
                break;
            }

            default:
                throw new UnsupportedOperationException("Unsupported URI: " + uri.toString());
        }
        //Execute built query
        c = mQueryBuilder.query(database, projection, selection, selectionArgs, groupBy, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        Logger.log(LOG_TAG, "insert( uri:" + uri.toString() + " , objects: " + values.length + ")", Log.VERBOSE);

        if (database == null) {
            database = mcHelper.getWritableDatabase();
        }
        McEnumUri mcEnumUri = matcher.matchUri(uri);
        int dataInserted = 0;

        switch (mcEnumUri) {
            case DEVICES:
                for (ContentValues contentValues : values) {

                    if (database.insert(McContract.DEVICE_TABLE_NAME,
                            null, contentValues) > 0) {
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " devices in DB", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "Device Bulk insert didn't insert devices correctly", Log.ERROR);
                }
                return dataInserted;

            case APPLICATION_PKG_NAME:
                for (ContentValues contentValues : values) {
                    if (database.insert(McContract.INSTALLED_APPLICATION_TABLE_NAME, null, contentValues) > 0) {
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " apps in DB", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "Device Bulk insert didn't insert devices correctly", Log.ERROR);
                }
                return dataInserted;

            case SERVERS:
                for (ContentValues c : values) {
                    if (database.insert(McContract.SERVER_INFO_TABLE_NAME, null, c) > 0) {
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " servers in DB", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "Server Bulk insert didn't insert info correctly", Log.ERROR);
                }
                return dataInserted;

            case MS_LIST:
                for (ContentValues contentValues : values) {
                    if (database.insert(McContract.MANAGEMENT_SERVER_TABLE_NAME, null, contentValues) > 0) {
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " MS in DB", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "MS bulk insert didn't insert values correctly", Log.ERROR);
                }
                return dataInserted;

            case DS_LIST:
                for (ContentValues contentValues : values) {
                    if (database.insert(McContract.DEPLOYMENT_SERVER_TABLE_NAME, null, contentValues) > 0) {
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " DS in DB", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "DS bulk insert didn't insert values correctly", Log.ERROR);
                }
                return dataInserted;

            case USERS:
                for (ContentValues contentValues : values) {
                    if (database.insert(McContract.USER_TABLE_NAME, null, contentValues) > 0) {
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " users in DB", Log.VERBOSE);
                    getContext().getContentResolver().notifyChange(uri, null);
                } else {
                    Logger.log(LOG_TAG, "Users bulk insert didn't insert values correctly", Log.ERROR);
                }
                return dataInserted;


            case PROFILE_DEVICE_ID:
                for (ContentValues contentValues : values) {
                    long newRowID =
                            database.insert(mcEnumUri.tableName, null, contentValues);
                    if (newRowID > 0) {
                        database.execSQL("INSERT INTO " + McContract.PROFILE_DEVICE_TABLE_NAME + " ("
                                + McContract.ProfileDevice.PROFILE_ID + " , " + McContract.ProfileDevice.DEVICE_ID + ") VALUES ('"
                                + newRowID + "','" + McContract.Device.getDeviceIdFromUri(uri) + "');");
                        dataInserted++;
                    }
                }
                if (dataInserted == values.length) {
                    Logger.log(LOG_TAG, "Bulk inserted " + dataInserted + " profiles(" +
                            McContract.Device.getDeviceIdFromUri(uri) + ") in DB", Log.VERBOSE);

                } else {
                    Logger.log(LOG_TAG, "Profiles bulk insert didn't insert values correctly", Log.ERROR);
                }
                return dataInserted;

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
        long newRowID =
                database.insert(mcEnumUri.tableName, null, contentValues);

        switch (mcEnumUri) {
            case DEVICES_BY_SERVER:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert device in DB, server: " + McContract.Device.getServerNameFromUri(uri), Log.ERROR);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return McContract.Device.buildUriWithID(newRowID);

            case APPLICATION_ID:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert application in DB", Log.ERROR);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return McContract.InstalledApplications.buildUriWithId(newRowID);

            case SCRIPTS:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert script in DB", Log.ERROR);
                    return null;
                }
                return McContract.Script.buildUriWithId(newRowID);

            case MS_LIST:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert MS in DB", Log.ERROR);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return McContract.MsInfo.buildUriWithMsId(newRowID);

            case DS_LIST:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert DS in DB", Log.ERROR);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return McContract.DsInfo.buildUriWithDsId(newRowID);

            case USERS:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert User in DB", Log.ERROR);
                    return null;
                }
                return McContract.UserInfo.buildUriWithUserId(newRowID);

            case PROFILE_DEVICE_ID:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert Profiles in DB", Log.ERROR);
                    return null;
                }
                database.execSQL("INSERT INTO " + McContract.PROFILE_DEVICE_TABLE_NAME + " ("
                        + McContract.ProfileDevice.PROFILE_ID + " , " + McContract.ProfileDevice.DEVICE_ID + ") VALUES ('"
                        + newRowID + "','" + McContract.Device.getDeviceIdFromUri(uri) + "');");
                return McContract.Profile.buildUriWithID(String.valueOf(newRowID));

            case SERVERS:
                if (newRowID < 1) {
                    Logger.log(LOG_TAG, "Impossible to insert ServerInfo in DB", Log.ERROR);
                    return null;
                }
                return McContract.ServerInfo.buildUriWithId(newRowID);
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
            case DEVICES: {
                deleted = database.delete(McContract.DEVICE_TABLE_NAME, null, null);
                Logger.log(LOG_TAG, "Devices deleted:" + deleted, Log.VERBOSE);
                break;
            }

            case DEVICES_BY_SERVER: {
                String serverName = McContract.Device.getServerNameFromUri(uri);
                String where = McContract.Device.COLUMN_SERVER_ID + " = ?";
                String whereValue = "( SELECT " + McContract.ServerInfo.NAME + " FROM " + McContract.SERVER_INFO_TABLE_NAME
                        + " WHERE " + McContract.ServerInfo.NAME + " = " + serverName + ")";
                String[] parameters = {whereValue};
                deleted = database.delete(McContract.DEVICE_TABLE_NAME, where, parameters);
                if (deleted > 0) {
                    Logger.log(LOG_TAG, "Server(" + serverName + ") devices  deleted", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "Server(" + serverName + ") devices NOT deleted", Log.VERBOSE);
                }
                break;
            }

            case DEVICES_ID: {
                String devId = McContract.Device.getDeviceIdFromUri(uri);
                String where = McContract.Device.COLUMN_DEVICE_ID + " = ?";
                String[] parameters = {devId};
                deleted = database.delete(McContract.DEVICE_TABLE_NAME, where, parameters);
                if (deleted > 0) {
                    Logger.log(LOG_TAG, "Device (" + devId + ") deleted", Log.VERBOSE);
                } else {
                    Logger.log(LOG_TAG, "Device (" + devId + ") not deleted", Log.VERBOSE);
                }
                break;
            }

            case APPLICATIONS_ON_DEVICE: {
                String devId = McContract.InstalledApplications.getDeviceIdFromUri(uri);
                deleted = database.delete(McContract.DEVICE_TABLE_NAME, McContract.InstalledApplications.DEVICE_ID + " =?",
                        new String[]{devId});
                Logger.log(LOG_TAG, "InstalledApps deleted:" + deleted, Log.VERBOSE);
                break;
            }
            case MS_LIST: {
                deleted = database.delete(McContract.MANAGEMENT_SERVER_TABLE_NAME, null, null);
                Logger.log(LOG_TAG, "MS deleted:" + deleted, Log.VERBOSE);
                break;
            }
            case DS_LIST: {
                deleted = database.delete(McContract.DEPLOYMENT_SERVER_TABLE_NAME, null, null);
                Logger.log(LOG_TAG, "DS deleted:" + deleted, Log.VERBOSE);
                break;
            }
            case USER_ID: {
                String id = McContract.UserInfo.getUserIdFromUri(uri);
                deleted = database.delete(McContract.USER_TABLE_NAME, McContract.UserInfo._ID, new String[]{id});
                Logger.log(LOG_TAG, "User deleted (" + id + "): " + deleted, Log.VERBOSE);
                break;
            }
            case USERS: {
                deleted = database.delete(McContract.USER_TABLE_NAME, null, null);
                Logger.log(LOG_TAG, "User deleted: " + deleted, Log.VERBOSE);
                break;
            }

            case PROFILE_ID: {
                String profileId = McContract.Profile.getUriId(uri);
                deleted = database.delete(McContract.PROFILE_DEVICE_TABLE_NAME,
                        McContract.ProfileDevice.PROFILE_ID + "=?", new String[]{profileId});
                Logger.log(LOG_TAG, "Profile (" + profileId + ") deleted", Log.VERBOSE);
                break;
            }

            case PROFILE_DEVICE_ID: {
                String devId = McContract.Profile.getUriId(uri);
                deleted = database.delete(McContract.PROFILE_DEVICE_TABLE_NAME,
                        McContract.ProfileDevice.DEVICE_ID + "=?", new String[]{devId});
                Logger.log(LOG_TAG, "Profiles deleted:" + deleted + " (device " + devId + ")", Log.VERBOSE);
                break;
            }

            case SERVERS: {
                deleted = database.delete(McContract.SERVER_INFO_TABLE_NAME, selection, selectionArgs);
                Logger.log(LOG_TAG, "Server deleted:" + deleted, Log.VERBOSE);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri.toString());

        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
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
                Logger.log(LOG_TAG, "Device (" + devId + ") Updated:" + updated, Log.VERBOSE);
                if (updated > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            case SERVER_BY_NAME:
                String serverName = McContract.ServerInfo.getServerNameFromUri(uri);
                updated = database.update(McContract.SERVER_INFO_TABLE_NAME, values, McContract.ServerInfo.NAME + "=?", new String[]{serverName});
                Logger.log(LOG_TAG, " Server(" + serverName + ") updated", Log.VERBOSE);
                if (updated > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri.toString());
        }
        return updated;
    }

    public void deleteDatabase() {

    }
}
