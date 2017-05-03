package com.mdmobile.pocketconsole.provider;

/**
 * This class provides all the information about uris for the content provider
 * The uriMatcher will go through this list of enums to build its schema
 */

public enum McEnumUri {
    //Uri matcher codes
    DEVICES(500, McContract.DEVICE_TABLE_NAME, false, McContract.DEVICE_TABLE_NAME),

    DEVICES_ID(501, McContract.DEVICE_TABLE_NAME + "/*", true, McContract.DEVICE_TABLE_NAME),

    CUSTOM_DATA_ID(502, McContract.CUSTOM_DATA_TABLE_NAME + "/*", true, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_DATA(503, McContract.CUSTOM_DATA_TABLE_NAME, false, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_ATTRIBUTE_ID(504, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME + "/*", true, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    CUSTOM_ATTRIBUTE(505, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME, false, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    MANAGEMENTS_SERVER_ID(506, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/*", true, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MANAGEMENT_SERVERS(507, McContract.MANAGEMENT_SERVER_TABLE_NAME, false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DEPLOYMENT_SERVER_ID(508, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/*", true, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    DEPLOYMENT_SERVERS(509, McContract.DEPLOYMENT_SERVER_TABLE_NAME, false, McContract.DEPLOYMENT_SERVER_TABLE_NAME);


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
