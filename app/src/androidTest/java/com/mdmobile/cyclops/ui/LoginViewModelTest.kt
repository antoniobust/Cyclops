package com.mdmobile.cyclops.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mdmobile.cyclops.commonTest.LiveDataTestUtil
import com.mdmobile.cyclops.commonTest.mock
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
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
    private var viewModel = LoginViewModel(repository)

    @Test
    fun testEmptyInitLoad() {
        verifyZeroInteractions(repository)
    }

    @Test
    fun updateInstanceNameTest(){
        val result = mock<Observer<InstanceInfo>>()
        viewModel.instanceInfo.observeForever(result)
        viewModel.instanceMediatorLiveData.observeForever(result)
        MatcherAssert.assertThat("Instance info should have registered observers", viewModel.instanceInfo.hasActiveObservers())
        viewModel.updateInstanceName("InstanceTest")
        val instanceInfo = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Instance name should be updated with test value: ${viewModel.instanceName}",
                viewModel.instanceName.value   == "InstanceTest")
        MatcherAssert.assertThat("Instance info should be updated: $instanceInfo",
                instanceInfo?.serverName  == "InstanceTest")
    }

    @Test
    fun updateInstanceAddressTest(){
        val result = mock<Observer<InstanceInfo>>()
        viewModel.instanceInfo.observeForever(result)
        viewModel.instanceMediatorLiveData.observeForever(result)
        MatcherAssert.assertThat("Instance info should have registered observers", viewModel.instanceInfo.hasActiveObservers())
        viewModel.updateInstanceAddress("https://InstanceTest.com")
        val instanceInfo = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Instance address should be updated with test value: ${viewModel.instanceAddress}",
                viewModel.instanceAddress.value   == "https://InstanceTest.com")
        MatcherAssert.assertThat("Instance info should be updated: $instanceInfo",
                instanceInfo?.serverAddress  == "https://InstanceTest.com")
    }

    @Test
    fun updateClientIdTest() {
        val result = mock<Observer<InstanceInfo>>()
        viewModel.instanceInfo.observeForever(result)
        viewModel.instanceMediatorLiveData.observeForever(result)
        MatcherAssert.assertThat("Instance info should have registered observers", viewModel.instanceInfo.hasActiveObservers())
        val testApiSecret = "testClientID"
        viewModel.updateClientId(testApiSecret)
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Api secret doesn't match test value $instanceLiveData",
                instanceLiveData?.clientId == instanceLiveData?.clientId)
    }
    @Test
    fun updateApiSecret() {
        val result = mock<Observer<InstanceInfo>>()
        viewModel.instanceInfo.observeForever(result)
        viewModel.instanceMediatorLiveData.observeForever(result)
        MatcherAssert.assertThat("Instance info should have registered observers", viewModel.instanceInfo.hasActiveObservers())
        val testApiSecret = "newApiSecret"
        viewModel.updateApiSecret(testApiSecret)
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Api secret doesn't match test value $instanceLiveData",
                instanceLiveData?.apiSecret == instanceLiveData?.apiSecret)
    }

    @Test
    fun updateUserName(){
        val result = mock<Observer<LoginViewModel.User>>()
        viewModel.user.observeForever(result)
        viewModel.userMediatorLiveData.observeForever(result)
        MatcherAssert.assertThat("User live data should have a registered observer", viewModel.user.hasActiveObservers())
        val userTest = LoginViewModel.User("TestUSer","testPassword")
        viewModel.updateUserName(userTest.userName)
        val userLiveData = LiveDataTestUtil.getValue(viewModel.user)
        MatcherAssert.assertThat("User name doesn't match test value $userLiveData",
                userLiveData?.userName == userLiveData?.userName)
    }

    @Test
    fun updateUserPassword(){
        val result = mock<Observer<LoginViewModel.User>>()
        viewModel.user.observeForever(result)
        viewModel.userMediatorLiveData.observeForever(result)
        MatcherAssert.assertThat("User live data should have a registered observer", viewModel.user.hasActiveObservers())
        val userTest = LoginViewModel.User("TestUSer","testPassword")
        viewModel.updateUserName(userTest.userName)
        val userLiveData = LiveDataTestUtil.getValue(viewModel.user)
        MatcherAssert.assertThat("User name doesn't match test value $userLiveData",
                userLiveData?.userName == userLiveData?.userName)
    }

    @Test
    fun loginTest() {
        updateClientIdTest()
        updateApiSecret()
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        val token = Token("testToken","testBearer",100,"testRefresh")
        val resourceTokenLiveData = MutableLiveData<Resource<Token>>()
        resourceTokenLiveData.value  = Resource.success(token)
        val apiResponse = resourceTokenLiveData as LiveData<Resource<Token>>
        `when`(repository.getToken(com.mdmobile.cyclops.commonTest.any(InstanceInfo::class.java)))
                .thenReturn(apiResponse)
        viewModel.logIn()
        val tokenLiveData = LiveDataTestUtil.getValue(viewModel.token)
        verify(repository).getToken(com.mdmobile.cyclops.commonTest.any(InstanceInfo::class.java))
        MatcherAssert.assertThat("No token found $instanceLiveData",
                tokenLiveData?.data?.token == "testToken" )
    }
}