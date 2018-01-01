package com.mdmobile.pocketconsole.networkRequests;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.dataModels.api.Profile;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;
import com.mdmobile.pocketconsole.utils.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

/**
 * Class to handle profiles installed info request
 */

public class ProfilesRequest extends BasicRequest<String> {

    private static final String LOG_TAG =ProfilesRequest.class.getSimpleName() ;
    Context mContext;
    private Response.Listener<String> responseListener;
    private final String devId;

    public ProfilesRequest(int method, String url, String devId, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.responseListener = responseListener;
        this.devId = devId;
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
            Logger.log(LOG_TAG,  profiles.size() + " profiles received for: " +devId, Log.VERBOSE);
            //Delete old data
            applicationContext.getContentResolver().delete(McContract.Profile.buildUriWithDeviceId(devId),null,null);
            //Parse Profiles and save in DB
            if (profiles.size() == 1) {
                ContentValues values = DbData.prepareProfilesValue(profiles.get(0));
                applicationContext.getContentResolver().insert(McContract.Profile.buildUriWithDeviceId(devId), values);
            } else if (profiles.size() > 1) {
                ContentValues[] appValues = DbData.prepareProfilesValue(profiles);
                applicationContext.getContentResolver().bulkInsert(McContract.Profile.buildUriWithDeviceId(devId), appValues);
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
