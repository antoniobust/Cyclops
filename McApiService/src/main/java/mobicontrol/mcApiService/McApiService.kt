package mobicontrol.mcApiService

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import mobicontrol.mcApiService.api.McApiServiceHolder
import mobicontrol.mcApiService.api.TokenAuthenticator
import mobicontrol.mcApiService.dataModel.InstanceInfo
import mobicontrol.mcApiService.dataTypes.DeviceKind
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.crypto.Mac


class McApiService(private val instanceInfo: InstanceInfo) {
    private val okHttpClient:OkHttpClient

    constructor( instanceInfo: InstanceInfo, okHttpClient: OkHttpClient): this(instanceInfo){

    }
    init {
        okHttpClient = provideOkHttpClient(provideTokenAuthenticator())
    }


    init {
        okHttpClient
    }

    val service:McApiService = Retrofit.Builder()
            .baseUrl(instanceInfo.managementServerAddress)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(McApiService::class.java)


    fun provideMcApiService(okHttpClient: OkHttpClient,
                            gSon: Gson, mcApiServiceHolder: McApiServiceHolder): McApiService {
        val retrofitService = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gSon))
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
//                .baseUrl(instanceInfo.serverAddress)
                .build()
                .create(McApiService::class.java)
//        mcApiServiceHolder.mcApiService = retrofitService
        return retrofitService
    }

    fun provideOkHttpClient(tokenAuthenticator: Authenticator): OkHttpClient {
        return OkHttpClient.Builder()
                .authenticator(tokenAuthenticator)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }


    fun provideMcApiServiceHolder(): McApiServiceHolder {
        return McApiServiceHolder()
    }


    fun provideTokenAuthenticator(): Authenticator {
        return TokenAuthenticator()
    }

//    fun provideRunTypeAdapterFactory(): RuntimeTypeAdapterFactory<BasicDevice> {
//        return RuntimeTypeAdapterFactory
//                .of(BasicDevice::class.java, "Kind")
//                .registerSubtype(IosDevice::class.java, DeviceKind.IOS)
//                .registerSubtype(IosDeviceV14::class.java, DeviceKind.IOS_V14)
//                .registerSubtype(AndroidGeneric::class.java, DeviceKind.ANDROID_GENERIC)
//                .registerSubtype(AndroidForWork::class.java, DeviceKind.ANDROID_FOR_WORK)
//                .registerSubtype(AndroidPlus::class.java, DeviceKind.ANDROID_PLUS)
//                .registerSubtype(SamsungElm::class.java, DeviceKind.ANDROID_ELM)
//                .registerSubtype(WindowsDesktop::class.java, DeviceKind.WINDOWS_DESKTOP)
//                .registerSubtype(WindowsDesktopLegacy::class.java, DeviceKind.WINDOWS_DESKTOP_LEGACY)
//                .registerSubtype(WindowsPhone::class.java, DeviceKind.WINDOWS_PHONE)
//                .registerSubtype(WindowsRuntime::class.java, DeviceKind.WINDOWS_RUNTIME)
//                .registerSubtype(WindowsCE::class.java, DeviceKind.WINDOWS_CE)
//                .registerSubtype(Linux::class.java, DeviceKind.LINUX)
//                .registerSubtype(Mac::class.java, DeviceKind.MAC)
////            .registerSubtype(SamsungKnoxDevice.class, DeviceKind.ANDROID_KNOX)
//    }
//
//    @Provides
//    @Singleton
//    fun provideGsonConverter(typeFactory: RuntimeTypeAdapterFactory<BasicDevice>): Gson {
//        return GsonBuilder()
//                .registerTypeAdapterFactory(typeFactory)
//                .setLenient()
//                .create()
//    }
}
