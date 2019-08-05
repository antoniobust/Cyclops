package com.mdmobile.cyclops.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mdmobile.cyclops.api.McApiService
import com.mdmobile.cyclops.api.McApiServiceHolder
import com.mdmobile.cyclops.api.TokenAuthenticator
import com.mdmobile.cyclops.dataModel.api.RuntimeTypeAdapterFactory
import com.mdmobile.cyclops.dataModel.api.devices.*
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataTypes.DeviceKind
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModules::class])
class NetModules(var instanceInfo: InstanceInfo) {
    @Provides
    @Singleton
    fun provideMcApiService(okHttpClient: OkHttpClient,
                            gSon: Gson, mcApiServiceHolder: McApiServiceHolder): McApiService {
        val retrofitService = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gSon))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .baseUrl(instanceInfo.serverAddress)
                .build()
                .create(McApiService::class.java)
        mcApiServiceHolder.mcApiService = retrofitService
        return retrofitService
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenAuthenticator: Authenticator): OkHttpClient {
        return OkHttpClient.Builder()
                .authenticator(tokenAuthenticator)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideMcApiServiceHolder(): McApiServiceHolder {
        return McApiServiceHolder()
    }


    @Provides
    @Singleton
    fun provideTokenAuthenticator(mcApiServiceHolder: McApiServiceHolder, instanceRepo:InstanceRepository): Authenticator {
        return TokenAuthenticator(mcApiServiceHolder,instanceRepo)
    }


    @Provides
    @Singleton
    fun provideRunTypeAdapterFactory(): RuntimeTypeAdapterFactory<BasicDevice> {
        return RuntimeTypeAdapterFactory
                .of(BasicDevice::class.java, "Kind")
                .registerSubtype(IosDevice::class.java, DeviceKind.IOS)
                .registerSubtype(IosDeviceV14::class.java, DeviceKind.IOS_V14)
                .registerSubtype(AndroidGeneric::class.java, DeviceKind.ANDROID_GENERIC)
                .registerSubtype(AndroidForWork::class.java, DeviceKind.ANDROID_FOR_WORK)
                .registerSubtype(AndroidPlus::class.java, DeviceKind.ANDROID_PLUS)
                .registerSubtype(SamsungElm::class.java, DeviceKind.ANDROID_ELM)
                .registerSubtype(WindowsDesktop::class.java, DeviceKind.WINDOWS_DESKTOP)
                .registerSubtype(WindowsDesktopLegacy::class.java, DeviceKind.WINDOWS_DESKTOP_LEGACY)
                .registerSubtype(WindowsPhone::class.java, DeviceKind.WINDOWS_PHONE)
                .registerSubtype(WindowsRuntime::class.java, DeviceKind.WINDOWS_RUNTIME)
                .registerSubtype(WindowsCE::class.java, DeviceKind.WINDOWS_CE)
                .registerSubtype(Linux::class.java, DeviceKind.LINUX)
                .registerSubtype(Mac::class.java, DeviceKind.MAC)
//            .registerSubtype(SamsungKnoxDevice.class, DeviceKind.ANDROID_KNOX)
    }

    @Provides
    @Singleton
    fun provideGsonConverter(typeFactory: RuntimeTypeAdapterFactory<BasicDevice>): Gson {
        return GsonBuilder()
                .registerTypeAdapterFactory(typeFactory)
                .setLenient()
                .create()
    }

}