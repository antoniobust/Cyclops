package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.Device

/**
 * Interface for db operation for Device operations
 */

@Dao
interface DeviceDao : BaseDao<Device> {
    @Query("SELECT * FROM DeviceInfo")
    fun getDevices(): LiveData<List<Device>>

    @Query("SELECT * FROM DeviceInfo WHERE DeviceId = :devId")
    fun getDevice(devId: String): LiveData<Device>

    @Query("SELECT * FROM DeviceInfo WHERE instanceId= :instanceId")
    fun getDevicesByInstance(instanceId: Int): LiveData<List<Device>>
}
