package com.mdmobile.pocketconsole.networkRequests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mdmobile.pocketconsole.gson.ServerInfo;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

/**
 * Responsible of requesting and storing information about Servers
 */

public class ServerInfoRequest extends BasicRequest<String> {
    private Response.Listener<String> responseListener;

    public ServerInfoRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.responseListener = responseListener;
    }

    @Override
    protected void deliverResponse(String response) {
        responseListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            ServerInfo servers = new Gson().fromJson(jsonResponseString, ServerInfo.class);

            ArrayList<ServerInfo.ManagementServer> managementServers = new ArrayList<>(servers.getManagementServers());
            ArrayList<ServerInfo.DeploymentServer> deploymentServers = new ArrayList<>(servers.getDeploymentServers());

            //Delete any existing data in DB
            applicationContext.getContentResolver().delete(McContract.MsInfo.CONTENT_URI, null, null);
            applicationContext.getContentResolver().delete(McContract.DsInfo.CONTENT_URI, null, null);

            if (managementServers.size() > 1) {
                applicationContext.getContentResolver().bulkInsert(McContract.MsInfo.CONTENT_URI,
                        DbData.prepareMsValues(managementServers));
            } else {
                applicationContext.getContentResolver().insert(McContract.MsInfo.CONTENT_URI,
                        DbData.prepareMsValues(managementServers.get(0)));
            }

            if (deploymentServers.size() > 1) {
                applicationContext.getContentResolver().bulkInsert(McContract.DsInfo.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers));
            } else {
                applicationContext.getContentResolver().insert(McContract.DsInfo.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers.get(0)));
            }

            for (int i = 0; i < deploymentServers.size(); i++) {
                deploymentServers.get(i).getConnected();
            }


            Boolean flag;
            for (ServerInfo.ManagementServer server : managementServers) {
                switch (isServerOnline(server)) {
                    case 10:
                    case 0:
                    case 1:
                }
            }

            for (ServerInfo.DeploymentServer server : deploymentServers) {
                switch (isServerOnline(server)) {
                    case -1:
                    case 0:
                    case 1:
                }
            }

//            notifyUser(deploymentServers.get(0).getName());

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    private int isServerOnline(ServerInfo.DeploymentServer server) {
        //return -1 for unknown status, 0 for offline, 1 online, 2 for running but disconnected
        if (server.getStatus().equals("Stopped") || server.getStatus().equals("Offline")) {
            return 0;
        }
        if (server.getStatus().equals("Started") && server.getConnected()) {
            return 1;
        } else if (server.getStatus().equals("Started") && !server.getConnected()) {
            return 2;
        }
        return -1;
    }

    private int isServerOnline(ServerInfo.ManagementServer server) {
        // 0 for offline, 1 online, 2 for running but disconnected
        if (server.getStatus().equals("Stopped") || server.getStatus().equals("Offline")) {
            return 0;
        }
        if (server.getStatus().equals("Started")) {
            return 1;
        }
        return -1;

    }

//    private void checkServerConnectivity(ArrayList<ServerInfo.ManagementServer> msList, ArrayList<ServerInfo.DeploymentServer> dsList) {
//
//        for (ServerInfo.ManagementServer server : msList) {
//
//            switch (server.getStatus()) {
//                case "Stopped":
//                    notifyUser(server.getName(), 0);
//                case "Disabled":
//                    notifyUser(server.getName(), 3);
//                case "Unlicensed":
//                    notifyUser(server.getName(), 2);
//                case "Deleted":
//                    notifyUser(server.getName(), 5);
//                case "StartedButNotRegistered":
//                    notifyUser(server.getName(), 4);
//                case "Offline":
//                    notifyUser(server.getName(), 1);
//                case "Unknown":
//                    notifyUser(server.getName(), 6);
//            }
//
//        }
//
//    }
//
//    private void notifyUser(String serverName, int status) {
//        String message = (applicationContext.getResources().getStringArray(R.array.server_notification_labels))[status];
//
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(applicationContext)
//                .setSmallIcon(R.drawable.ic_block)
//                .setContentTitle("Alert: ServerInfo not reachable")
//                .setContentText(message);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationBuilder.setChannelId(applicationContext.getPackageName());
//        } else {
//            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
//        }
//
//        NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notificationBuilder.build());
//
//    }

}
