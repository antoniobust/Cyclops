package com.mdmobile.cyclops.db

import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstalledApps

@Dao
interface InstalledAppsDao : BaseDao<InstalledApps> {
    @Query("SELECT * FROM InstalledApps WHERE DeviceId = :deviceId")
    fun getDeviceInstalledApps(deviceId: String): List<InstalledApps>
}