package com.mdmobile.pocketconsole.apiHandler;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mdmobile.pocketconsole.BuildConfig;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.apiHandler.api.ApiModels;
import com.mdmobile.pocketconsole.gson.Token;
import com.mdmobile.pocketconsole.interfaces.NetworkCallBack;
import com.mdmobile.pocketconsole.networkRequests.DeviceInstalledAppRequest;
import com.mdmobile.pocketconsole.networkRequests.DeviceRequest;
import com.mdmobile.pocketconsole.networkRequests.SimpleRequest;
import com.mdmobile.pocketconsole.utils.Logger;
import com.mdmobile.pocketconsole.utils.UsersUtility;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_ADDRESS_KEY;

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
        requestsQueue = Volley.newRequestQueue(context);
        mContext = context;
    }

    public static synchronized ApiRequestManager getInstance(Context context) {
        if (server == null) {
            //New Api server instance needs to be created
            //Getting the app context from the context provided will avoid memory leaks
            server = new ApiRequestManager(context.getApplicationContext());
            return server;
        } else {
            return server;
        }
    }

    /**
     * Get a new token for the provided user and server
     */
    public void getToken(String serverUrl, String clientID, String clientSecret,
                         String userName, String password, final NetworkCallBack callBack) {


        //if debug discard input and use debugging info
        if (BuildConfig.DEBUG) {
            serverUrl = mContext.getString(R.string.mc_server_url).concat("/MobiControl/api/token");
            clientID = mContext.getString(R.string.mc_clientID);
            clientSecret = mContext.getString(R.string.mc_client_secret);
            userName = mContext.getString(R.string.mc_user_name);
            password = mContext.getString(R.string.mc_password);
        } else {
            //If not debug take the url input from user and attach the token request
            serverUrl = serverUrl.concat("/MobiControl/api/token");
        }
        final String grantType = "grant_type=password&username=" + userName + "&password=" + password;
        final String header = clientID.concat(":").concat(clientSecret);


        SimpleRequest tokenRequest = new SimpleRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(LOG_TAG, "Token request received successfully");
                        if (BuildConfig.DEBUG) {
                            Log.v(LOG_TAG, "Token Response:" + response.replace(",", "\n"));
                        }

                        //Parse network response to get token details
                        Token token = new Gson().fromJson(response, Token.class);
                        callBack.tokenReceived(token);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.errorReceivingToken(error);
                Log.e(LOG_TAG, "Error receiving token");
                error.printStackTrace();
            }
        }, mContext) {
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

        if (!tokenRequest.isCanceled()) {
            requestsQueue.add(tokenRequest);
        }

    }

    public void getDevices(Account account) {

//        Account account = AccountManager.get(mContext).getAccountsByType(mContext.getString(R.string.account_type))[0];
        String apiAuthority = UsersUtility.getUserInfo(mContext, account).get(SERVER_ADDRESS_KEY);
        String api = ApiModels.DevicesApi.Builder(apiAuthority).build();

        DeviceRequest deviceRequest = new DeviceRequest<>(mContext, Request.Method.GET, api,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Logger.log(LOG_TAG, " done with request", Log.VERBOSE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error requesting devices", Log.ERROR);
            }
        }, DeviceRequest.ERASE_OLD_DEVICE_INFO);

        requestsQueue.add(deviceRequest);
    }

    public void getDeviceInstalledApps(@NonNull String devID) {
        Account account = AccountManager.get(mContext).getAccountsByType(mContext.getString(R.string.account_type))[0];
        String apiAuthority = UsersUtility.getUserInfo(mContext, account).get(SERVER_ADDRESS_KEY);
        String api = ApiModels.DevicesApi.Builder(apiAuthority, devID).getInstalledApplications().build();

        DeviceInstalledAppRequest installedAppRequest = new DeviceInstalledAppRequest(Request.Method.GET,
                api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, mContext);

        requestsQueue.add(installedAppRequest);
    }

    public void uninstallApplication(@NonNull String devID){

    }


}