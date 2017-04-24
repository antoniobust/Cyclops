package com.mdmobile.pocketconsole.ApiTest;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.VolleyLog;
import com.mdmobile.pocketconsole.ApiTest.TestClass.FakeApiManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class Authentication {

    @Test
    public void getAuthenticationToken() {
        FakeApiManager manager = new FakeApiManager(InstrumentationRegistry.getTargetContext());
        //TODO: type user name password instead ****
        String tokenResponse = manager.getApiToken("****", "*****");

        assertTrue("Error receiving token" ,!tokenResponse.equals("Error receiving token"));
    }
}
