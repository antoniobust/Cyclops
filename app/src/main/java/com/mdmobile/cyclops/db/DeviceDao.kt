package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice

/**
 * Interface for db operation for Device operations
 */

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(devices: List<BasicDevice>)

    @Query("SELECT * FROM ")
    fun findByDevId(devId:String): LiveData<BasicDevice>
}