package com.mdmobile.cyclops.apiManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mdmobile.cyclops.BuildConfig;
import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.apiManager.api.ApiModel;
import com.mdmobile.cyclops.dataModels.api.Action;
import com.mdmobile.cyclops.dataModels.api.Token;
import com.mdmobile.cyclops.dataTypes.ApiActions;
import com.mdmobile.cyclops.interfaces.NetworkCallBack;
import com.mdmobile.cyclops.networkRequests.ActionRequest;
import com.mdmobile.cyclops.networkRequests.DeviceInstalledAppRequest;
import com.mdmobile.cyclops.networkRequests.DeviceRequest;
import com.mdmobile.cyclops.networkRequests.ProfilesRequest;
import com.mdmobile.cyclops.networkRequests.ServerInfoRequest;
import com.mdmobile.cyclops.networkRequests.SimpleRequest;
import com.mdmobile.cyclops.networkRequests.UserRequest;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.GeneralUtility;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;
import com.mdmobile.cyclops.utils.UserUtility;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;
import static com.mdmobile.cyclops.utils.ServerUtility.SERVER_ADDRESS_KEY;

/**
 * Main class for API requests.
 * Provides methods to manage users, queue/launch api requests etc.
 * It is a Singleton class so requests can be queue under the same queue throughout the app lifecycle
 */
public class ApiRequestManager {

    private final static String LOG_TAG = ApiRequestManager.class.getSimpleName();
    private static ApiRequestManager server;
    private RequestQueue requestsQueue;

    private ApiRequestManager() {
        requestsQueue = Volley.newRequestQueue(applicationContext);
    }

    public static synchronized ApiRequestManager getInstance() {
        if (server == null) {
            server = new ApiRequestManager();
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

        serverUrl = serverUrl.concat("/MobiControl/api/token");
        final String grantType = "grant_type=password&username=" + userName + "&password=" + password;
        final String header = clientID.concat(":").concat(clientSecret);
        final Bundle userInput = new Bundle();
        userInput.putString(UserUtility.USER_NAME_KEY, userName);
        userInput.putString(UserUtility.PASSWORD_KEY, password);


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
                        callBack.tokenReceived(userInput, token);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.errorReceivingToken(error);
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

        if (!tokenRequest.isCanceled()) {
            requestsQueue.add(tokenRequest);
        }

    }

    public void getDeviceInfo(@NonNull final String devId) {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.DevicesApi.SelectDevice.Builder(apiAuthority, devId).build();

        DeviceRequest deviceRequest = new DeviceRequest<>(applicationContext, Request.Method.GET, api,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Logger.log(LOG_TAG, "Device:" + devId + " synced...", Log.VERBOSE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error updating " + devId, Log.ERROR);
            }
        }, DeviceRequest.UPDATE_EXISTING_DEVICE_INFO);

        requestsQueue.add(deviceRequest);
    }

    public void getDeviceInfo() {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.DevicesApi.Builder(apiAuthority).build();

        DeviceRequest deviceRequest = new DeviceRequest<>(applicationContext, Request.Method.GET, api,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Logger.log(LOG_TAG, "Devices synced...", Log.VERBOSE);
                        GeneralUtility.setSharedPreference(
                                applicationContext,
                                applicationContext.getString(R.string.last_dev_sync_pref),
                                Calendar.getInstance().getTimeInMillis());

                        Intent intent = new Intent(MainActivity.DEV_SYNC_BROADCAST_ACTION);
                        intent.setPackage(applicationContext.getPackageName());
                        applicationContext.sendBroadcast(intent);

//                        if (LocalBroadcastManager.getInstance(ApplicationLoader.applicationContext).sendBroadcast(intent)) {
//                            Logger.log(LOG_TAG, "Broadcast sync dev intent sent", Log.VERBOSE);
//                        } else{
//                            Logger.log(LOG_TAG, "Broadcast sync dev intent NOT sent", Log.VERBOSE);
//                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error syncing devices", Log.ERROR);
            }
        }, DeviceRequest.ERASE_OLD_DEVICE_INFO);

        requestsQueue.add(deviceRequest);
    }

    public void getDeviceProfiles(@NonNull final String deviceID) {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.DevicesApi.Builder(apiAuthority, deviceID).getProfiles().build();

        ProfilesRequest request = new ProfilesRequest(Request.Method.GET, api, deviceID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.log(LOG_TAG, "Device (" + deviceID + ") profiles received", Log.VERBOSE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error requesting device's profiles (" + deviceID + ") ", Log.ERROR);
            }
        });

        requestsQueue.add(request);
    }

    public void getDeviceInstalledApps(@NonNull final String devID) {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.DevicesApi.Builder(apiAuthority, devID).getInstalledApplications().build();

        DeviceInstalledAppRequest installedAppRequest = new DeviceInstalledAppRequest(Request.Method.GET,
                api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.log(LOG_TAG, "Device (" + devID + ") apps received", Log.VERBOSE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error requesting installed Apps for:" + devID, Log.ERROR);
            }
        });

        requestsQueue.add(installedAppRequest);
    }

    public void uninstallApplication(@NonNull String devID, @NonNull String packageName) {
        String script = "uninstall \"".concat(packageName).concat("\"");
        requestAction(devID, ApiActions.SEND_SCRIPT, script, null);
    }

    public void requestAction(@NonNull final String deviceID, @NonNull @ApiActions final String action,
                              @Nullable final String message, @Nullable String phoneNumber) {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.DevicesApi.Builder(apiAuthority, deviceID).actionRequest().build();

        String jsonPayload = new Gson().toJson(new Action(action, message, phoneNumber));

        ActionRequest actionRequest = new ActionRequest(api, jsonPayload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.log(LOG_TAG, "Request( " + action + ") successfully sent to: " + deviceID, Log.VERBOSE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error sending action:" + action + " to device= " + deviceID, Log.ERROR);
            }
        });


        requestsQueue.add(actionRequest);
        Logger.log(LOG_TAG, "Request( " + action + " -> " + message + ") requested to device: " + deviceID, Log.VERBOSE);
        Toast.makeText(applicationContext, action + " request sent", Toast.LENGTH_SHORT).show();
    }

    public void getServerInfo() {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.ServerApi.Builder(apiAuthority).getServerInfo().build();

        ServerInfoRequest request = new ServerInfoRequest(api,
                ServerUtility.getServer().getString(McContract.ServerInfo.NAME),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.log(LOG_TAG, "Server info synced...", Log.VERBOSE);
                        GeneralUtility.setSharedPreference(
                                applicationContext,
                                applicationContext.getString(R.string.last_server_sync_pref),
                                Calendar.getInstance().getTimeInMillis());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.log(LOG_TAG, "Error syncing server info", Log.ERROR);
            }
        });

        requestsQueue.add(request);
    }

    public void getUsers() {
        String apiAuthority = ServerUtility.getServer().getString(SERVER_ADDRESS_KEY);
        String api = ApiModel.UserSecurityApi.Builder(apiAuthority).getAllUsers(false, null, null).build();
        UserRequest userRequest = new UserRequest(api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.log(LOG_TAG, "Users synced...", Log.VERBOSE);
                        GeneralUtility.setSharedPreference(
                                applicationContext,
                                applicationContext.getString(R.string.last_user_sync_pref),
                                Calendar.getInstance().getTimeInMillis());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestsQueue.add(userRequest);
    }


}