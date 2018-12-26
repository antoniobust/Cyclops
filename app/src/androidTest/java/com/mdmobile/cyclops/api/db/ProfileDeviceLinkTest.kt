package com.mdmobile.cyclops.api.db

import com.mdmobile.cyclops.api.db.util.TestUtils
import com.mdmobile.cyclops.dataModel.api.newDataClass.ProfileDevice
import org.hamcrest.MatcherAssert
import org.junit.Test

class ProfileDeviceLinkTest : DbTest() {

    private val profile = TestUtils.createProfile()
    private val instance = TestUtils.createInstance()
    private val device = TestUtils.createDevice(instance)
    private val profileDevice = ProfileDevice(profile.referenceId, device.deviceId)

    @Test
    fun insertAndRead() {
        db.instanceDao().insert(instance)
        db.deviceDao().insert(device)
        db.profileDao().insert(profile)
        val inserted = db.profileDeviceDao().insert(profileDevice)
        MatcherAssert.assertThat("Values not inserted correctly: $inserted", inserted.toInt() == 1)
        val profileDeviceNew = TestUtils.getValue(db.profileDeviceDao().getAllProfilesByDevice(device.deviceId))!!
        MatcherAssert.assertThat("Unexpected ProfileDevice value: $profileDeviceNew / $profileDevice",
                !profileDeviceNew.isNullOrEmpty() && profileDeviceNew.size == 1)

        val profileNew = TestUtils.getValue(db.profileDao().getProfileByReferenceId(profileDeviceNew[0].referenceId))
        MatcherAssert.assertThat("Unexpected Profile for device:$device -> Profile new: $profileDeviceNew \n old: $profileDevice",
                profileNew == profile)
    }

    @Test
    fun deleteProfileDeviceReferenceByDeletingDevice(){
        insertAndRead()
        db.deviceDao().delete(device)
        val profiles = TestUtils.getValue(db.profileDeviceDao().getAllProfilesByDevice(device.deviceId))
        MatcherAssert.assertThat("Unexpected profile in DB, should be empty: $profiles", profiles.isNullOrEmpty())
    }


}