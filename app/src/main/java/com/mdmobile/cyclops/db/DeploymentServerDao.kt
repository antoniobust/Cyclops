package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.DeploymentServer

@Dao
interface DeploymentServerDao : BaseDao<DeploymentServer> {
    @Query("SELECT * FROM DsInfo")
    fun getAllDs(): LiveData<List<DeploymentServer>>

    @Query("SELECT * FROM DsInfo WHERE instanceId = :instanceId")
    fun getAllDsByInstance(instanceId: Int): LiveData<List<DeploymentServer>>

    @Query("SELECT * FROM DsInfo WHERE id = :id")
    fun getDs(id: String): LiveData<DeploymentServer>
}
