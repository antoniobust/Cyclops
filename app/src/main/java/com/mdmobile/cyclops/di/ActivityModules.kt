package com.mdmobile.cyclops.di

import com.mdmobile.cyclops.ui.logIn.LoginActivity
import dagger.Module
import dagger.Subcomponent
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModules {
    @ContributesAndroidInjector(modules = [LoginActivtiyModule::class])
    abstract fun contributeLoginActivity(): LoginActivity
}