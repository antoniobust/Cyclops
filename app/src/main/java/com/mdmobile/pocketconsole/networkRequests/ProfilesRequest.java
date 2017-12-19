package com.mdmobile.pocketconsole.networkRequests;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.dataModels.api.Profile;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle profiles installed info request
 */

public class ProfilesRequest extends BasicRequest<String> {

    Context mContext;
    private Response.Listener<String> responseListener;

    public ProfilesRequest(int method, String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.responseListener = responseListener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Type type = new TypeToken<List<Profile>>() {
            }.getType();

            Gson gson = new Gson();
            ArrayList<Profile> profiles = gson.fromJson(jsonResponseString, type);

            //Parse Profiles and save in DB
            if (profiles.size() == 1) {
                ContentValues values = DbData.prepareProfilesValue(profiles.get(0));
                mContext.getContentResolver().insert(McContract.Profile.CONTENT_URI, values);
            } else if (profiles.size() > 1) {
                ContentValues[] appValues = DbData.prepareProfilesValue(profiles);
                mContext.getContentResolver().bulkInsert(McContract.Profile.CONTENT_URI, appValues);
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
        responseListener.onResponse(response);
    }
}
