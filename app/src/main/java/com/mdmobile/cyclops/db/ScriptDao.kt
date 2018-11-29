package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.Script

@Dao
interface ScriptDao : BaseDao<Script> {

    @Query("SELECT * FROM Script")
    fun getScripts(): LiveData<List<Script>>

    @Query("SELECT * FROM Script WHERE id = :id")
    fun getScript(id: String): LiveData<Script>

}