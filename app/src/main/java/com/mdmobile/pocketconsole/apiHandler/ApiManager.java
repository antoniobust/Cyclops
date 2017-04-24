package com.mdmobile.pocketconsole.apiHandler;

import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class for API requests.
 * Provides methods to manage users, queue/launch api requests etc.
 * It is a Singleton class so requests can be queue under the same queue throughout the app lifecycle
 */
public class ApiManager {

    private static ApiManager server;
    private RequestQueue requestsQueue;

    private ApiManager(Context context) {
        //New Api server instance created, instantiate a volley request queue
        //Getting the app context from the context provided will avoid memory leaks
        requestsQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ApiManager getInstance(Context context) {
        if (server == null) {
            server = new ApiManager(context);
            return server;
        } else {
            return server;
        }
    }

    public void getApiToken(final String userName, final String password) {

        String tokenUrl = "https://uk.mobicontrolcloud.com/api/token";
        String clientID = "189e788896d44300bc4eda2f0c6d6298";
        String clientSecret = "DfELQy11kDNBGrU1pG9TQ2lVpfzMVec7";
        final String header = clientID.concat(":").concat(clientSecret);


        StringRequest tokenRequest = new StringRequest(Request.Method.POST, tokenUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic ".concat(Base64.encodeToString(header.getBytes(), Base64.NO_WRAP)));
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return ("grant_type=password&username="+userName+"&password="+password).getBytes();            }
        };

        requestsQueue.add(tokenRequest);

    }
}