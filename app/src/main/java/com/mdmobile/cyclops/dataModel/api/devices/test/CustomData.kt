package com.mdmobile.cyclops.dataModel.api.devices.test

import com.google.gson.annotations.SerializedName

data class CustomData(
        @SerializedName("Name") val name: String = "",
        @SerializedName("Value") val value: String = ""
)
