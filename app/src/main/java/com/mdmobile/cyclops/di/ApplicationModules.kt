package com.mdmobile.cyclops.di

import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApplicationModules() {

    @Provides
    @Singleton
    fun getMcService(): McApiService {
        return Retrofit.Builder()
                .baseUrl("test")
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(McApiService::class.java)
    }
}