package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.INSTANCE_INFO_TABLE_NAME)
data class InstanceInfo(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val serverName: String,
        val apiSecret: String,
        val clientId: String,
        val serverAddress: String,
        val serverMajorVersion: Int = -1,
        val buildNumber: Int = -1,
        val currentToken: String = "NULL")