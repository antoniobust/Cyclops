package com.mdmobile.cyclops.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.ApplicationLoader.applicationContext
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.ServerInfo
import com.mdmobile.cyclops.provider.McContract
import javax.inject.Inject

class ServerRepository @Inject constructor(
        private val appExecutors: ApplicationExecutors,
        private val instance: Instance,
        private val apiService: McApiService) {

//    fun loadServer(): LiveData<Resource<ServerInfo>> {
//        return object : NetworkBoundResource<ServerInfo, ServerInfo>(appExecutors) {
//            override fun saveApiResult(item: ServerInfo) {
//                applicationContext.contentResolver
//                        .bulkInsert(McContract.DsInfo.CONTENT_URI, item.dsToContentValues(instance.serverName))
//                applicationContext.contentResolver
//                        .bulkInsert(McContract.MsInfo.CONTENT_URI, item.msToContentValues(instance.serverName))
//
//            }
//
//            override fun shouldFetch(data: ServerInfo?): Boolean = data == null
//
//
//            override fun loadFromDb(): LiveData<ServerInfo> {
//                val cursor = applicationContext.contentResolver.query(
//                        McContract.DsInfo.CONTENT_URI, null, null, null, null)
//
//            }
//
//            override fun createCall(): LiveData<ApiResponse<ServerInfo>> {
//            }
//
//        }.asLiveData()
//    }
}
