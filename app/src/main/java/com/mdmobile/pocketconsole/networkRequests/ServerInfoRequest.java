package com.mdmobile.pocketconsole.networkRequests;

import android.content.Context;

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

/**
 * Responsible of requesting and storing information about Servers
 */

public class ServerInfoRequest extends BasicRequest<String> {
    private Response.Listener<String> responseListener;
    private Context mContext;

    public ServerInfoRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener, Context context) {
        super(Method.GET, url, errorListener, context);
        mContext = context;
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
            mContext.getContentResolver().delete(McContract.ManagementServer.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(McContract.DeploymentServer.CONTENT_URI, null, null);

            if (managementServers.size() > 1) {
                mContext.getContentResolver().bulkInsert(McContract.ManagementServer.CONTENT_URI,
                        DbData.prepareMsValues(managementServers));
            } else {
                mContext.getContentResolver().insert(McContract.ManagementServer.CONTENT_URI,
                        DbData.prepareMsValues(managementServers.get(0)));
            }

            if (deploymentServers.size() > 1) {
                mContext.getContentResolver().bulkInsert(McContract.DeploymentServer.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers));
            } else {
                mContext.getContentResolver().insert(McContract.DeploymentServer.CONTENT_URI,
                        DbData.prepareDsValues(deploymentServers.get(0)));
            }

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

}
