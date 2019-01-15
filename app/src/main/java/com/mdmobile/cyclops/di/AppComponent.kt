package com.mdmobile.cyclops.di

import android.app.Application
import android.content.Context
import com.mdmobile.cyclops.CyclopsApplication
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.ui.logIn.LoginActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModules::class,
    ViewModelModules::class,
    ActivityModules::class])
interface AppComponent {

    fun inject(cyclopsApp: CyclopsApplication)
    fun inject(context: Context)
    fun inject(loginActivity: LoginActivity)
    fun inject(mcApiService: McApiService)
    fun inject(instanceRepository: InstanceRepository)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}