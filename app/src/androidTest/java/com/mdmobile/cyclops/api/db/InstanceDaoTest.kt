package com.mdmobile.cyclops.api.db

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.runner.AndroidJUnit4
import com.mdmobile.cyclops.api.db.util.TestUtils.Companion.getValue
import com.mdmobile.cyclops.api.db.util.TestUtils
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.db.MobiControlDB
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class InstanceDaoTest : DbTest() {

    private val instance = TestUtils.createInstance()
    private val device = TestUtils.createDevice(instance)

    @Test
    fun insertAndRead() {
        val newItem = db.instanceDao().insert(instance)
        assertThat("New instance id doesn't match the test one $newItem", newItem.toInt() == 190)
        val newInstance = TestUtils.getValue(db.instanceDao().getInstance(instance.id.toString()))
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
    fun deleteInstanceWithInfo(){
        db.beginTransaction()
        //Deleting instances should delete all related devices and info
        insertAndRead()
        db.deviceDao().insert(device)
        db.instanceDao().delete(instance)
        val devices = getValue(db.deviceDao().getDevicesByInstanceId(instance.id.toString()))
        val msList = getValue(db.managementServerDao().getAllMs())
        val dsList = getValue(db.deploymentServerDao().getAllDs())
        assertThat("Device under instance id ${instance.id} was not deleted automatically\n Devices: $devices",
                devices.isNullOrEmpty() && msList.isNullOrEmpty() && dsList.isNullOrEmpty())
        db.endTransaction()
    }
}
