package com.mdmobile.cyclops.di

import com.mdmobile.cyclops.api.McApiService
import dagger.Component

@Component(modules = [ApplicationModules::class])
interface AppComponent {
    fun inject(mcApiService: McApiService)
}