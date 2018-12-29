package com.mdmobile.cyclops.api.db

import com.mdmobile.cyclops.commonTest.TestUtils
import org.hamcrest.MatcherAssert
import org.junit.Test

class InstalledAppDaoTest : DbTest() {

    private val instance = TestUtils.createInstance()
    private val device = TestUtils.createDevice(instance)
    private val application = TestUtils.createInstalledApp(device)

    @Test
    fun insertAndRead() {
        db.instanceDao().insert(instance)
        db.deviceDao().insert(device)
        val id = db.installedAppsDao().insert(application)
        MatcherAssert.assertThat("Inserted id is not what expected: $id", id.toInt() == application.id)

        val installedAppRead = TestUtils.getValue(db.installedAppsDao().getDeviceInstalledApps(deviceId = device.deviceId))
        MatcherAssert.assertThat("Inserted app returned unexpected values ${installedAppRead!![0]}", !installedAppRead.isNullOrEmpty() && installedAppRead.find {
            it == application
        } != null)
    }

    @Test
    fun deleteByDeviceDeletion(){
        insertAndRead()
        db.deviceDao().delete(device)
        val installedAppsRead = TestUtils.getValue(db.installedAppsDao().getDeviceInstalledApps(device.deviceId))
        MatcherAssert.assertThat("Installed apps not deleted after having deleted the device: ${device.id} -> $installedAppsRead", installedAppsRead.isNullOrEmpty())
    }

    @Test
    fun deleteApplication(){
        insertAndRead()
        db.installedAppsDao().delete(application)
        val installedAppsRead = TestUtils.getValue(db.installedAppsDao().getDeviceInstalledApps(device.deviceId))
        MatcherAssert.assertThat("Installed apps not deleted: $installedAppsRead", installedAppsRead.isNullOrEmpty())
    }
}