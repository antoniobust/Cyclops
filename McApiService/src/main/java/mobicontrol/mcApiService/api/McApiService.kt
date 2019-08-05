package mobicontrol.mcApiService.api

import mobicontrol.mcApiService.dataTypes.ProfileActions
import mobicontrol.mcApiService.dataModel.*
import mobicontrol.mcApiService.dataTypes.ApiActions
import mobicontrol.mcApiService.dataTypes.ComplexDataType
import retrofit2.http.*

/**
 * Interface for MC apis
 */

interface McApiService {

    //Token
    @GET("/token")
    fun getAuthToken(): ApiResponse<Token>

    //Devices APIs
    @GET("/search")
    fun getDevices(
            @Query("path") path: String? = null,
            @Query("skip") skip: Int? = null,
            @Query("take") take: Int? = null,
            @Query("order") order: String? = null,
            @Query("filter") filter: String? = null,
            @Query("userFilter") userFilter: String? = null
    ): ApiResponse<List<Device>>

    @GET("/devices/{deviceId}")
    fun getDevice(@Path("deviceId") devId: String): ApiResponse<Device>

    @DELETE("/devices/{deviceId}")
    fun deleteDevice(): ApiResponse<Void>

    @POST("/devices/{deviceId}/actions")
    fun sendAction(@Path("deviceId") devId: String, @Body action: ApiActions): ApiResponse<Void>

    @GET("/api/devices/{deviceId}/collectedData")
    fun getCollectedData(
            @Path("deviceId") devId: String,
            @Query("startDate") startData: String,
            @Query("endDate") endDate: String,
            @Query("builtInDataType") builtInDataType: ComplexDataType.BuiltInDataType?,
            @Query("customDataType") customDataType: String?
    ): ApiResponse<List<CollectedData>>

    @GET("/api/devices/collectedData")
    fun getAllCollectedData(
            @Query("startDate") startData: String,
            @Query("endDate") endDate: String,
            @Query("builtInDataType") builtInDataType: ComplexDataType.BuiltInDataType?,
            @Query("customDataType") customDataType: String?,
            @Query("path") path: String?,
            @Query("skip") skip: Int?,
            @Query("take") take: Int?
    ): ApiResponse<List<CollectedData>>

    @GET("/api/devices/{deviceId}/installedApplications")
    fun getInstalledApps(@Path("deviceId") devId: String): ApiResponse<List<InstalledApp>>

    @GET("/api/devices/{deviceId}/profiles")
    fun getDeviceProfiles(@Path("deviceId") devId: String): ApiResponse<List<Profile>>

    @POST("/api/devices/{deviceId}/profiles/{profileId}/actions")
    fun deviceProfileAction(@Path("deviceId") devId: String,
                            @Path("profileId") profileId: String,
                            @Body action: ProfileActions): ApiResponse<Void>


    //Server APIs
    @GET("/api/servers")
    fun getServers(): ApiResponse<ServerInfo>

    //User APIs
    @GET("/api/security/users")
    fun getUsers(): ApiResponse<List<User>>

}