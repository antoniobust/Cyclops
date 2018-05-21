package com.mdmobile.cyclops.dataModel.api.devices.test

import com.google.gson.annotations.SerializedName

data class Memory(
        @SerializedName("AvailableExternalStorage") val availableExternalStorage: Long = 0,
        @SerializedName("AvailableMemory") val availableMemory: Int = 0,
        @SerializedName("AvailableSDCardStorage") val availableSDCardStorage: Any = Any(),
        @SerializedName("AvailableStorage") val availableStorage: Long = 0,
        @SerializedName("TotalExternalStorage") val totalExternalStorage: Long = 0,
        @SerializedName("TotalMemory") val totalMemory: Int = 0,
        @SerializedName("TotalSDCardStorage") val totalSDCardStorage: Any = Any(),
        @SerializedName("TotalStorage") val totalStorage: Long = 0
)