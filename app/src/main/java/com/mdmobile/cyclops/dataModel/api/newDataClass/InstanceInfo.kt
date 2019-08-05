package com.mdmobile.cyclops.dataModel.api.newDataClass

import android.net.Uri
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mdmobile.cyclops.provider.McContract
import kotlinx.android.parcel.Parcelize
import okhttp3.HttpUrl

@Parcelize
@Entity(tableName = McContract.INSTANCE_INFO_TABLE_NAME)
data class InstanceInfo(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val instanceName: String = "N/A",
        val apiSecret: String = "N/A",
        val clientId: String = "N/A",
        val serverAddress: String = "https:\\\\N/A",
        val serverMajorVersion: String = "N/A",
        val buildNumber: String = "N/A",
        val mode: Int = 0,
        @Embedded val token: Token) : Parcelable {

    @Ignore
    constructor(
            instanceName: String = "N/A",
            apiSecret: String = "N/A",
            clientId: String = "N/A",
            serverAddress: String = "https:\\\\NA/",
            serverMajorVersion: String = "N/A",
            buildNumber: String = "N/A",
            mode: Int = 0,
            token: Token = Token()
    ) : this(-1, instanceName, apiSecret, clientId, serverAddress, serverMajorVersion, buildNumber, mode, token)

    companion object {
        fun validateAddress(address: String): String {
            var addr = address
            if (!addr.startsWith("https://")) {
                addr = "https://$addr"
            }
            val url = Uri.parse(addr)
            val segment = url.lastPathSegment
            if (segment != "Mobicontrol/") {
                addr = url.buildUpon().appendPath("MobiControl").build().toString().plus("/")
            }
            return addr
        }

        fun instanceNotDefault(instanceInfo: InstanceInfo): Boolean {
            return !(instanceInfo.serverAddress.isEmpty() || instanceInfo.serverAddress == "https:\\\\N/A/"
                    || instanceInfo.apiSecret.isEmpty() || instanceInfo.apiSecret == "N/A"
                    || instanceInfo.clientId.isEmpty() || instanceInfo.clientId == "N/A"
                    || instanceInfo.instanceName.isEmpty() || instanceInfo.instanceName == "N/A")
        }
    }
}