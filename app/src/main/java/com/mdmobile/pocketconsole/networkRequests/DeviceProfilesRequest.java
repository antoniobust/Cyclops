package com.mdmobile.pocketconsole.networkRequests;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.gson.InstalledApp;
import com.mdmobile.pocketconsole.gson.Profile;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle profiles installed info request
 */

public class DeviceProfilesRequest extends BasicRequest<String> {

    Context mContext;
    private Response.Listener<String> listener;

    public DeviceProfilesRequest(int method, String url, Response.ErrorListener errorListener, Context context) {
        super(method, url, errorListener, context);
        mContext = context.getApplicationContext();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

//        try {
//            String jsonResponseString = new String(response.data,
//                    HttpHeaderParser.parseCharset(response.headers));
//
//            Type type = new TypeToken<List<Profile>>() {
//            }.getType();
//
//            Gson gson = new Gson();
//            ArrayList<Profile> profiles = gson.fromJson(jsonResponseString, type);
//
//            //Parse devices to extract common properties and put other as extra string
//            if (profiles.size() == 1) {
//                ContentValues appValues = DbData.formatInstalledApp(profiles.get(0));
//                mContext.getContentResolver().insert(McContract.InstalledApplications.CONTENT_URI, appValues);
//            } else if (profiles.size() > 1) {
//                ContentValues[] appValues = DbData.bulkFormatInstalledApp(profiles);
//                mContext.getContentResolver().bulkInsert(McContract.InstalledApplications.CONTENT_URI, appValues);
//            }
//
//            return Response.success(null,
//                    HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return Response.error(new ParseError(e));
//        }

        return null;
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
