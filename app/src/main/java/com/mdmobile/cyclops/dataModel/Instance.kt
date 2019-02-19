package com.mdmobile.cyclops.dataModel

import android.content.ContentValues
import android.os.Parcelable
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.util.ServerUtility
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Instance(val serverName: String = "", val apiSecret: String = "", val clientId: String = "", val serverAddress: String = "",
                    val serverMajorVersion: Int = -1, val buildNumber: Int = -1):Parcelable {

    fun setActive() {
        ServerUtility.setActiveServer(this)
    }

    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(McContract.ServerInfo.NAME, serverName)
        values.put(McContract.ServerInfo.CLIENT_SECRET, apiSecret)
        values.put(McContract.ServerInfo.CLIENT_ID, clientId)
        values.put(McContract.ServerInfo.SERVER_ADDRESS, serverAddress)
        values.put(McContract.ServerInfo.SERVER_MAJOR_VERSION, serverMajorVersion)
        values.put(McContract.ServerInfo.SERVER_BUILD_NUMBER, buildNumber)

        return values
    }
}
