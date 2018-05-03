package com.mdmobile.cyclops.utils;

import com.mdmobile.cyclops.provider.McContract;

public class QueryUtility {

    public static String buildServerInfoInnerJoin(String tableName) {
        return tableName + " INNER JOIN " + McContract.SERVER_INFO_TABLE_NAME + " ON "
                + McContract.SERVER_INFO_TABLE_NAME + "." + McContract.ServerInfo._ID
                + "=" + tableName + ".ServerID";
    }

    public static String buildServerNameWhereSubQuery(String tableName, String serverName){
        return  tableName + "." + McContract.UserInfo.SERVER_ID
                + " = (SELECT " + McContract.UserInfo._ID + " from "+ McContract.SERVER_INFO_TABLE_NAME + " WHERE "
                + McContract.ServerInfo.NAME + " = \"" + serverName + "\")";
    }

}
