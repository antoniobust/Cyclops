package com.mdmobile.cyclops.di

import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.api.TokenAuthenticator
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModules {

    @Provides
    @Singleton
    fun getMcService(serverInstance: Instance): McApiService {
        val client = OkHttpClient.Builder()
                .authenticator(TokenAuthenticator(
                        Retrofit.Builder()
                                .baseUrl(serverInstance.serverAddress)
                                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                                .build()
                                .create(McApiService::class.java))
                )
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .baseUrl(serverInstance.serverAddress)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .build()
                .create(McApiService::class.java)
    }
}