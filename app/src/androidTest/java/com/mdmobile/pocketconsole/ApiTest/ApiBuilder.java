package com.mdmobile.pocketconsole.ApiTest;

import android.support.test.runner.AndroidJUnit4;

import com.mdmobile.pocketconsole.apiManager.api.ApiModels;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;


/**
 * Test the api builder methods
 */

@RunWith(AndroidJUnit4.class)
public class ApiBuilder {

    private String devID = "my-fake-device-id-12:23:32:00";
    private String serverAuthority = "https://uk.mobicontrolcloud.com";


    @Before
    public void setUp() {
    }

    @Test
    public void devicesApis() {
        //All devices api
        String api = ApiModels.DevicesApi.Builder(serverAuthority).build();
        String expectedApi = serverAuthority.concat("/MobiControl/api/devices");
        assertTrue("URL returned: " + api, api.equals(expectedApi));

        //device installed Apps API
        api = ApiModels.DevicesApi.Builder(serverAuthority, devID).getInstalledApplications().build();
        expectedApi = serverAuthority.concat("/MobiControl/api/devices/").concat(devID).concat("/installedApplications");
        assertTrue("URL returned: " + api + "\n URL expected: " + expectedApi, api.equals(expectedApi));


        //CheckIn action api
        api = ApiModels.DevicesApi.Builder(serverAuthority,devID).actionRequest().build();
        expectedApi = serverAuthority.concat("/MobiControl/api/devices/").concat(devID).concat("/actions");
        assertTrue("URL returned: " + api + "\n URL expected: " + expectedApi, api.equals(expectedApi));


    }

}
