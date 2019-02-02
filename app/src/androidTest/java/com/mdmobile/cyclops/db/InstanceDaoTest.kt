package com.mdmobile.cyclops.db

import androidx.test.runner.AndroidJUnit4
import com.mdmobile.cyclops.commonTest.LiveDataTestUtil.getValue
import com.mdmobile.cyclops.commonTest.TestUtils
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class InstanceDaoTest : DbTest() {

    private val instance = TestUtils.createInstance()
    private val device = TestUtils.createDevice(instance)

    @Test
    fun insertAndRead() {
        val newItem = db.instanceDao().insert(instance)
        assertThat("New instance id doesn't match the test one $newItem", newItem.toInt() == 190)
        val newInstance = getValue(db.instanceDao().getInstanceById(instance.id))
        assertThat("Select operation returned unexpected value: $newInstance", newInstance != null && newInstance == instance)
    }

    @Test
    fun insertAndDelete() {
        db.beginTransaction()
        insertAndRead()
        val elements = db.instanceDao().delete(instance)
        assertThat("Instance not deleted: $elements", elements == 1)
    }

    @Test
    fun deleteInstanceWithInfo() {
        db.beginTransaction()
        //Deleting instances should delete all related devices and info
        insertAndRead()
        db.deviceDao().insert(device)
        db.instanceDao().delete(instance)
        val devices = getValue(db.deviceDao().getDevicesByInstance(instance.id))
        val msList = getValue(db.managementServerDao().getAllMs())
        val dsList = getValue(db.deploymentServerDao().getAllDs())
        assertThat("Device under instance id ${instance.id} was not deleted automatically\n Devices: $devices",
                devices.isNullOrEmpty() && msList.isNullOrEmpty() && dsList.isNullOrEmpty())
        db.endTransaction()
    }
}
