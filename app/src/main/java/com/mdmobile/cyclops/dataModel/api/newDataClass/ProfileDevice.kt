package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.PROFILE_DEVICE_TABLE_NAME,
        foreignKeys = [ForeignKey(entity = Device::class, parentColumns = arrayOf("deviceId"), childColumns = arrayOf("deviceId")),
            ForeignKey(entity = Profile::class, parentColumns = arrayOf("profileId"), childColumns = arrayOf("profileId"))])
data class ProfileDevice(
        @PrimaryKey(autoGenerate = false)
        val profileId: Int,
        @PrimaryKey(autoGenerate = false)
        val deviceId: Int)