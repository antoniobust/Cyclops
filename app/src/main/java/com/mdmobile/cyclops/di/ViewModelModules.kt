package com.mdmobile.cyclops.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdmobile.cyclops.ui.logIn.LoginViewModel
import com.mdmobile.cyclops.viewModel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModules {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}