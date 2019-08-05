package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName

//TODO:excluding some data as MC return null which is parsed and saved in DB -> when read from kotlin null causes issues
//    val Version: String = "N/A", /**val ShortVersion: String = "N/A"*,*/ val SizeInBytes: String = "N/A",
//    val DataSizeInBytes: String = "N/A",


class InstalledApp(
        @field:SerializedName("DeviceId")
        val deviceId: String,
        @field:SerializedName("ApplicationId")
        val packageId: String,
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("Status")
        val status: String)