package com.mdmobile.cyclops.di

import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.ui.logIn.LoginViewModel
import dagger.Module
import dagger.Provides


@Module
class LoginActivtiyModule {
    @Provides
    fun provideLogInActivityViewModel(repository:InstanceRepository): LoginViewModel {
        return LoginViewModel(repository)
    }
}
