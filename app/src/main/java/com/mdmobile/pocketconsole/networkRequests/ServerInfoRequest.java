package com.mdmobile.pocketconsole.networkRequests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mdmobile.pocketconsole.dataModels.api.ServerInfo;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;
import com.mdmobile.pocketconsole.utils.ServerUtility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

/**
 * Responsible of requesting and storing information about Servers
 */

public class ServerInfoRequest extends BasicRequest<String> {
    private Response.Listener<String> responseListener;
    private final String serverSynced;

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
        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            ServerInfo servers = new Gson().fromJson(jsonResponseString, ServerInfo.class);

            ArrayList<ServerInfo.ManagementServer> managementServers = new ArrayList<>(servers.getManagementServers());
            ArrayList<ServerInfo.DeploymentServer> deploymentServers = new ArrayList<>(servers.getDeploymentServers());

            //Update server info -> Version could have changed since last sync
            applicationContext.getContentResolver().update(
                    McContract.ServerInfo.buildServerInfoUriWithName(serverSynced),
                    DbData.prepareServerInfoValues(servers), null, null);

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


            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}
