package com.mdmobile.cyclops.repository

import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.Device
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.db.DeviceDao
import javax.inject.Inject

class DeviceRepository @Inject constructor(
        private val instance: InstanceInfo,
        private val appExecutors: ApplicationExecutors,
        private val remoteService: McApiService,
        private val deviceDao: DeviceDao) {

    fun loadDevices(): LiveData<Resource<List<Device>>> {
        return object : NetworkBoundResource<List<Device>, List<Device>>(appExecutors) {
            override fun saveApiResult(item: List<Device>) {
                deviceDao.insertAll(item)
            }

            override fun shouldFetch(data: List<Device>?): Boolean {
                //TODO: implement should fetch logic
                return true
            }

            override fun loadFromDb(): LiveData<List<Device>> {
                return deviceDao.getDevicesByInstance(instance.id)
            }

            override fun createCall(): LiveData<ApiResponse<List<Device>>> {
                return remoteService.getDevices()
            }

        }.asLiveData()
    }

    fun loadDevice(deviceId: String): LiveData<Resource<Device>> {
        return object : NetworkBoundResource<Device, Device>(appExecutors) {
            override fun saveApiResult(item: Device) {
                deviceDao.insert(item.copy(instanceId = instance.id))
            }

            override fun shouldFetch(data: Device?): Boolean {
                //TODO: implement should fetch logic
                return true
            }

            override fun loadFromDb(): LiveData<Device> {
                return deviceDao.getDevice(deviceId)
            }

            override fun createCall(): LiveData<ApiResponse<Device>> {
                return remoteService.getDevice(deviceId)
            }

        }.asLiveData()
    }
}