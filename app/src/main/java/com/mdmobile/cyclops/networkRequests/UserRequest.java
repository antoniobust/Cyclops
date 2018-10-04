package com.mdmobile.cyclops.networkRequests;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclops.dataModel.Instance;
import com.mdmobile.cyclops.dataModel.api.User;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.security.ServerNotFound;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.DbData;
import com.mdmobile.cyclops.utils.ServerUtility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;

public class UserRequest extends BasicRequest<String> {

    private Response.Listener<String> responseListener;

    public UserRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.responseListener = responseListener;
    }
    public UserRequest(UserRequest request){
        super(request.getMethod(),request.getUrl(),request.getErrorListener());
        this.responseListener = request.responseListener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Intent intent = new Intent(MainActivity.UPDATE_LOADING_BAR_ACTION);
        intent.setPackage(applicationContext.getPackageName());

        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Type deviceCollectionType = new TypeToken<ArrayList<User>>() {
            }.getType();
            ArrayList<User> user = new Gson().fromJson(jsonResponseString, deviceCollectionType);

            applicationContext.sendBroadcast(intent);

            Instance instance = ServerUtility.getActiveServer();
            Uri uri = McContract.buildUriWithServerName(McContract.UserInfo.CONTENT_URI, instance.getServerName());
            Cursor c = applicationContext.getContentResolver().query(McContract.ServerInfo.buildServerInfoUriWithName(instance.getServerName()),
                    new String[]{McContract.ServerInfo._ID}, null, null, null);

            if (c == null || !c.moveToFirst()) {
                throw new UnsupportedOperationException("No server found in DB: " + instance.getServerName());
            }
            String serverId = c.getString(0);
            c.close();

            applicationContext.getContentResolver().delete(uri, null, null);
            applicationContext.getContentResolver().bulkInsert(McContract.UserInfo.CONTENT_URI,
                    DbData.prepareUserValues(user, serverId));

            applicationContext.sendBroadcast(intent);

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException|ServerNotFound e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        responseListener.onResponse(response);
    }
}
