package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.ServerInfo
import com.mdmobile.cyclops.db.MobiControlDB
import com.mdmobile.cyclops.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class InstanceRepository @Inject constructor(
        private val mcApiService: McApiService,
        private val db: MobiControlDB,
        private val appExecutors: ApplicationExecutors) {


    fun getServerInfo(instanceInfo: InstanceInfo): LiveData<Resource<InstanceInfo>> {
        return object : NetworkBoundResource<InstanceInfo, ServerInfo>(appExecutors) {
            override fun shouldFetch(data: InstanceInfo?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<InstanceInfo> {
                return db.instanceDao().getInstanceByName(instanceInfo.instanceName)
            }

            override fun createCall(): LiveData<ApiResponse<ServerInfo>> {
                return mcApiService.getServers()
            }

            override fun saveApiResult(item: ServerInfo) {
                val dsInfo = item.deploymentServer
                val msInfo = item.managementServer
                if (item.ProductVersion != null && item.ProductVersionBuild != null) {
                    val instance = instanceInfo.copy(serverMajorVersion = item.ProductVersion,
                            buildNumber = item.ProductVersionBuild)
                    db.instanceDao().insert(instance)
                }
                db.deploymentServerDao().insertAll(dsInfo)
                db.managementServerDao().insertAll(msInfo)
            }

            override fun onFetchFailed() {
                super.onFetchFailed()

            }
        }.asLiveData()


    }
//
//    fun isValidVersion(serverInfo: ServerInfo): Boolean {
//        return serverInfo.ProductVersion != null && serverInfo.ProductVersion >= "14"
//    }
}
