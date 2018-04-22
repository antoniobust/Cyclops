package com.mdmobile.cyclops.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.dataModel.api.ServerInfo;
import com.mdmobile.cyclops.provider.McContract;

import static android.content.Context.MODE_PRIVATE;
import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;

/**
 * Utility class for server info methods
 */

public class ServerUtility {
    public static final String SERVER_ADDRESS_KEY = "ServerAddressKey";
    public static int SERVER_STOPPED = 0;
    public static int SERVER_STARTED = 1;
    public static int SERVER_DISABLED = 2;
    public static int SERVER_UNLICENSED = 3;
    public static int SERVER_DELETED = 4;
    public static int SERVER_STARTED_BUT_NOT_REGISTERED = 5;
    public static int SERVER_OFFLINE = 6;
    public static int SERVER_STATUS_UNKNOWN = 7;
    public static int SERVER_STARTED_BUT_NOT_CONNECTED = 5;

    public static boolean anyActiveServer() {
        SharedPreferences preferences = applicationContext
                .getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE);
        return preferences.contains(applicationContext.getString(R.string.server_name_preference));
    }

    public static Server getActiveServer() {
        SharedPreferences preferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE);
        String serverName = preferences.getString(applicationContext.getString(R.string.server_name_preference), null);
        String apiSecret = preferences.getString(applicationContext.getString(R.string.api_secret_preference), null);
        String clientId = preferences.getString(applicationContext.getString(R.string.client_id_preference), null);
        String address = preferences.getString(applicationContext.getString(R.string.server_address_preference), null);
        int version = preferences.getInt(applicationContext.getString(R.string.server_version_preference), -1);
        int build = preferences.getInt(applicationContext.getString(R.string.server_build_preference), -1);

        if (serverName != null && apiSecret != null && clientId != null && address != null) {
            return new Server(serverName, apiSecret, clientId, address, version, build);
        } else {
            throw new UnsupportedOperationException("Active server not found");
        }
    }

    public static void setActiveServer(Server server) {
        SharedPreferences.Editor editor = applicationContext
                .getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE).edit();
        editor.putString(applicationContext.getString(R.string.server_name_preference), server.getServerName());
        editor.putString(applicationContext.getString(R.string.api_secret_preference), server.getApiSecret());
        editor.putString(applicationContext.getString(R.string.client_id_preference), server.getClientId());
        editor.putString(applicationContext.getString(R.string.server_address_preference), server.getServerAddress());
        editor.putInt(applicationContext.getString(R.string.server_version_preference),server.getServerMajorVersion());
        editor.putInt(applicationContext.getString(R.string.server_build_preference),server.getBuildNumber());

        editor.apply();
    }

    public static void deactivateServer() {
        SharedPreferences.Editor editor =
                applicationContext.getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE).edit();
        editor.remove(applicationContext.getString(R.string.server_name_preference));
        editor.remove(applicationContext.getString(R.string.api_secret_preference));
        editor.remove(applicationContext.getString(R.string.client_id_preference));
        editor.remove(applicationContext.getString(R.string.server_address_preference));
        editor.apply();

    }

    public static int serverStatus(ServerInfo.DeploymentServer server) {

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
                return SERVER_UNLICENSED;
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

    public static int serverStatus(ServerInfo.ManagementServer server) {
        switch (server.getStatus()) {
            case "Stopped":
                return SERVER_STOPPED;
            case "Started":
                return SERVER_STARTED;
            case "Disabled":
                return SERVER_DISABLED;
            case "Unlicensed":
                return SERVER_UNLICENSED;
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

    public static void deleteServer(int serverId) {
        deactivateServer();
        applicationContext.getContentResolver().delete(McContract.ServerInfo.CONTENT_URI,
                McContract.ServerInfo._ID + "=?", new String[]{String.valueOf(serverId)});
    }
}
