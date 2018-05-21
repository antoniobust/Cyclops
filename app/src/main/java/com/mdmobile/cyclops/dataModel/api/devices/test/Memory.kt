package com.mdmobile.cyclops.dataModel.api.devices.test

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Memory(
        @SerializedName("AvailableExternalStorage") val availableExternalStorage: Long = 0,
        @SerializedName("AvailableMemory") val availableMemory: Int = 0,
        @SerializedName("AvailableSDCardStorage") val availableSDCardStorage: Long = 0,
        @SerializedName("AvailableStorage") val availableStorage: Long = 0,
        @SerializedName("TotalExternalStorage") val totalExternalStorage: Long = 0,
        @SerializedName("TotalMemory") val totalMemory: Int = 0,
        @SerializedName("TotalSDCardStorage") val totalSDCardStorage: Long = 0,
        @SerializedName("TotalStorage") val totalStorage: Long = 0
) : Parcelable