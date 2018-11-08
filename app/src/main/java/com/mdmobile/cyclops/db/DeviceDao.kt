package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice

/**
 * Interface for db operation for Device operations
 */

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(devices: List<BasicDevice>): List<Long>

    @Query("SELECT * FROM DeviceInfo")
    fun getDevices(): LiveData<List<BasicDevice>>

    @Query("SELECT * FROM DeviceInfo WHERE DeviceId = :devId")
    fun getDevice(devId: String): LiveData<BasicDevice>

    @Update
    fun updateDeviceInfo(newDevice: BasicDevice): Int
}