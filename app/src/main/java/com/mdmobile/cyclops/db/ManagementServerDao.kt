package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.ManagementServer

@Dao
interface ManagementServerDao : BaseDao<ManagementServer> {
    @Query("SELECT * FROM MsInfo")
    fun getAllMs(): LiveData<List<ManagementServer>>

    @Query("SELECT * FROM MsInfo WHERE id = :id")
    fun getMs(id: String): LiveData<ManagementServer>
}