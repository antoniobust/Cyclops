package com.mdmobile.cyclops.api.db.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mdmobile.cyclops.dataModel.api.newDataClass.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class TestUtils {
    companion object {
        fun createInstance() = InstanceInfo(
                190,
                "TestInstance",
                "xxxx",
                "zzzz",
                "https://address",
                13,
                4040)


        fun createDevice() = Device(
                Random.nextInt(), "Android Plus", Random.nextInt().toString(), "TestDevice",
                "Sunday", "Android", "TestHostname", "MAC:ADDR",
                "OnePlus", "4", "6t", "9.0", "\\Anto",
                true, true, false, "Android",
                "ExtraInfo;ExtraInfo2", createInstance().id)

        fun createScript() = Script(
                123, "Testing script", "Hello my name is doc. greenthumb", "reset -a \n reset -b"
        )

        fun createDeploymentServer() = DeploymentServer(
                111, "10.10.10.10", "1.1.1.1",
                "2.2.2.2", "3.3.3.3.3", "123.123.123.123",
                "tesetDs", "online", true, 12, 1, 12,
                12, 12, 21, 32, 43,
                345, 65, 234, createInstance().id)

        fun createManagementServer() = ManagementServer(
                12314, "testMsFqdn", "This is a test MS", "1232", "12:we:123:123",
                "TestManagement", "online", 443, 123, createInstance().id)

        fun <T> getValue(liveData: LiveData<T>): T? {

            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(o: T?) {
                    data[0] = o
                    latch.countDown()
                    liveData.removeObserver(this)
                }
            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            @Suppress("UNCHECKED_CAST")
            return data[0] as T
        }
    }
}