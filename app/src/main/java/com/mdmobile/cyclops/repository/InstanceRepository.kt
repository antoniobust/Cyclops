package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.db.MobiControlDB
import com.mdmobile.cyclops.testing.OpenForTesting
import retrofit2.Retrofit
import javax.inject.Inject

@OpenForTesting
class InstanceRepository @Inject constructor(
        private val retrofitBuilder:Retrofit.Builder,
        private val db: MobiControlDB,
        private val appExecutors: ApplicationExecutors) {


    fun loadInstance(instanceName: String): LiveData<InstanceInfo> =
            db.instanceDao().getInstanceByName(instanceName)

    fun loadInstance(instanceInfo: InstanceInfo): LiveData<InstanceInfo> =
            db.instanceDao().getInstanceById(instanceInfo.id)

    fun loadAllInstances(): LiveData<List<InstanceInfo>> =
            db.instanceDao().getAllInstances()


    fun getToken(instanceInfo: InstanceInfo): LiveData<ApiResponse<Token>> {
        return retrofitBuilder.baseUrl(instanceInfo.serverAddress).build().create(McApiService::class.java).getAuthToken()
    }
}
