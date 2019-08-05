package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.db.MobiControlDB
import javax.inject.Inject

class TokenRepository @Inject constructor(private val instanceInfo: InstanceInfo,
                                          private val apiClient: McApiService,
                                          private val appExecutors: ApplicationExecutors,
                                          private val db: MobiControlDB) {

    fun getCachedToken(instanceInfo: InstanceInfo): Token {
        return db.instanceDao().getInstanceToken(instanceInfo.instanceName)
    }

//    fun refreshToken(): ApiResponse<Token> {
//        apiClient.getAuthToken()
//    }

    fun saveToken(instance: InstanceInfo) {
        db.instanceDao().update(instance)
    }
}