package com.mdmobile.cyclops.provider;

/**
 * This class provides all the information about uris for the content provider
 * The uriMatcher will go through this list of enums to build its schema
 */

public enum McEnumUri {
    //Uri matcher codes
    DEVICES(500, McContract.DEVICE_TABLE_NAME, false, McContract.DEVICE_TABLE_NAME),

    DEVICES_BY_SERVER(501, McContract.DEVICE_TABLE_NAME + "/server/*", false, McContract.DEVICE_TABLE_NAME),

    DEVICES_ID(502, McContract.DEVICE_TABLE_NAME + "/*", true, McContract.DEVICE_TABLE_NAME),

    MS_BY_SERVER(503, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/server/*", false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MS_ID(504, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/*", true, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MS_LIST(505, McContract.MANAGEMENT_SERVER_TABLE_NAME, false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DS_BY_SERVER(506, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/server/*", false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DS_ID(507, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/*", true, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    DS_LIST(508, McContract.DEPLOYMENT_SERVER_TABLE_NAME, false, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    APPLICATIONS_ON_DEVICE(509, McContract.INSTALLED_APPLICATION_TABLE_NAME + "/*", false, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    APPLICATION_ID(510, McContract.INSTALLED_APPLICATION_TABLE_NAME, true, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    APPLICATION_PKG_NAME(511, McContract.INSTALLED_APPLICATION_TABLE_NAME, true, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    SCRIPTS(512, McContract.SCRIPT_TABLE_NAME, false, McContract.SCRIPT_TABLE_NAME),

    SCRIPT_ID(513, McContract.SCRIPT_TABLE_NAME + "/*", true, McContract.SCRIPT_TABLE_NAME),

    USERS_BY_SERVER(514, McContract.USER_TABLE_NAME + "/server/*", false, McContract.USER_TABLE_NAME),

    USERS(515, McContract.USER_TABLE_NAME, false, McContract.USER_TABLE_NAME),

    USER_ID(516, McContract.USER_TABLE_NAME + "/*", true, McContract.USER_TABLE_NAME),

    PROFILES(517, McContract.PROFILE_TABLE_NAME, false, McContract.PROFILE_TABLE_NAME),

    PROFILE_DEVICE_ID(517, McContract.PROFILE_TABLE_NAME + "/device/*", true, McContract.PROFILE_TABLE_NAME),

    PROFILE_ID(518, McContract.PROFILE_TABLE_NAME + "/*", true, McContract.PROFILE_TABLE_NAME),

    SERVERS(519, McContract.SERVER_INFO_TABLE_NAME, false, McContract.SERVER_INFO_TABLE_NAME),

    SERVER_BY_NAME(520, McContract.SERVER_INFO_TABLE_NAME + "/*", true, McContract.SERVER_INFO_TABLE_NAME);


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
