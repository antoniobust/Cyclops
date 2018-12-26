package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.Profile

@Dao
interface ProfileDao : BaseDao<Profile> {
    @Query("DELETE FROM Profile WHERE referenceId = :referenceId")
    fun deleteByReferenceId(referenceId: String): Int

    @Query("DELETE FROM Profile WHERE referenceId = :id")
    fun deleteById(id: Int): Int

    @Query("SELECT * FROM Profile")
    fun getAllProfiles(): LiveData<List<Profile>>

    @Query("SELECT * FROM Profile where referenceId = :referenceId")
    fun getProfileByReferenceId(referenceId: String): LiveData<Profile>

    @Query("SELECT * FROM Profile where id= :id")
    fun getProfileById(id: Int): LiveData<Profile>


}