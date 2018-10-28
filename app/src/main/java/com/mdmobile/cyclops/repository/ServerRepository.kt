package com.mdmobile.cyclops.repository

import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Instance
import javax.inject.Inject

class ServerRepository @Inject constructor(
        private val appExecutors: ApplicationExecutors,
        private val instance: Instance,
        private val apiService: McApiService) {

//    fun loadServer(): LiveData<Resource<ServerInfo>>{
//        return object : NetworkBoundResource<ServerInfo,ServerInfo>(){
//            override fun saveApiResult(item: ServerInfo) {
//
//            }
//
//            override fun shouldFetch(data: ServerInfo?): Boolean {
//            }
//
//            override fun loadFromDb(): LiveData<ServerInfo> {
//            }
//
//            override fun createCall(): LiveData<ApiResponse<ServerInfo>> {
//            }
//
//        }.asLiveData()
}
