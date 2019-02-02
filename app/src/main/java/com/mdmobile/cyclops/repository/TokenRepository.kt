package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.db.InstanceDao
import javax.inject.Inject

class TokenRepository @Inject constructor(
        private val instance: InstanceInfo,
        private val appExecutors: ApplicationExecutors,
        private val remoteService: McApiService,
        private val instanceDao: InstanceDao) {

    fun loadToken(): LiveData<Resource<Token>> {
        return object : NetworkBoundResource<Token, Token>(appExecutors) {
            override fun saveApiResult(item: Token) {
                val _instance = instance.copy(token = item.token)
                instanceDao.insert(_instance)
            }

            override fun shouldFetch(data: Token?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Token> {
                return instanceDao.getInstanceToken(instance.id)
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                return remoteService.getAuthToken()
            }
        }.asLiveData()
    }
}