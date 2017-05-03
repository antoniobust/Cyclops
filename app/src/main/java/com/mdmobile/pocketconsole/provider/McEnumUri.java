package com.mdmobile.pocketconsole.provider;

/**
 * This class provides all the information about uris for the content provider
 * The uriMatcher will go through this list of enums to build its schema
 */

public enum McEnumUri {
    //TODO:fix double uris
    //Uri matcher codes
    DEVICES(500, McContract.DEVICE_TABLE_NAME, false, McContract.DEVICE_TABLE_NAME),

    DEVICES_ID(502, McContract.DEVICE_TABLE_NAME + "/*", true, McContract.DEVICE_TABLE_NAME),

    DEVICES_ID_VERBOSE(503, McContract.DEVICE_TABLE_NAME + "/*", false, McContract.DEVICE_TABLE_NAME),

    CUSTOM_DATA_ID(504, McContract.CUSTOM_DATA_TABLE_NAME + "/*", true, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_DATA_ID_DEVICES(505, McContract.CUSTOM_DATA_TABLE_NAME + "/*", false, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_DATA(506, McContract.CUSTOM_DATA_TABLE_NAME, false, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_DATA_DEVICES(507, McContract.CUSTOM_DATA_TABLE_NAME, false, McContract.CUSTOM_DATA_TABLE_NAME),

    CUSTOM_ATTRIBUTE_ID(508, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME + "/*", true, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    CUSTOM_ATTRIBUTE_DEVICE(509, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME + "/*",false, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    CUSTOM_ATTRIBUTE(510, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME, false, McContract.CUSTOM_ATTRIBUTE_TABLE_NAME),

    MANAGEMENTS_SERVER_ID(511, McContract.MANAGEMENT_SERVER_TABLE_NAME + "/*", true, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    MANAGEMENT_SERVERS(512, McContract.MANAGEMENT_SERVER_TABLE_NAME, false, McContract.MANAGEMENT_SERVER_TABLE_NAME),

    DEPLOYMENT_SERVER_ID(513, McContract.DEPLOYMENT_SERVER_TABLE_NAME + "/*", true, McContract.DEPLOYMENT_SERVER_TABLE_NAME),

    DEPLOYMENT_SERVERS(514, McContract.DEPLOYMENT_SERVER_TABLE_NAME, false, McContract.DEPLOYMENT_SERVER_TABLE_NAME);



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
