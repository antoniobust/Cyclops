package com.mdmobile.pocketconsole.provider;

/**
 * This class provides all the information about uris for the content provider
 * The uriMatcher will go through this list of enums to build its schema
 */

public enum McEnumUri {
    //Uri matcher codes
    DEVICES(500, McContract.DEVICE_TABLE_NAME, false, McContract.DEVICE_TABLE_NAME),

    DEVICES_GROUP_BY (501, McContract.DEVICE_TABLE_NAME + "/GROUP_BY/*",false,McContract.DEVICE_TABLE_NAME),

    DEVICES_ID(502, McContract.DEVICE_TABLE_NAME + "/*", true, McContract.DEVICE_TABLE_NAME),

    CUSTOM_DATA_ID(503, McContract.CUSTOM_DATA_TABLE_NAME + "/*", true, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_DATA(504, McContract.CUSTOM_DATA_TABLE_NAME, false, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_ATTRIBUTE_ID(505, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME + "/*", true, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    CUSTOM_ATTRIBUTE(506, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME, false, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    MANAGEMENTS_SERVER_ID(507, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/*", true, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MANAGEMENT_SERVERS(508, McContract.MANAGEMENT_SERVER_TABLE_NAME, false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DEPLOYMENT_SERVER_ID(509, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/*", true, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    DEPLOYMENT_SERVERS(510, McContract.DEPLOYMENT_SERVER_TABLE_NAME, false, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    INSTALLED_APPLICATIONS_ON_DEVICE(511, McContract.INSTALLED_APPLICATION_TABLE_NAME + "/*", false, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    INSTALLED_APPLICATION_ID(512, McContract.INSTALLED_APPLICATION_TABLE_NAME, true, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    INSTALLED_APPLICATION_PKG_NAME(513, McContract.INSTALLED_APPLICATION_TABLE_NAME, true, McContract.INSTALLED_APPLICATION_TABLE_NAME),

    SCRIPTS(514, McContract.SCRIPT_TABLE_NAME, false, McContract.SCRIPT_TABLE_NAME),

    SCRIPT_ID(515, McContract.SCRIPT_TABLE_NAME + "/*", true, McContract.SCRIPT_TABLE_NAME),

    USERS(516, McContract.USER_TABLE_NAME, false, McContract.USER_TABLE_NAME),

    USER_ID(517, McContract.USER_TABLE_NAME + "/*", true, McContract.USER_TABLE_NAME);

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
