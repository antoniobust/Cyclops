package com.mdmobile.cyclops.dataModel.api.devices.test

import com.google.gson.annotations.SerializedName

data class Antivirus(
        @SerializedName("AntivirusDefinitionsVersion") val antivirusDefinitionsVersion: String = "",
        @SerializedName("LastEmptyQuarantine") val lastEmptyQuarantine: Int = 0,
        @SerializedName("LastVirusDefUpdate") val lastVirusDefUpdate: Int = 0,
        @SerializedName("LastVirusScan") val lastVirusScan: Int = 0
)