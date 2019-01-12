package com.mdmobile.cyclops.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.mdmobile.cyclops.api.utils.ApiUtil
import com.mdmobile.cyclops.commonTest.LiveDataTestUtil
import com.mdmobile.cyclops.commonTest.TestUtils
import com.mdmobile.cyclops.dataModel.api.ErrorResponse
import com.mdmobile.cyclops.dataModel.api.RuntimeTypeAdapterFactory
import com.mdmobile.cyclops.dataModel.api.devices.*
import com.mdmobile.cyclops.dataTypes.DeviceKind
import com.mdmobile.cyclops.utils.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.bouncycastle.crypto.tls.ConnectionEnd.client
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class McApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var apiService:McApiService

    @Before
    fun init() {
        val typeFactory = RuntimeTypeAdapterFactory
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

        val gson = GsonBuilder()
                .registerTypeAdapterFactory(typeFactory)
                .setLenient()
                .create()


        apiService = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(McApiService::class.java)

    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun tokenResponseTest() {
        enqueueReq("Token.json")
        val token = apiService.getAuthToken()


    }


    private fun enqueueReq(resourceName: String, headers: Map<String, String> = emptyMap()) {
        val mockResponse = MockResponse()
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$resourceName")

        if (inputStream == null) {
            mockResponse.setBody("")
        } else {
            val inputBuffer = Okio.buffer(Okio.source(inputStream))
            mockResponse.setBody(inputBuffer.readString(Charsets.UTF_8))
        }
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse)
    }

}