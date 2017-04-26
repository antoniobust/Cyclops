package com.mdmobile.pocketconsole.apiHandler;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mdmobile.pocketconsole.BuildConfig;
import com.mdmobile.pocketconsole.NetworkCallBack;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.gson.Token;
import com.mdmobile.pocketconsole.ui.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class for API requests.
 * Provides methods to manage users, queue/launch api requests etc.
 * It is a Singleton class so requests can be queue under the same queue throughout the app lifecycle
 */
public class ApiRequestManager {

    private final static String LOG_TAG = ApiRequestManager.class.getSimpleName();
    private static ApiRequestManager server;
    private RequestQueue requestsQueue;
    private Context mContext;

    private ApiRequestManager(Context context) {
        //New Api server instance created, instantiate a volley request queue
        //Getting the app context from the context provided will avoid memory leaks
        requestsQueue = Volley.newRequestQueue(context.getApplicationContext());
        mContext = context;
    }

    public static synchronized ApiRequestManager getInstance(Context context) {
        if (server == null) {
            server = new ApiRequestManager(context);
            return server;
        } else {
            return server;
        }
    }

    public void getApiToken(String tokenUrl, String clientID, String clientSecret,
                            String userName, String password, final NetworkCallBack callBack) {


        //if debug discard input and use debugging info
        if (BuildConfig.DEBUG) {
            tokenUrl = "https://uk.mobicontrolcloud.com/MobiControl/api/token";
            clientID = "e0edb1295a8e4754a3180bb9595b7f6a";
            clientSecret = "MD7oqtUp3HPsJSXF36e2YhVKG9KTWnDe";
            userName = mContext.getString(R.string.mc_user_name);
            password = mContext.getString(R.string.mc_password);

        }
        final String grantType = "grant_type=password&username=" + userName + "&password=" + password;
        final String header = clientID.concat(":").concat(clientSecret);


        StringRequest tokenRequest = new StringRequest(Request.Method.POST, tokenUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(LOG_TAG, "Token request received successfully");
                        if (BuildConfig.DEBUG) {
                            Log.v(LOG_TAG, "Token Response:" + response.replace(",","\n"));
                        }

                        //Parse network response to get token details
                        Token token = new Gson().fromJson(response,Token.class);
                        //Return data to LogIn activity
                        callBack.tokenReceived(token);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.errorReceivingToken(error.toString());
                Log.e(LOG_TAG, "Error receiving token");
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic ".concat(Base64.encodeToString(header.getBytes(), Base64.NO_WRAP)));
                return headers;
            }

            @Override
            public byte[] getBody() {
                return grantType.getBytes();
            }
        };

        requestsQueue.add(tokenRequest);

    }
}