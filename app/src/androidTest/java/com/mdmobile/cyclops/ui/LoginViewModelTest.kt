package com.mdmobile.cyclops.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mdmobile.cyclops.commonTest.InstantAppExecutors
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.ui.logIn.LoginViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class LoginViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(InstanceRepository::class.java)
    private val viewModel = LoginViewModel(repository, InstantAppExecutors())

    @Test
    fun testNullInstance() {
        verify(repository).loadAllInstances()
    }

}