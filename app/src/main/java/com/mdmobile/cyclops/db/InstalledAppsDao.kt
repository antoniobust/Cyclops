package com.mdmobile.cyclops.db

import androidx.room.Dao
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstalledApps

@Dao
interface InstalledAppsDao :BaseDao<InstalledApps> {

}