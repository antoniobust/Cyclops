package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.Profile
import com.mdmobile.cyclops.dataModel.api.newDataClass.ProfileDevice

@Dao
interface ProfileDeviceDao : BaseDao<ProfileDevice> {
    @Query("SELECT * from Profile INNER JOIN ProfileDevice ON ProfileDevice.profileId = Profile.id INNER JOIN DeviceInfo ON DeviceInfo.id = ProfileDevice.deviceId WHERE DeviceInfo.deviceId = :deviceId")
    fun getAllProfilesByDevice(deviceId: String): LiveData<List<Profile>>
}