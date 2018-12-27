package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.db.MobiControlDB
import javax.inject.Inject

class InstanceRepository @Inject constructor(
        private val instance: InstanceInfo,
        private val appExecutors: ApplicationExecutors) {

    fun loadInstance(): LiveData<Resource<InstanceInfo>> {
        return object : NetworkBoundResource<InstanceInfo, InstanceInfo>(appExecutors) {
            override fun saveApiResult(item: InstanceInfo) {
                MobiControlDB.database.instanceDao().insert(instance)
            }

            override fun shouldFetch(data: InstanceInfo?): Boolean = false


            override fun loadFromDb(): LiveData<InstanceInfo> {
                return MobiControlDB.database.instanceDao().getInstance(instance.id.toString())
            }

            override fun createCall(): LiveData<ApiResponse<InstanceInfo>> {
                throw UnsupportedOperationException("Instance info doesn't support create call method as cannot be" +
                        "fetched from online services")
            }

        }.asLiveData()
    }
}
