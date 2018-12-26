package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.PROFILE_DEVICE_TABLE_NAME,
        primaryKeys = ["profileReferenceId", "deviceId"],
        foreignKeys = [ForeignKey(entity = Device::class, parentColumns = arrayOf("deviceId"), childColumns = arrayOf("deviceId")),
            ForeignKey(entity = Profile::class, parentColumns = arrayOf("referenceId"), childColumns = arrayOf("profileReferenceId"))],
        indices = [Index(value = ["profileReferenceId"], unique = true)])
data class ProfileDevice(
        val profileReferenceId: String,
        val deviceId: String)