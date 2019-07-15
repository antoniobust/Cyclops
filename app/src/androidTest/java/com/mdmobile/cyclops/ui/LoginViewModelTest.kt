package com.mdmobile.cyclops.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mdmobile.cyclops.commonTest.LiveDataTestUtil
import com.mdmobile.cyclops.commonTest.mock
import com.mdmobile.cyclops.dataModel.User
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.ui.logIn.LoginViewModel
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyZeroInteractions

class LoginViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(InstanceRepository::class.java)
    private var viewModel = LoginViewModel(repository)

    @Before
    fun initialize() {
        val observer1 = mock<Observer<InstanceInfo>>()
        val observer2 = mock<Observer<User>>()
        viewModel.instanceInfo.observeForever(observer1)
        viewModel.user.observeForever(observer2)
    }

    @Test
    fun testEmptyInitLoad() {
        verifyZeroInteractions(repository)
        MatcherAssert.assertThat("Live data don't have active observers after initialization",
                viewModel.apiSecret.hasActiveObservers() && viewModel.clientId.hasActiveObservers() &&
                        viewModel.instanceAddress.hasActiveObservers() && viewModel.apiSecret.hasActiveObservers()
                        && viewModel.instanceName.hasActiveObservers() && viewModel.userName.hasActiveObservers()
                        && viewModel.password.hasActiveObservers())
        MatcherAssert.assertThat("Instance Live data manager doesn't have active listeners", viewModel.instanceInfo.hasObservers())
        MatcherAssert.assertThat("User Live data manager doesn't have active listeners", viewModel.user.hasObservers())

    }

    @Test
    fun updateInstanceNameTest() {
        viewModel.updateInstanceName("InstanceTest")
        val instanceInfo = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Instance name should be updated with test value: ${viewModel.instanceName}",
                viewModel.instanceName.value == "InstanceTest")
        MatcherAssert.assertThat("Instance info should be updated: $instanceInfo",
                instanceInfo?.instanceName == "InstanceTest")
    }

    @Test
    fun updateInstanceAddressTest() {
        viewModel.updateInstanceAddress("https://InstanceTest.com/Mobicontrol/")
        val instanceInfo = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Instance address should be updated with test value: ${viewModel.instanceAddress}",
                viewModel.instanceAddress.value == "https://InstanceTest.com/Mobicontrol/")
        MatcherAssert.assertThat("Instance info should be updated: ${instanceInfo?.serverAddress}",
                instanceInfo?.serverAddress == "https://InstanceTest.com/Mobicontrol/")
    }

    @Test
    fun updateClientIdTest() {
        val testApiSecret = "testClientID"
        viewModel.updateClientId(testApiSecret)
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Api secret doesn't match test value $instanceLiveData",
                instanceLiveData?.clientId == instanceLiveData?.clientId)
    }

    @Test
    fun updateApiSecret() {
        val testApiSecret = "newApiSecret"
        viewModel.updateApiSecret(testApiSecret)
        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instanceInfo)
        MatcherAssert.assertThat("Api secret doesn't match test value $instanceLiveData",
                instanceLiveData?.apiSecret == instanceLiveData?.apiSecret)
    }

    @Test
    fun updateUserName() {
        val userTest = User("TestUSer", "testPassword")
        viewModel.updateUserName(userTest.userName)
        val userLiveData = LiveDataTestUtil.getValue(viewModel.user)
        MatcherAssert.assertThat("User name didn't update with test value",
                userLiveData?.userName == userTest.userName)
        MatcherAssert.assertThat("User name doesn't match test value $userLiveData",
                userLiveData?.userName == userLiveData?.userName)
    }

    @Test
    fun updateUserPassword() {
        val userTest = User("TestUSer", "testPassword")
        viewModel.updatePassword(userTest.password)
        val userLiveData = LiveDataTestUtil.getValue(viewModel.user)
        MatcherAssert.assertThat("Password didn't update with test value",
                userLiveData?.password == userTest.password)
        MatcherAssert.assertThat("User name doesn't match test value $userLiveData",
                userLiveData?.userName == userLiveData?.userName)
    }

//    @Test
//    fun loginTest() {
//        updateClientIdTest()
//        updateApiSecret()
//        val instanceLiveData = LiveDataTestUtil.getValue(viewModel.instanceInfo)
//        val token = Token("testToken","testBearer",100,"testRefresh")
//        val resourceTokenLiveData = MutableLiveData<Resource<Token>>()
//        resourceTokenLiveData.value  = Resource.success(token)
//        val apiResponse = resourceTokenLiveData as LiveData<Resource<Token>>
//        `when`(repository.getToken(com.mdmobile.cyclops.commonTest.any(InstanceInfo::class.java)))
//                .thenReturn(apiResponse)
//        viewModel.getServerInfo()
//        val tokenLiveData = LiveDataTestUtil.getValue(viewModel.token)
//        verify(repository).getToken(com.mdmobile.cyclops.commonTest.any(InstanceInfo::class.java))
//        MatcherAssert.assertThat("No token found $instanceLiveData",
//                tokenLiveData?.data?.token == "testToken" )
//    }
}