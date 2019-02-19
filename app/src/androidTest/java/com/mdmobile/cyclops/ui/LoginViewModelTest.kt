package com.mdmobile.cyclops.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mdmobile.cyclops.commonTest.LiveDataTestUtil
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.ui.logIn.LoginViewModel
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class LoginViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(InstanceRepository::class.java)
    private val viewModel = LoginViewModel(repository)

    @Test
    fun testEmptyInitLoad() {
        verify(repository).loadAllInstances()
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instance)
        val userLiveData = LiveDataTestUtil.getValue(viewModel.user)
        verifyNoMoreInteractions(repository)
        MatcherAssert.assertThat("Instance should be the default one",
                instanceLiveData?.apiSecret == "N/A")
        MatcherAssert.assertThat("User should be the default one",
                userLiveData?.password == "N/A" && userLiveData.userName == "N/A")

        val duplicate = LiveDataTestUtil.getValue(viewModel.isDuplicate)
        MatcherAssert.assertThat("IsDuplicate value should be false at this point", duplicate == false)
        val token = LiveDataTestUtil.getValue(viewModel.token)
        MatcherAssert.assertThat("token should be empty at this point", token?.token.isNullOrEmpty())
    }

    @Test
    fun updateInstanceInfoTest() {
        viewModel.updateApiSecret("TestApiSecret")
        viewModel.updateClientId("TestClientId")
        viewModel.updateInstanceAddress("TestAddress")
        viewModel.updateInstanceName("TestInstance")
        val instanceInfo = LiveDataTestUtil.getValue(viewModel.instance)
        MatcherAssert.assertThat("Instance didn't update with test values",
                instanceInfo?.apiSecret == "TestApiSecret" &&
                        instanceInfo.clientId == "TestClientId" &&
                        instanceInfo.serverName == "TestInstance" &&
                        instanceInfo.serverAddress == "TestAddress")
    }

    @Test
    fun updateUserInfoTest() {
        viewModel.updateUserName("TestUser")
        viewModel.updatePassword("TestPassword")
        val userInfo = LiveDataTestUtil.getValue(viewModel.user)
        MatcherAssert.assertThat("User did not update values correctly $userInfo", userInfo?.password == "TestPassword" &&
                userInfo.userName == "TestUser")
    }

    @Test
    fun loginTest() {
        updateInstanceInfoTest()
        updateUserInfoTest()
        val apiResponse = MutableLiveData<Resource<InstanceInfo>>() as LiveData<Resource<InstanceInfo>>
        `when`(repository.getToken()).thenReturn(apiResponse)
        viewModel.logIn()
        verify(repository).getToken()
    }

    @Test
    fun updateServerInfo(){
        val testApiSecret = "newApiSecret"
        viewModel.updateApiSecret(testApiSecret)
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instance)
        MatcherAssert.assertThat("Api secret doesn't match test value $instanceLiveData",
                instanceLiveData?.apiSecret == instanceLiveData?.apiSecret)
    }

}