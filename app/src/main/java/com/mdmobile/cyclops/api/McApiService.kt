package com.mdmobile.cyclops.api

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.dataModel.api.*
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import com.mdmobile.cyclops.dataTypes.ComplexDataType
import com.mdmobile.cyclops.dataTypes.ProfileActions
import retrofit2.http.*

/**
 * Interface for MC apis
 */


interface McApiService {

    //Token
    @GET("/token")
    fun getAuthToken(): LiveData<ApiResponse<Token>>

    //Devices APIs
    @GET("/devices")
    fun getDevices(
            @Query("path") path: String?,
            @Query("skip") skip: Int?,
            @Query("take") take: Int?,
            @Query("order") order: String?,
            @Query("filter") filter: String?,
            @Query("userFilter") userFilter: String?
    ): LiveData<ApiResponse<List<BasicDevice>>>

    @GET("/search")
    fun getDevicesV14(): LiveData<ApiResponse<List<BasicDevice>>>

    @GET("/devices/{deviceId}")
    fun getDevice(@Path("deviceId") devId: String): LiveData<ApiResponse<BasicDevice>>

    @DELETE("/devices/{deviceId}")
    fun deleteDevice(): LiveData<ApiResponse<Void>>

    @POST("/devices/{deviceId}/actions")
    fun sendAction(@Path("deviceId") devId: String, @Body action: Action): LiveData<ApiResponse<Void>>

    @GET("/api/devices/{deviceId}/collectedData")
    fun getCollectedData(
            @Path("deviceId") devId: String,
            @Query("startDate") startData: String,
            @Query("endDate") endDate: String,
            @Query("builtInDataType") builtInDataType: ComplexDataType.BuiltInDataType?,
            @Query("customDataType") customDataType: String?
    ): LiveData<ApiResponse<List<CollectedData>>>

    @GET("/api/devices/collectedData")
    fun getAllCollectedData(
            @Query("startDate") startData: String,
            @Query("endDate") endDate: String,
            @Query("builtInDataType") builtInDataType: ComplexDataType.BuiltInDataType?,
            @Query("customDataType") customDataType: String?,
            @Query("path") path: String?,
            @Query("skip") skip: Int?,
            @Query("take") take: Int?
    ): LiveData<ApiResponse<List<CollectedData>>>

    @GET("/api/devices/{deviceId}/installedApplications")
    fun getInstalledApps(@Path("deviceId") devId: String): LiveData<ApiResponse<List<InstalledApp>>>

    @GET("/api/devices/{deviceId}/profiles")
    fun getDeviceProfiles(@Path("deviceId") devId: String): LiveData<ApiResponse<List<Profile>>>

    @POST("/api/devices/{deviceId}/profiles/{profileId}/actions")
    fun deviceProfileAction(@Path("deviceId") devId: String,
                            @Path("profileId") profileId: String,
                            @Body @ProfileActions action: String): LiveData<ApiResponse<Void>>


    //Server APIs
    @GET("/api/servers")
    fun getServers(): LiveData<ApiResponse<List<ServerInfo>>>

    //User APIs
    @GET("/api/security/users")
    fun getInstalledApps(): LiveData<ApiResponse<List<User>>>

}