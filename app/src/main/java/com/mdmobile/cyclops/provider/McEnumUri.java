package com.mdmobile.cyclops.provider;

/**
 * This class provides all the information about uris for the content provider
 * The uriMatcher will go through this list of enums to build its schema
 */

public enum McEnumUri {
    //Uri matcher codes
    DEVICES(500, McContract.DEVICE_TABLE_NAME, false, McContract.DEVICE_TABLE_NAME),

    DEVICES_BY_SERVER(501, McContract.DEVICE_TABLE_NAME + "/server/*", false, McContract.DEVICE_TABLE_NAME),

    DEVICES_GROUP_BY(502, McContract.DEVICE_TABLE_NAME + "/GROUP_BY/*", false, McContract.DEVICE_TABLE_NAME),

    DEVICES_ID(503, McContract.DEVICE_TABLE_NAME + "/*", true, McContract.DEVICE_TABLE_NAME),

    MS_BY_SERVER(504, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/server/*", false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MS_ID(505, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/*", true, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MS_LIST(506, McContract.MANAGEMENT_SERVER_TABLE_NAME, false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DS_BY_SERVER(507, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/server/*", false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DS_ID(508, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/*", true, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    DS_LIST(509, McContract.DEPLOYMENT_SERVER_TABLE_NAME, false, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    APPLICATIONS_ON_DEVICE(510, McContract.INSTALLED_APPLICATION_TABLE_NAME + "/*", false, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    APPLICATION_ID(511, McContract.INSTALLED_APPLICATION_TABLE_NAME, true, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    APPLICATION_PKG_NAME(512, McContract.INSTALLED_APPLICATION_TABLE_NAME, true, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    SCRIPTS(513, McContract.SCRIPT_TABLE_NAME, false, McContract.SCRIPT_TABLE_NAME),

    SCRIPT_ID(514, McContract.SCRIPT_TABLE_NAME + "/*", true, McContract.SCRIPT_TABLE_NAME),

    USERS_BY_SERVER(515, McContract.USER_TABLE_NAME + "/server/*", false, McContract.USER_TABLE_NAME),

    USERS(516, McContract.USER_TABLE_NAME, false, McContract.USER_TABLE_NAME),

    USER_ID(517, McContract.USER_TABLE_NAME + "/*", true, McContract.USER_TABLE_NAME),

    PROFILES(518, McContract.PROFILE_TABLE_NAME, false, McContract.PROFILE_TABLE_NAME),

    PROFILE_DEVICE_ID(519, McContract.PROFILE_TABLE_NAME + "/device/*", true, McContract.PROFILE_TABLE_NAME),

    PROFILE_ID(520, McContract.PROFILE_TABLE_NAME + "/*", true, McContract.PROFILE_TABLE_NAME),

    SERVERS(521, McContract.INSTANCE_INFO_TABLE_NAME, false, McContract.INSTANCE_INFO_TABLE_NAME),

    SERVER_BY_NAME(522, McContract.INSTANCE_INFO_TABLE_NAME + "/*", true, McContract.INSTANCE_INFO_TABLE_NAME);

    public String path, contentType, tableName;
    public int matcherCode;


    McEnumUri(int matcherCode, String path, boolean item, String tableName) {

        this.matcherCode = matcherCode;
        this.path = path;
        this.contentType = item ? McContract.makeItemContentType(tableName) :
                McContract.makeContentType(tableName);
        this.tableName = tableName;

    }
}
