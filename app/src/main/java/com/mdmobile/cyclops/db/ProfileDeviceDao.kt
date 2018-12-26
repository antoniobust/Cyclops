package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.Profile
import com.mdmobile.cyclops.dataModel.api.newDataClass.ProfileDevice

@Dao
interface ProfileDeviceDao : BaseDao<ProfileDevice> {
    @Query("SELECT * from Profile INNER JOIN ProfileDevice ON ProfileDevice.profileReferenceId = Profile.referenceId INNER JOIN DeviceInfo ON DeviceInfo.deviceId = ProfileDevice.deviceId WHERE DeviceInfo.deviceId = :devId")
    fun getAllProfilesByDevice(devId: String): LiveData<List<Profile>>
}