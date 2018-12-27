package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstalledApp

@Dao
interface InstalledAppsDao : BaseDao<InstalledApp> {
    @Query("SELECT * FROM InstalledApps WHERE DeviceId = :deviceId")
    fun getDeviceInstalledApps(deviceId: String): LiveData<List<InstalledApp>>
}