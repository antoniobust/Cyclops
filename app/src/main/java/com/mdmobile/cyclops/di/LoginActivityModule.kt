package com.mdmobile.cyclops.di

import android.app.Application
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.ui.logIn.LoginViewModel
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule {
    @Provides
    fun provideLogInActivityViewModel(repository:InstanceRepository): LoginViewModel {
        return LoginViewModel(repository)
    }
}
