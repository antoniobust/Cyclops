package com.mdmobile.cyclops.networkRequests;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Volley request that send action down to a device
 */

public class ActionRequest extends BasicRequest<String> {

    private Response.Listener<String> responseListener;
    private String actionPayload;

    public ActionRequest(String url, @NonNull String actionPayload, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        responseListener = listener;
        this.actionPayload = actionPayload;
    }

    public ActionRequest(ActionRequest request) {
        super(request.getMethod(), request.getUrl(), request.getErrorListener());
        this.responseListener = request.responseListener;
        this.actionPayload = request.actionPayload;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success(null,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        responseListener.onResponse(response);
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return actionPayload.getBytes();
    }
}
