package com.mdmobile.cyclops.networkRequests;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.dataModel.api.ServerInfo;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.DbData;
import com.mdmobile.cyclops.utils.ServerUtility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;

/**
 * Responsible of requesting and storing information about Servers
 */

public class ServerInfoRequest extends BasicRequest<String> {
    private final String serverSynced;
    private Response.Listener<String> responseListener;

    public ServerInfoRequest(String url, String serverName, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.responseListener = responseListener;
        serverSynced = serverName;
    }

    @Override
    protected void deliverResponse(String response) {
        responseListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Intent intent = new Intent(MainActivity.UPDATE_LOADING_BAR_ACTION);
        intent.setPackage(applicationContext.getPackageName());

        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            ServerInfo serverComponents = new Gson().fromJson(jsonResponseString, ServerInfo.class);

            ArrayList<ServerInfo.ManagementServer> managementServers = new ArrayList<>(serverComponents.getManagementServers());
            ArrayList<ServerInfo.DeploymentServer> deploymentServers = new ArrayList<>(serverComponents.getDeploymentServers());

            applicationContext.sendBroadcast(intent);

            //Update serverInfo table with version -> it could have changed since last sync
            Server serverInfo = ServerUtility.getActiveServer();
            Server newServerInfo = new Server(serverInfo.getServerName(), serverInfo.getApiSecret(), serverInfo.getClientId(),
                    serverInfo.getServerAddress(), serverComponents.getProductVersion(), serverComponents.getProductVersionBuild());

            applicationContext.getContentResolver().update(McContract.ServerInfo.buildServerInfoUriWithName(serverInfo.getServerName()),
                    newServerInfo.toContentValues(), null, null);
            newServerInfo.setActive();

            Cursor c = applicationContext.getContentResolver()
                    .query(McContract.ServerInfo.buildServerInfoUriWithName(serverInfo.getServerName()),
                            new String[]{McContract.ServerInfo._ID}, null, null, null);

            if (c == null || !c.moveToFirst()) {
                throw new UnsupportedOperationException("No server found in DB:" + serverInfo.getServerName());
            }

            String serverId = c.getString(0);
            c.close();

            //Delete any existing MS,DS in DB
            Uri uri = McContract.buildUriWithServerName(McContract.MsInfo.CONTENT_URI, serverInfo.getServerName());
            applicationContext.getContentResolver().delete(uri, null, null);
            uri = McContract.buildUriWithServerName(McContract.DsInfo.CONTENT_URI, serverInfo.getServerName());
            applicationContext.getContentResolver().delete(uri, null, null);

            //TODO: update serverInfo. version could have changed since last sync
            if (managementServers.size() > 1) {
                applicationContext.getContentResolver().bulkInsert(McContract.MsInfo.CONTENT_URI,
                        DbData.prepareMsValues(managementServers, serverId));
            } else {
                applicationContext.getContentResolver().insert(McContract.MsInfo.CONTENT_URI,
                        DbData.prepareMsValues(managementServers.get(0), serverId));
            }

            if (deploymentServers.size() > 1) {
                applicationContext.getContentResolver().bulkInsert(McContract.DsInfo.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers, serverId));
            } else {
                applicationContext.getContentResolver().insert(McContract.DsInfo.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers.get(0), serverId));
            }

            int status;

            for (ServerInfo.ManagementServer server : managementServers) {
                status = ServerUtility.serverStatus(server);
                if (status != ServerUtility.SERVER_STARTED) {
                    ServerUtility.notifyServerStatus(server.getName(), status);
                }
            }

            for (ServerInfo.DeploymentServer server : deploymentServers) {
                status = ServerUtility.serverStatus(server);
                if (status != ServerUtility.SERVER_STARTED) {
                    ServerUtility.notifyServerStatus(server.getName(), status);
                }
            }

            applicationContext.sendBroadcast(intent);

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}
