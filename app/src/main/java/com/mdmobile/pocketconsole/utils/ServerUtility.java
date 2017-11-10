package com.mdmobile.pocketconsole.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.gson.ServerInfo;

import static android.content.Context.MODE_PRIVATE;
import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.API_SECRET_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.CLIENT_ID_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_ADDRESS_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_NAME_KEY;

/**
 * Utility class for server info methods
 */

public class ServerUtility {

    public static int SERVER_STOPPED = 0;
    public static int SERVER_STARTED = 1;
    public static int SERVER_DISABLED = 2;
    public static int SERVER_UNLICENSED = 3;
    public static int SERVER_DELETED = 4;
    public static int SERVER_STARTED_BUT_NOT_REGISTERED = 5;
    public static int SERVER_OFFLINE = 6;
    public static int SERVER_STATUS_UNKNOWN = 7;
    public static int SERVER_STARTED_BUT_NOT_CONNECTED = 5;


    public static void saveServerInfo(String serverName, String apiSecret, String clientId, String serverAddress) {

        SharedPreferences preferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(applicationContext.getString(R.string.server_name_preference), serverName);
        editor.putString(applicationContext.getString(R.string.api_secret_preference), apiSecret);
        editor.putString(applicationContext.getString(R.string.client_id_preference), clientId);
        editor.putString(applicationContext.getString(R.string.server_address_preference), serverAddress);

        editor.apply();
    }

    public static Bundle getServer() {

        SharedPreferences preferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE);
        String serverName = preferences.getString(applicationContext.getString(R.string.server_name_preference), null);
        String apiSecret = preferences.getString(applicationContext.getString(R.string.api_secret_preference), null);
        String clientId = preferences.getString(applicationContext.getString(R.string.client_id_preference), null);
        String address = preferences.getString(applicationContext.getString(R.string.server_address_preference), null);

        if (serverName != null && apiSecret != null && clientId != null && address != null) {
            Bundle bundle = new Bundle(4);
            bundle.putString(SERVER_NAME_KEY, serverName);
            bundle.putString(SERVER_ADDRESS_KEY, address);
            bundle.putString(CLIENT_ID_KEY, clientId);
            bundle.putString(API_SECRET_KEY, apiSecret);

            return bundle;
        } else {
            return null;
        }
    }

    public static int isServerOnline(ServerInfo.DeploymentServer server) {

        if (!server.getConnected()) {
            return SERVER_STARTED_BUT_NOT_CONNECTED;
        }

        switch (server.getStatus()) {
            case "Stopped":
                return SERVER_STOPPED;
            case "Started":
                return SERVER_STARTED;
            case "Disabled":
                return SERVER_DISABLED;
            case "Unlicensed":
                return 3;
            case "Deleted":
                return SERVER_DELETED;
            case "StartedButNotRegistered":
                return SERVER_STARTED_BUT_NOT_REGISTERED;
            case "Offline":
                return SERVER_OFFLINE;
            case "Unknown":
                return SERVER_STATUS_UNKNOWN;
            default:
                return SERVER_STATUS_UNKNOWN;
        }
    }

    public static int isServerOnline(ServerInfo.ManagementServer server) {
        switch (server.getStatus()) {
            case "Stopped":
                return SERVER_STOPPED;
            case "Started":
                return SERVER_STARTED;
            case "Disabled":
                return SERVER_DISABLED;
            case "Unlicensed":
                return 3;
            case "Deleted":
                return SERVER_DELETED;
            case "StartedButNotRegistered":
                return SERVER_STARTED_BUT_NOT_REGISTERED;
            case "Offline":
                return SERVER_OFFLINE;
            case "Unknown":
                return SERVER_STATUS_UNKNOWN;
            default:
                return SERVER_STATUS_UNKNOWN;
        }
    }

    public static void notifyServerStatus(String serverName, int status) {
        String message = applicationContext.getResources().getStringArray(R.array.server_notification_labels)[status];
        message = String.format(message, serverName);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(applicationContext, applicationContext.getPackageName())
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_block)
                .setContentTitle(applicationContext.getString(R.string.notification_server_status_title))
                .setContentText(message);


        NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());

    }
}
