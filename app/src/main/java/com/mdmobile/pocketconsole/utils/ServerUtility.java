package com.mdmobile.pocketconsole.utils;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.mdmobile.pocketconsole.R;

import static android.content.Context.MODE_PRIVATE;
import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.API_SECRET_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.CLIENT_ID_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_ADDRESS_KEY;
import static com.mdmobile.pocketconsole.services.AccountAuthenticator.SERVER_NAME_KEY;

/**
 * Utility class for server info methods
 */

public class ServerUtility {

    public static void saveServerInfo(String serverName, String apiSecret, String clientId, String serverAddress) {

        SharedPreferences preferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(applicationContext.getString(R.string.server_name_preference), serverName);
        editor.putString(applicationContext.getString(R.string.api_secret_preference), apiSecret);
        editor.putString(applicationContext.getString(R.string.client_id_preference), clientId);
        editor.putString(applicationContext.getString(R.string.server_address_preference), serverAddress);

        editor.apply();
    }

    public static Bundle getServer() {

                SharedPreferences preferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.server_shared_preference), MODE_PRIVATE);
                String serverName = preferences.getString(applicationContext.getString(R.string.server_name_preference), null);
                String apiSecret = preferences.getString(applicationContext.getString(R.string.api_secret_preference), null);
                String clientId = preferences.getString(applicationContext.getString(R.string.client_id_preference), null);
                String address = preferences.getString(applicationContext.getString(R.string.server_address_preference), null);

                if (serverName != null && apiSecret != null && clientId != null && address != null) {
                    Bundle bundle = new Bundle(4);
                    bundle.putString(SERVER_NAME_KEY, serverName);
                    bundle.putString(SERVER_ADDRESS_KEY, address);
                    bundle.putString(CLIENT_ID_KEY, clientId);
                    bundle.putString(API_SECRET_KEY, apiSecret);

                    return bundle;
                } else {
                    return null;
        }
    }
}
