package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.ManagmentServer

@Dao
interface ManagementServerDao : BaseDao<ManagmentServer> {
    @Query("SELECT * FROM DsInfo")
    fun getAllMs(): LiveData<List<ManagmentServer>>

    @Query("SELECT * FROM DsInfo WHERE id = :id")
    fun getMs(id: String): LiveData<ManagmentServer>
}