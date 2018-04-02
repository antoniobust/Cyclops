package com.mdmobile.cyclops.networkRequests;

import android.content.ContentValues;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclops.dataModels.api.InstalledApp;
import com.mdmobile.cyclops.provider.McContract;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;

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
                applicationContext.getContentResolver().insert(McContract.InstalledApplications.CONTENT_URI, applications.get(0).toContentValues());
            } else if (applications.size() > 1) {
                ArrayList<ContentValues> values = new ArrayList<>();
                for (InstalledApp app : applications) {
                    values.add(app.toContentValues());
                }
                ContentValues[] vals = new ContentValues[values.size()];
                values.toArray(vals);
                applicationContext.getContentResolver().bulkInsert(McContract.InstalledApplications.CONTENT_URI, vals);
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
