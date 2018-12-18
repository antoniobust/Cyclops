package com.mdmobile.cyclops.api.db

import com.mdmobile.cyclops.api.db.util.TestUtils
import org.hamcrest.MatcherAssert
import org.junit.Test

class DeviceDaoTest : DbTest() {

    private val instance = TestUtils.createInstance()
    private val device = TestUtils.createDevice(instance)
    @Test
    fun insertAndRead() {
        db.beginTransaction()

        db.instanceDao().insert(instance)
        val newItem = db.deviceDao().insert(device)
        MatcherAssert.assertThat("New device id doesn't match the test one $newItem", newItem.toInt() == device.id)
        val devices = TestUtils.getValue(db.deviceDao().getDevices())
        MatcherAssert.assertThat("Select operation returned unexpected value: $devices", devices!!.find {
            it.id == device.id
        } != null)
        val instanceDevices = TestUtils.getValue(db.deviceDao().getDevicesByInstanceId(instance.id.toString()))
        MatcherAssert.assertThat("Select operation returned unexpected value: $devices", !instanceDevices.isNullOrEmpty() && instanceDevices.find {
            it.id == device.id
        } != null)
        db.endTransaction()
    }

    @Test
    fun delete() {
        insertAndRead()
        val deleted = db.deviceDao().delete(device)
        MatcherAssert.assertThat("Device not deleted: $deleted", deleted == 1)
    }
}