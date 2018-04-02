package com.mdmobile.cyclops.utils;

import android.content.ContentValues;
import android.os.Bundle;

import com.mdmobile.cyclops.dataModels.api.ServerInfo;
import com.mdmobile.cyclops.dataModels.api.User;
import com.mdmobile.cyclops.provider.McContract;

import java.util.ArrayList;

/**
 * Helper methods to get data ready for DB
 */

public class DbData {

    public static ContentValues prepareScriptValues(String name, String description, String script) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(McContract.Script.TITLE, name);
        contentValues.put(McContract.Script.DESCRIPTION, description);
        contentValues.put(McContract.Script.SCRIPT, script);
        return contentValues;
    }


    private static ContentValues getMsContentValues(ServerInfo.ManagementServer server) {
        ContentValues msValues = new ContentValues();
        msValues.put(McContract.MsInfo.NAME, server.getName());
        msValues.put(McContract.MsInfo.FULLY_QUALIFIED_NAME, server.getFqdn());
        msValues.put(McContract.MsInfo.DESCRIPTION, server.getDescription());
        msValues.put(McContract.MsInfo.MAC_ADDRESS, server.getMacAddress());
        msValues.put(McContract.MsInfo.PORT_NUMBER, server.getPortNumber());
        msValues.put(McContract.MsInfo.STATUS_TIME, server.getStatusTime());
        msValues.put(McContract.MsInfo.STATUS, server.getStatus());
        msValues.put(McContract.MsInfo.TOTAL_USER_COUNT, server.getTotalConsoleUsers());
        return msValues;
    }

    public static ContentValues prepareMsValues(ServerInfo.ManagementServer server) {
        return getMsContentValues(server);
    }

    public static ContentValues[] prepareMsValues(ArrayList<ServerInfo.ManagementServer> servers) {
        ContentValues[] msValuesArray = new ContentValues[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            msValuesArray[i] = getMsContentValues(servers.get(i));
        }
        return msValuesArray;
    }


    private static ContentValues getDsContentValues(ServerInfo.DeploymentServer server) {
        ContentValues values = new ContentValues();
        values.put(McContract.DsInfo.NAME, server.getName());
        values.put(McContract.DsInfo.STATUS, server.getStatus());
        values.put(McContract.DsInfo.CONNECTED, server.getConnected() ? 1 : 0);
        values.put(McContract.DsInfo.PRIMARY_AGENT_ADDRESS, server.getPrimaryAgentAddress());
        values.put(McContract.DsInfo.SECONDARY_AGENT_ADDRESS, server.getSecondaryAgentAddress());
        values.put(McContract.DsInfo.DEVICE_MANAGEMENT_ADDRESS, server.getDeviceManagementAddress());
        values.put(McContract.DsInfo.PRIMARY_MANAGEMENT_ADDRESS, server.getPrimaryManagementAddress());
        values.put(McContract.DsInfo.SECONDARY_MANAGEMENT_ADDRESS, server.getSecondaryManagementAddress());
        values.put(McContract.DsInfo.RULE_RELOAD, server.getRuleReload());
        values.put(McContract.DsInfo.SCHEDULE_INTERVAL, server.getScheduleInterval());
        values.put(McContract.DsInfo.MIN_THREADS, server.getMinThreads());
        values.put(McContract.DsInfo.MAX_THREADS, server.getMaxThread());
        values.put(McContract.DsInfo.MAX_BURST_THREADS, server.getMaxBurstThreads());
        values.put(McContract.DsInfo.DEVICES_CONNECTED, server.getConnectedDeviceCount());
        values.put(McContract.DsInfo.MANAGERS_CONNECTED, server.getConnectedManagerCount());
        values.put(McContract.DsInfo.QUEUE_LENGTH, server.getMsgQueueLength());
        values.put(McContract.DsInfo.CURRENT_THREAD_COUNT, server.getMsgQueueLength());
        values.put(McContract.DsInfo.PULSE_WAIT_INTERVAL, server.getPulseWaitInterval());

        return values;
    }

    public static ContentValues prepareDsValues(ServerInfo.DeploymentServer server) {
        return getDsContentValues(server);
    }

    public static ContentValues[] prepareDsValues(ArrayList<ServerInfo.DeploymentServer> servers) {
        ContentValues[] dsValuesArray = new ContentValues[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            dsValuesArray[i] = getDsContentValues(servers.get(i));
        }
        return dsValuesArray;
    }

    private static ContentValues getUserContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(McContract.UserInfo.NAME, user.getName());
        values.put(McContract.UserInfo.DISPLAYED_NAME, user.getDisplayName());
        values.put(McContract.UserInfo.KIND, user.getKind());
        values.put(McContract.UserInfo.IS_EULA_ACCEPTED, user.getEulaAccepted());
        values.put(McContract.UserInfo.EULA_ACCEPTANCE_DATE, user.getEulaAcceptanceDate());
        values.put(McContract.UserInfo.IS_LOCKED, user.getAccountLocked());
        values.put(McContract.UserInfo.NUMBER_OF_FAILED_LOGIN, user.getNumberOfFailedLogins());

        return values;
    }

    public static ContentValues prepareUserValues(User user) {
        return getUserContentValues(user);
    }

    public static ContentValues[] prepareUserValues(ArrayList<User> users) {
        ContentValues[] userValues = new ContentValues[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userValues[i] = getUserContentValues(users.get(i));
        }
        return userValues;
    }

    public static Bundle getDeviceExtraInfo(String extraInfo) {
        String[] extras = extraInfo.split(";");
        String[] temp;
        Bundle extraBundle = new Bundle();
        for (String extra : extras) {
            temp = extra.split("=");
            if (temp.length == 1) {
                extraBundle.putString(temp[0], "N/A");
                continue;
            }
            temp[0] = temp[0].substring(1, temp[0].length());
            extraBundle.putString(temp[0], temp[1]);
        }
        return extraBundle;
    }

    public static ContentValues prepareServerInfoValues(ServerInfo serverInfo) {
        ContentValues serverValues = new ContentValues();
        serverValues.put(McContract.ServerInfo.PRODUCT_VERSION, serverInfo.getProductVersion());
        serverValues.put(McContract.ServerInfo.PRODUCT_BUILD_NUMBER, serverInfo.getProductVersionBuild());
        return serverValues;
    }
}
