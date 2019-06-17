package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token

@Dao
interface InstanceDao : BaseDao<InstanceInfo> {
    @Query("SELECT * FROM InstanceInfo")
    fun getAllInstances(): LiveData<List<InstanceInfo>>

    @Query("SELECT * FROM InstanceInfo WHERE id = :id")
    fun getInstanceById(id: Int): LiveData<InstanceInfo>

    @Query("SELECT * FROM InstanceInfo WHERE serverName = :serverName")
    fun getInstanceByName(serverName: String): LiveData<InstanceInfo>

    @Query("SELECT token FROM InstanceInfo WHERE id= :serverId")
    fun getInstanceToken(serverId: Int): LiveData<Token>

    @Query("SELECT token FROM InstanceInfo WHERE mode= 1")
    fun getActiveInstanceToken(): LiveData<Token>

    @Query("SELECT * FROM InstanceInfo WHERE mode= 1")
    fun getActiveInstance(): LiveData<InstanceInfo>

    @Query("SELECT token from InstanceInfo WHERE serverName = :instanceName")
    fun getInstanceToken(instanceName:String):LiveData<Token>

}