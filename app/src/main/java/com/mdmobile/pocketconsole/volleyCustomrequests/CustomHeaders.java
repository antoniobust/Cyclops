package com.mdmobile.pocketconsole.volleyCustomrequests;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

/**
 * This class override Headers in volley requests to set the required headers
 * as api token
 */

public abstract class CustomHeaders extends Request {

    public CustomHeaders(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    //TODO: get api token from user data
    @Override
    public Map<String, String> getHeaders() {
        return null;
    }
}
