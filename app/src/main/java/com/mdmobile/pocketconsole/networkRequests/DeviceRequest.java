package com.mdmobile.pocketconsole.networkRequests;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.gson.Device;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Request devices
 * Pass API request in the constructor
 */

public class DeviceRequest<T> extends BasicRequest<T> {

    Response.Listener<T> listener;
    Context mContext;

    public DeviceRequest(Context context, int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener, context);

        this.mContext = context;
        this.listener = listener;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {


            String jsonResponseString =  new String (response.data,
                    HttpHeaderParser.parseCharset(response.headers));


            Gson gson = new Gson();
            Type deviceCollectionType = new TypeToken<Collection<Device>>() {
            }.getType();
            Collection<Device> devices = gson.fromJson(jsonResponseString, deviceCollectionType);
            Device[] devicesArray = new Device[devices.size()];
            devices.toArray(devicesArray);

            if (devicesArray.length == 1) {
                ContentValues device = DbData.getDeviceContentValues(devicesArray[0]);
                mContext.getContentResolver().insert(McContract.Device.CONTENT_URI, device);
            } else if (devices.size() > 1) {
                ContentValues[] devicesValues = DbData.getListOfDeviceContentValues(devicesArray);
                mContext.getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
            }

            return Response.success(null ,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
