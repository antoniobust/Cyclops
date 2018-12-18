package com.mdmobile.cyclops.db

import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.Profile

@Dao
interface ProfileDao : BaseDao<Profile> {
    @Query("DELETE FROM Profile WHERE referenceId = :referenceId")
    fun deleteByReferenceId(referenceId: String): Int
}