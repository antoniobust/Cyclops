package com.mdmobile.cyclops.di

import com.mdmobile.cyclops.ui.logIn.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModules {
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeLoginActivity(): LoginActivity
}