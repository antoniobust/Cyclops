package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.Device
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import javax.inject.Inject

class DeviceRepository @Inject constructor(
        private val instance:InstanceInfo,
        private val appExecutors: ApplicationExecutors){

    fun loadDevices(): LiveData<Resource<Device>>{
        return object: NetworkBoundResource<Device,Device>(appExecutors){
            override fun saveApiResult(item: Device) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: Device?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun loadFromDb(): LiveData<Device> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createCall(): LiveData<ApiResponse<Device>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }.asLiveData()
    }
}