package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName

data class ManagementServer(
        @field:SerializedName("Fqdn")
        val fqdn: String,
        @field:SerializedName("Description")
        val description: String,
        @field:SerializedName("StatusTime")
        val statusTime: String,
        @field:SerializedName("MacAddress")
        val macAddress: String,
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("Status")
        val status: String,
        @field:SerializedName("PortNumber")
        val portNumber: Int,
        @field:SerializedName("TotalConsoleUsers")
        val totalConsoleUsers: Int,
        val instanceId:Int)