package com.mdmobile.cyclops.api

import com.mdmobile.cyclops.dataModel.api.*
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for MC apis
 */


interface McApiService {

    //Devices APIs
    @GET("/devices")
    fun getDevices(): Call<List<BasicDevice>>

    @GET("/search")
    fun getDevicesV14(): Call<List<BasicDevice>>

    @GET("/devices/{deviceId}")
    fun getDevice(@Path("deviceId") devId: String): Call<BasicDevice>

    @DELETE("/devices/{deviceId}")
    fun deleteDevice(): Call<Void>

    @POST("/devices/{deviceId}/actions")
    fun sendAction(@Path("deviceId") devId: String, @Body action: Action): Call<Void>

    @GET("/api/devices/{deviceId}/collectedData")
    fun getCollectedData(@Path("deviceId") devId: String): Call<List<CollectedData>>

    @GET("/api/devices/collectedData")
    fun getAllCollectedData(): Call<List<CollectedData>>

    @GET("/api/devices/{deviceId}/installedApplications")
    fun getInstalledApps(@Path("deviceId") devId: String): Call<List<InstalledApp>>

    @POST("/api/devices/{deviceId}/profiles/{profileId}/actions")
    fun getDeviceProfiles(@Path("deviceId") devId: String, @Path("profileId") profileId: String): Call<Void>

    @POST("/api/devices/{deviceId}/profiles")
    fun getDeviceProfiles(@Path("deviceId") devId: String): Call<List<Profile>>


    //Server APIs
    @GET("/api/servers")
    fun getServers(): Call<List<ServerInfo>>

    //User APIs
    @GET("/api/security/users")
    fun getInstalledApps(): Call<List<User>>

}