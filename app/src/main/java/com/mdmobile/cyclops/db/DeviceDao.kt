package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice

/**
 * Interface for db operation for Device operations
 */

@Dao
interface DeviceDao : BaseDao<BasicDevice> {
    @Query("SELECT * FROM DeviceInfo")
    fun getDevices(): LiveData<List<BasicDevice>>

    @Query("SELECT * FROM DeviceInfo WHERE DeviceId = :devId")
    fun getDevice(devId: String): LiveData<BasicDevice>
}