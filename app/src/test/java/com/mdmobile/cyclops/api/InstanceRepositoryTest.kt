package com.mdmobile.cyclops.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mdmobile.cyclops.api.utils.InstantAppExecutors
import com.mdmobile.cyclops.commonTest.TestUtils
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.db.InstanceDao
import com.mdmobile.cyclops.db.MobiControlDB
import com.mdmobile.cyclops.repository.InstanceRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class InstanceRepositoryTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var repository: InstanceRepository
    private val instance = TestUtils.createInstance()
    private val instanceDao = mock(InstanceDao::class.java)
    private val mcApiService = mock(McApiService::class.java)

    @Before
    fun init() {
        val db = mock(MobiControlDB::class.java)
        `when`(db.instanceDao()).thenReturn(instanceDao)
        repository = InstanceRepository(instance, mcApiService, InstantAppExecutors())
    }

    @Test
    fun loadInstanceInfo() {
        val dbData = MutableLiveData<List<InstanceInfo>>()
        `when`(instanceDao.getAllInstances()).thenReturn(dbData)


        val data = instanceDao.getInstanceById(-1)
        verify(instanceDao).getInstanceById(-1)
        verifyNoMoreInteractions(instanceDao)

    }
}