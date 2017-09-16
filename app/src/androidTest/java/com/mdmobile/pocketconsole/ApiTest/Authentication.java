package com.mdmobile.pocketconsole.ApiTest;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mdmobile.pocketconsole.ApiTest.TestClass.FakeApiManager;
import com.mdmobile.pocketconsole.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class Authentication {

    @Test
    public void getAuthenticationToken() {
        FakeApiManager manager = new FakeApiManager(InstrumentationRegistry.getTargetContext());
        String userName = InstrumentationRegistry.getTargetContext().getString(R.string.mc_user_name);
        String password = InstrumentationRegistry.getTargetContext().getString(R.string.mc_password);
        String tokenResponse = manager.getApiToken(userName, password);

        assertTrue("Error receiving token" ,!tokenResponse.equals("Error receiving token"));
    }
}
