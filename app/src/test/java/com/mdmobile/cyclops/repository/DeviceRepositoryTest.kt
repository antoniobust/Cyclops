package com.mdmobile.cyclops.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.api.utils.ApiUtil
import com.mdmobile.cyclops.commonTest.InstantAppExecutors
import com.mdmobile.cyclops.commonTest.TestUtils
import com.mdmobile.cyclops.dataModel.api.newDataClass.Device
import com.mdmobile.cyclops.db.DeviceDao
import com.mdmobile.cyclops.db.MobiControlDB
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class DeviceRepositoryTest {

    lateinit var repository: DeviceRepository
    private val dao = mock(DeviceDao::class.java)
    private val mcApiService = mock(McApiService::class.java)
    private val instance = TestUtils.createInstance()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(MobiControlDB::class.java)
        `when`(db.deviceDao()).thenReturn(dao)
        repository = DeviceRepository(instance, InstantAppExecutors(), mcApiService, dao)
    }

    @Test
    fun loadDeviceFromNetwork() {
        val dbData = MutableLiveData<Device>()
        `when`(dao.getDevice("foo")).thenReturn(dbData)

        val device = TestUtils.createDevice(instance)
        val apiCall = ApiUtil.successCall(device)

        `when`(mcApiService.getDevice("foo")).thenReturn(apiCall)

        val data = repository.loadDevice("foo")
        verify(dao).getDevice("foo")
        verifyNoMoreInteractions(dao)
    }

    @Test
    fun loadDeviceListFromNetwork() {
        val dbData = MutableLiveData<List<Device>>()
        `when`(dao.getDevices()).thenReturn(dbData)

        val deviceList = ArrayList<Device>(100)
        for (i in 0..100) {
            deviceList.add(TestUtils.createDevice(instance))
        }

        val apiCall = ApiUtil.successCall(deviceList.toList())
        `when`(mcApiService.getDevices()).thenReturn(apiCall)

        val data = repository.loadDevices()
        verify(dao).getDevicesByInstance(instance.id)
    }

}