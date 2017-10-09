package com.mdmobile.pocketconsole.networkRequests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.gson.User;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

public class UserRequest extends BasicRequest<String> {

    public UserRequest(String url, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Type deviceCollectionType = new TypeToken<ArrayList<User>>() {
            }.getType();

            ArrayList<User> user = new Gson().fromJson(jsonResponseString, deviceCollectionType);
            applicationContext.getContentResolver().delete(McContract.UserInfo.CONTENT_URI,null,null);
            applicationContext.getContentResolver().bulkInsert(McContract.UserInfo.CONTENT_URI,
                    DbData.prepareUserValues(user));

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {

    }
}
