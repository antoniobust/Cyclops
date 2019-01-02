package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.db.MobiControlDB
import javax.inject.Inject

class InstanceRepository @Inject constructor(
        private val instanceInfo: InstanceInfo,
        private val apiService: McApiService,
        private val appExecutors: ApplicationExecutors) {

    fun loadServer(): LiveData<Resource<InstanceInfo>> {
        return object : NetworkBoundResource<InstanceInfo, InstanceInfo>(appExecutors) {
            override fun saveApiResult(item: InstanceInfo) {
                MobiControlDB.database.instanceDao().insert(instanceInfo)
            }

            override fun shouldFetch(data: InstanceInfo?): Boolean = false


            override fun loadFromDb(): LiveData<InstanceInfo> {
                return MobiControlDB.database.instanceDao().getInstanceById(instanceInfo.id)
            }

            override fun createCall(): LiveData<ApiResponse<InstanceInfo>> {
                throw UnsupportedOperationException("Instance info doesn't support create call method as cannot be" +
                        "fetched from online services")
            }

        }.asLiveData()
    }

    fun refreshToken(): LiveData<Resource<InstanceInfo>> {
        return object : NetworkBoundResource<InstanceInfo, Token>(appExecutors) {
            override fun saveApiResult(item: Token) {
                val instanceInfo = instanceInfo.copy(currentToken = item.access_token)
                MobiControlDB.database.instanceDao().update(instanceInfo)
            }

            override fun shouldFetch(data: InstanceInfo?): Boolean {
                //TODO:Implement logic for fetching only when needed
                return true
            }

            override fun loadFromDb(): LiveData<InstanceInfo> {
                return MobiControlDB.database.instanceDao().getInstanceById(instanceInfo.id)
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return apiService.getAuthToken()
            }
        }.asLiveData()
    }
}
