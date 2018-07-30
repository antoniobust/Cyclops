package com.mdmobile.cyclops.networkRequests;

import android.content.ContentValues;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclops.dataModel.api.Profile;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.utils.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;

/**
 * Class to handle profiles installed info request
 */

public class ProfilesRequest extends BasicRequest<String> {

    private static final String LOG_TAG = ProfilesRequest.class.getSimpleName();
    private final String devId;
    private Response.Listener<String> responseListener;

    public ProfilesRequest(int method, String url, String devId, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.responseListener = responseListener;
        this.devId = devId;
    }

    public ProfilesRequest(ProfilesRequest request){
        super(request.getMethod(),request.getUrl(),request.getErrorListener());
        this.devId = request.devId;
        this.responseListener = request.responseListener;
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
            Logger.log(LOG_TAG, profiles.size() + " profiles received for: " + devId, Log.VERBOSE);

            if (profiles.size() == 0) {
                profiles.add(new Profile("N/A", "N/A", "N/A", "N/A", 0, false));
            }
            //Parse Profiles and save in DB
            if (profiles.size() == 1) {
                applicationContext.getContentResolver().insert(McContract.Profile.buildUriWithDeviceId(devId), profiles.get(0).toContentValues());
            } else if (profiles.size() > 1) {
                ArrayList<ContentValues> values = new ArrayList<>();
                for (Profile p : profiles) {
                    values.add(p.toContentValues());
                }
                ContentValues[] vals = new ContentValues[values.size()];
                values.toArray(vals);
                applicationContext.getContentResolver().bulkInsert(McContract.Profile.buildUriWithDeviceId(devId), vals);
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
