package com.mdmobile.cyclops.commonTest

import com.mdmobile.cyclops.dataModel.api.newDataClass.*
import kotlin.math.absoluteValue
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


        fun createDevice(instance: InstanceInfo) = Device(
                Random.nextInt().absoluteValue, "Android Plus", Random.nextInt().toString(), "TestDevice",
                "Sunday", "Android", "TestHostname", "MAC:ADDR",
                "OnePlus", "4", "6t", "9.0", "\\Anto",
                true, true, false, "Android",
                "ExtraInfo;ExtraInfo2", instance.id)

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

        fun createInstalledApp(device: Device) = InstalledApp(12121, device.deviceId, "com.test.cyclops", "CyclopsTest", "Installed")

        fun createProfile() = Profile(Random.nextInt().absoluteValue, Random.nextInt().absoluteValue.toString(), "TEST PROFILE", "Installed", "20/20/1901", 11, true)

        fun createProfileList(a: Int = 5): List<Profile> {
            val list = ArrayList<Profile>()
            for (i in 0..a) {
                list.add(createProfile())
            }
            return list
        }
    }
}