package com.mdmobile.cyclops.ApiTest.TestClass;


import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Copy of ApiRequestManager, it has same methods but is not a singleton class.
 * Created for test purposes only
 * It uses blocking calls instead of async logic
 */

public class FakeApiManager {

    private Context mContext;

    public FakeApiManager(Context context) {
        mContext = context;
        VolleyLog.DEBUG = true;
    }


    public String getApiToken(final String userName, final String password) {

        String tokenUrl = "https://uk.mobicontrolcloud.com/MobiControl/api/token";
        String clientID = "e0edb1295a8e4754a3180bb9595b7f6a";
        String clientSecret = "MD7oqtUp3HPsJSXF36e2YhVKG9KTWnDe";
        final String header = clientID.concat(":").concat(clientSecret);
        String response;



        RequestQueue queue = Volley.newRequestQueue(mContext);
        RequestFuture<String> requestFuture = RequestFuture.newFuture();

        StringRequest tokenReq = new StringRequest(Request.Method.POST, tokenUrl, requestFuture, requestFuture) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic ".concat(Base64.encodeToString(header.getBytes(), Base64.NO_WRAP)));
                return headers;
            }

            @Override
            public byte[] getBody() {
                return ("grant_type=password&username="+userName+"&password="+password).getBytes();
            }
        };

        queue.add(tokenReq);

        try {
            response = requestFuture.get(20, TimeUnit.SECONDS);
            return response;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return "Error receiving token";
        }

    }
}