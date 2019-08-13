package com.mdmobile.cyclops.repository

import android.util.Log
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.ApiSuccessResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.db.MobiControlDB
import com.mdmobile.cyclops.util.Logger
import javax.inject.Inject

class TokenRepository @Inject constructor(private val instanceInfo: InstanceInfo,
                                          private val apiClient: McApiService,
                                          private val appExecutors: ApplicationExecutors,
                                          private val db: MobiControlDB) {

    private val logTag = TokenRepository::class.java.simpleName

    fun getCachedToken(instanceInfo: InstanceInfo): Token {
        Logger.log(logTag, "Getting cached token for ${instanceInfo.instanceName}", Log.VERBOSE)
        return db.instanceDao().getInstanceToken(instanceInfo.instanceName)
    }

    fun refreshToken(): ApiResponse<Token> {
        Logger.log(logTag, "Sending request for new Token for ${instanceInfo.instanceName}", Log.VERBOSE)
        val tokenResponse = apiClient.getAuthToken()
        if (tokenResponse is ApiSuccessResponse) {
            Logger.log(logTag, "Received new Token for${instanceInfo.instanceName}", Log.VERBOSE)
            val updatedInstanceInfo = instanceInfo.copy(token = tokenResponse.body)
            saveToken(updatedInstanceInfo)
        }
        return tokenResponse
    }

    fun saveToken(instance: InstanceInfo) {
        Logger.log(logTag, "Saving token for ${instanceInfo.instanceName}", Log.VERBOSE)
        db.instanceDao().update(instance)
    }
}