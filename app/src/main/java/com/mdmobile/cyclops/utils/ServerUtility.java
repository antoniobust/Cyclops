package com.mdmobile.cyclops.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.dataModel.Instance;
import com.mdmobile.cyclops.dataModel.api.ServerInfo;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.security.ServerNotFound;

import java.util.ArrayList;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;
import static com.mdmobile.cyclops.CyclopsApplication.Companion;
import static com.mdmobile.cyclops.CyclopsApplication.applicationContext;

/**
 * Utility class for server info methods
 */

public class ServerUtility {
    public static final String SERVER_ADDRESS_KEY = "ServerAddressKey";
    private final static String LOG_TAG = ServerUtility.class.getSimpleName();
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
        SharedPreferences preferences = Companion.getApplicationContext()
                .getSharedPreferences(Companion.getApplicationContext().getString(R.string.server_shared_preference), MODE_MULTI_PROCESS);
        return preferences.contains(Companion.getApplicationContext().getString(R.string.server_name_preference));
    }

    public static Instance getActiveServer() throws ServerNotFound {
        SharedPreferences preferences = Companion.getApplicationContext().getSharedPreferences(Companion.getApplicationContext().getString(R.string.server_shared_preference), MODE_MULTI_PROCESS);
        String serverName = preferences.getString(Companion.getApplicationContext().getString(R.string.server_name_preference), null);
        String apiSecret = preferences.getString(Companion.getApplicationContext().getString(R.string.api_secret_preference), null);
        String clientId = preferences.getString(Companion.getApplicationContext().getString(R.string.client_id_preference), null);
        String address = preferences.getString(Companion.getApplicationContext().getString(R.string.server_address_preference), null);
        int version = preferences.getInt(Companion.getApplicationContext().getString(R.string.server_version_preference), -1);
        int build = preferences.getInt(Companion.getApplicationContext().getString(R.string.server_build_preference), -1);

        if (serverName != null && apiSecret != null && clientId != null && address != null) {
            return new Instance(serverName, apiSecret, clientId, address, version, build);
        } else {
            throw  new ServerNotFound("Active server not found");
        }
    }

    public static void setActiveServer(String serverName) {
        setActiveServer(getServer(serverName));
    }

    public static void setActiveServer(Instance instance) {
        SharedPreferences.Editor editor = Companion.getApplicationContext()
                .getSharedPreferences(Companion.getApplicationContext().getString(R.string.server_shared_preference), MODE_PRIVATE).edit();
        editor.putString(Companion.getApplicationContext().getString(R.string.server_name_preference), instance.getServerName());
        editor.putString(Companion.getApplicationContext().getString(R.string.api_secret_preference), instance.getApiSecret());
        editor.putString(Companion.getApplicationContext().getString(R.string.client_id_preference), instance.getClientId());
        editor.putString(Companion.getApplicationContext().getString(R.string.server_address_preference), instance.getServerAddress());
        editor.putInt(Companion.getApplicationContext().getString(R.string.server_version_preference), instance.getServerMajorVersion());
        editor.putInt(Companion.getApplicationContext().getString(R.string.server_build_preference), instance.getBuildNumber());
        editor.apply();

        Logger.log(LOG_TAG, instance.getServerName() + " set as active", Log.VERBOSE);

    }

    public static void deactivateServer() {
        SharedPreferences.Editor editor =
                Companion.getApplicationContext().getSharedPreferences(Companion.getApplicationContext().getString(R.string.server_shared_preference), MODE_MULTI_PROCESS).edit();
        editor.remove(Companion.getApplicationContext().getString(R.string.server_name_preference));
        editor.remove(Companion.getApplicationContext().getString(R.string.api_secret_preference));
        editor.remove(Companion.getApplicationContext().getString(R.string.client_id_preference));
        editor.remove(Companion.getApplicationContext().getString(R.string.server_address_preference));
        editor.apply();
        Logger.log(LOG_TAG, " active server deactivated", Log.VERBOSE);
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
        String message = Companion.getApplicationContext().getResources().getStringArray(R.array.server_notification_labels)[status];
        message = String.format(message, serverName);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Companion.getApplicationContext(), Companion.getApplicationContext().getPackageName())
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_block)
                .setContentTitle(Companion.getApplicationContext().getString(R.string.notification_server_status_title))
                .setContentText(message);

        NotificationManager notificationManager = (NotificationManager) Companion.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());

    }

    public static void deleteServer(int serverId) {
        Companion.getApplicationContext().getContentResolver().delete(McContract.ServerInfo.CONTENT_URI,
                McContract.ServerInfo._ID + "=?", new String[]{String.valueOf(serverId)});
    }

    public static Instance getServer(String serverName) {
        String[] projection = {McContract.ServerInfo.NAME, McContract.ServerInfo.SERVER_ADDRESS, McContract.ServerInfo.SERVER_MAJOR_VERSION,
                McContract.ServerInfo.SERVER_BUILD_NUMBER, McContract.ServerInfo.CLIENT_ID, McContract.ServerInfo.CLIENT_SECRET};
        Cursor c = Companion.getApplicationContext().getContentResolver()
                .query(McContract.ServerInfo.buildServerInfoUriWithName(serverName), projection, null, null, null);
        if (c == null || !c.moveToFirst()) {
            throw new UnsupportedOperationException("No server found");
        }
        Instance s = new Instance(c.getString(0), c.getString(5), c.getString(4), c.getString(1), c.getInt(2), c.getInt(3));
        c.close();
        return s;
    }

    public static Instance[] getAllInstances() {
        String[] projection = {McContract.ServerInfo.NAME, McContract.ServerInfo.SERVER_ADDRESS, McContract.ServerInfo.SERVER_MAJOR_VERSION,
                McContract.ServerInfo.SERVER_BUILD_NUMBER, McContract.ServerInfo.CLIENT_ID, McContract.ServerInfo.CLIENT_SECRET};
        Cursor c = Companion.getApplicationContext().getContentResolver()
                .query(McContract.ServerInfo.CONTENT_URI, projection, null, null, null);
        if (c == null || !c.moveToFirst()) {
            throw new UnsupportedOperationException("No server found");
        }
        ArrayList<Instance> instanceList = new ArrayList<>(c.getCount());
        do {
            instanceList.add(new Instance(c.getString(0), c.getString(5), c.getString(4), c.getString(1), c.getInt(2), c.getInt(3)));
            c.moveToNext();
        } while (!c.isAfterLast());
        c.close();
        return instanceList.toArray(new Instance[]{});
    }
}
