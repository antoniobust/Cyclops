package com.mdmobile.cyclops.dataModel

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import com.mdmobile.cyclops.ApplicationLoader.applicationContext
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.provider.McContract

data class Server(val serverName: String = "", val apiSecret: String = "", val clientId: String = "", val serverAddress: String = "") {

    fun setActive() {
        val preferences = applicationContext.getSharedPreferences(
                applicationContext.getString(R.string.server_shared_preference),
                MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(applicationContext.getString(R.string.server_name_preference), serverName)
        editor.putString(applicationContext.getString(R.string.api_secret_preference), apiSecret)
        editor.putString(applicationContext.getString(R.string.client_id_preference), clientId)
        editor.putString(applicationContext.getString(R.string.server_address_preference), serverAddress)
        editor.apply()
    }

    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(McContract.ServerInfo.NAME, serverName)
        values.put(McContract.ServerInfo.CLIENT_SECRET, apiSecret)
        values.put(McContract.ServerInfo.CLIENT_ID, clientId)
        values.put(McContract.ServerInfo.SERVER_ADDRESS, serverAddress)

        return values
    }
}
