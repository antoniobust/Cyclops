package com.mdmobile.pocketconsole.networkRequests;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.gson.InstalledApp;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Volley request to get installed application on a single device
 */

public class DeviceInstalledAppRequest extends BasicRequest<String> {

    private Response.Listener<String> listener;
    private Context mContext;

    public DeviceInstalledAppRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Context context) {
        super(method, url, errorListener, context);
        mContext = context;
        this.listener = listener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {

            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Type type = new TypeToken<List<InstalledApp>>() {
            }.getType();

            Gson gson = new Gson();
            ArrayList<InstalledApp> applications = gson.fromJson(jsonResponseString, type);

            //Parse devices to extract common properties and put other as extra string
            if (applications.size() == 1) {
                //TODO:insert in DB

            } else if (applications.size() > 1) {
                //TODO: bulk Insert in DB
            }

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

}
