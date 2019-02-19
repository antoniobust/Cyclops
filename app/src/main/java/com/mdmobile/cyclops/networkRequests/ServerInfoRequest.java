package com.mdmobile.cyclops.networkRequests;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mdmobile.cyclops.dataModel.Instance;
import com.mdmobile.cyclops.dataModel.api.ServerInfo;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.security.ServerNotFound;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.util.DbData;
import com.mdmobile.cyclops.util.ServerUtility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.mdmobile.cyclops.CyclopsApplication.Companion;

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
    public ServerInfoRequest(ServerInfoRequest request){
        super(request.getMethod(),request.getUrl(),request.getErrorListener());
        this.responseListener = request.responseListener;
        this.serverSynced = request.serverSynced;
    }

    @Override
    protected void deliverResponse(String response) {
        responseListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Intent intent = new Intent(MainActivity.UPDATE_LOADING_BAR_ACTION);
        intent.setPackage(Companion.getApplicationContext().getPackageName());

        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            ServerInfo serverComponents = new Gson().fromJson(jsonResponseString, ServerInfo.class);

            ArrayList<ServerInfo.ManagementServer> managementServers = new ArrayList<>(serverComponents.getManagementServers());
            ArrayList<ServerInfo.DeploymentServer> deploymentServers = new ArrayList<>(serverComponents.getDeploymentServers());

            Companion.getApplicationContext().sendBroadcast(intent);

            //Update serverInfo table with version -> it could have changed since last sync
            Instance instanceInfo = ServerUtility.getActiveServer();
            Instance newInstanceInfo = new Instance(instanceInfo.getServerName(), instanceInfo.getApiSecret(), instanceInfo.getClientId(),
                    instanceInfo.getServerAddress(), serverComponents.getProductVersion(), serverComponents.getProductVersionBuild());

            Companion.getApplicationContext().getContentResolver().update(McContract.ServerInfo.buildServerInfoUriWithName(instanceInfo.getServerName()),
                    newInstanceInfo.toContentValues(), null, null);
            newInstanceInfo.setActive();

            Cursor c = Companion.getApplicationContext().getContentResolver()
                    .query(McContract.ServerInfo.buildServerInfoUriWithName(instanceInfo.getServerName()),
                            new String[]{McContract.ServerInfo._ID}, null, null, null);

            if (c == null || !c.moveToFirst()) {
                throw new UnsupportedOperationException("No server found in DB:" + instanceInfo.getServerName());
            }

            String serverId = c.getString(0);
            c.close();

            //Delete any existing MS,DS in DB
            Uri uri = McContract.buildUriWithServerName(McContract.MsInfo.CONTENT_URI, instanceInfo.getServerName());
            Companion.getApplicationContext().getContentResolver().delete(uri, null, null);
            uri = McContract.buildUriWithServerName(McContract.DsInfo.CONTENT_URI, instanceInfo.getServerName());
            Companion.getApplicationContext().getContentResolver().delete(uri, null, null);

            if (managementServers.size() > 1) {
                Companion.getApplicationContext().getContentResolver().bulkInsert(McContract.MsInfo.CONTENT_URI,
                        DbData.prepareMsValues(managementServers, serverId));
            } else {
                Companion.getApplicationContext().getContentResolver().insert(McContract.MsInfo.CONTENT_URI,
                        DbData.prepareMsValues(managementServers.get(0), serverId));
            }

            if (deploymentServers.size() > 1) {
                Companion.getApplicationContext().getContentResolver().bulkInsert(McContract.DsInfo.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers, serverId));
            } else {
                Companion.getApplicationContext().getContentResolver().insert(McContract.DsInfo.CONTENT_URI,
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

            Companion.getApplicationContext().sendBroadcast(intent);

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | ServerNotFound e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}
