package com.mdmobile.pocketconsole.networkRequests;

import android.content.ContentValues;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.dataModels.api.InstalledApp;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

/**
 * Volley request to get installed application on a single device
 */

public class DeviceInstalledAppRequest extends BasicRequest<String> {

    private Response.Listener<String> listener;

    public DeviceInstalledAppRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {

            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Type type = new TypeToken<List<InstalledApp>>() {
            }.getType();

            Gson gson = new Gson();
            ArrayList<InstalledApp> applications = gson.fromJson(jsonResponseString, type);

            //Parse devices to extract common properties and put other as extra string
            if (applications.size() == 1) {
                ContentValues appValues = DbData.prepareInstalledAppValues(applications.get(0));
                applicationContext.getContentResolver().insert(McContract.InstalledApplications.CONTENT_URI, appValues);
            } else if (applications.size() > 1) {
                ContentValues[] appValues = DbData.prepareInstalledAppValues(applications);
                applicationContext.getContentResolver().bulkInsert(McContract.InstalledApplications.CONTENT_URI, appValues);
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
