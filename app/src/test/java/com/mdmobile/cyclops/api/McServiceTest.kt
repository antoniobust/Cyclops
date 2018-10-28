package com.mdmobile.cyclops.api

import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.di.ApplicationModules
import junit.framework.Assert.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.charset.Charset

@RunWith(JUnit4::class)
class McServiceTest {

    private lateinit var apiService: McApiService
    private lateinit var mockServer: MockWebServer


    @Before
    fun getApiService() {
        mockServer = MockWebServer()
        val mcInstance = Instance(mockServer.hostName, "testSecret", "TestClient",
                "https://" + mockServer.hostName + "/MobiControl/", 13, 0)
        apiService = ApplicationModules().getMcService(mcInstance)
    }

    @After
    fun stopMockServer() {
        mockServer.shutdown()
    }

    @Test
    fun getDevices() {
        enqueueResponse("deviceList.json")
        val request = mockServer.takeRequest()
        assertTrue("Wrong device request URL: ${request.path}", request.path == "/devices")
    }


    private fun enqueueResponse(fileName: String) {
        val responseStream = javaClass.classLoader?.getResourceAsStream("apiResponse/$fileName")
        val source = Okio.buffer(Okio.source(responseStream))
        val mockResponse = MockResponse()

        mockResponse.setBody(source.readString(Charset.defaultCharset()))
        mockServer.enqueue(mockResponse)
    }

}