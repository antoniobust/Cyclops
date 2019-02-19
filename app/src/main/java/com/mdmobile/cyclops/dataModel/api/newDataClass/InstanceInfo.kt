package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.*
import com.mdmobile.cyclops.provider.McContract
import retrofit2.http.Field

@Entity(tableName = McContract.INSTANCE_INFO_TABLE_NAME)
data class InstanceInfo(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val serverName: String = "N/A",
        val apiSecret: String = "N/A",
        val clientId: String = "N/A",
        val serverAddress: String = "https:\\\\N/A",
        val serverMajorVersion: Int = -1,
        val buildNumber: Int = -1,
        @Embedded val token:Token) {

    @Ignore
    constructor(
            serverName: String = "N/A",
            apiSecret: String = "N/A",
            clientId: String = "N/A",
            serverAddress: String = "https:\\\\NA/",
            serverMajorVersion: Int = -1,
            buildNumber: Int = -1,
            token: Token = Token()
    ) : this(-1, serverName, apiSecret, clientId, serverAddress, serverMajorVersion, buildNumber,token)
}