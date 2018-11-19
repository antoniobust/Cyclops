package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo

@Dao
interface InstanceDao : BaseDao<InstanceInfo> {
    @Query("SELECT * FROM InstanceInfo")
    fun getAllInstances(): LiveData<List<BasicDevice>>

    @Query("SELECT * FROM InstanceInfo WHERE id = :id")
    fun getInstance(id: String): LiveData<InstanceInfo>

    @Query("SELECT * FROM InstanceInfo WHERE serverName = :serverName")
    fun getInstanceByName(serverName: String): LiveData<InstanceInfo>
}