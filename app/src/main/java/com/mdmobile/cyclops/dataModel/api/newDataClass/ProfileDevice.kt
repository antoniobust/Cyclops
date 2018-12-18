package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.PROFILE_DEVICE_TABLE_NAME,
        primaryKeys = ["profileId", "deviceId"],
        foreignKeys = [ForeignKey(entity = Device::class, parentColumns = arrayOf("deviceId"), childColumns = arrayOf("deviceId")),
            ForeignKey(entity = Profile::class, parentColumns = arrayOf("id"), childColumns = arrayOf("profileId"))])
data class ProfileDevice(
        val profileId: Int,
        val deviceId: Int)