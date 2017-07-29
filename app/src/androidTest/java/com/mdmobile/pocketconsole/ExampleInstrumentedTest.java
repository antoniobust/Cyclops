package com.mdmobile.pocketconsole;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import com.mdmobile.pocketconsole.apiManager.api.ApiModel;
import com.mdmobile.pocketconsole.dataTypes.ComplexDataType;
import com.mdmobile.pocketconsole.dataTypes.DeviceAttributes;
import com.mdmobile.pocketconsole.dataTypes.ParameterKeys;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    String authority = "https://uk.mobicontrolcloud.com";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mdmobile.mobiconsole", appContext.getPackageName());
    }

    @Test
    public void createDevicesApi() {

        HashSet<Pair<String, String>> filter = new HashSet<>();
        HashSet<Pair<String, String>> order = new HashSet<>();
        String testReq =
                "https://uk.mobicontrolcloud.com/MobiControl/api/devices?skip=20&take=10&order=+osversion,-model&filter=manufacturer:Apple";

        //Create the filter parameter
        filter.add(new Pair<>(DeviceAttributes.BaseAttributes.Manufacturer, "Apple"));
        //Create order parameter
        order.add(new Pair<>("+", DeviceAttributes.BaseAttributes.OSVersion));
        order.add(new Pair<>("-", DeviceAttributes.BaseAttributes.Model));


        String apiReq =
                ApiModel.DevicesApi.Builder(authority).skip(20).take(10).order(order).filter(filter).build();
        assertTrue("\nUrl created does not match with the test one: " + apiReq, apiReq.equalsIgnoreCase(testReq));

        //test api with user filter
        testReq = "https://uk.mobicontrolcloud.com/MobiControl/api/devices?userFilter=UserName:acalabrese";
        Pair<String, String> userFilter = new Pair<>(ParameterKeys.UserName.toString(), "acalabrese");
        apiReq = ApiModel.DevicesApi.Builder(authority).addUserFilter(userFilter).build();

        assertTrue("Url created does not match with the test one: " + apiReq, apiReq.equals(testReq));
    }

    @Test
    public void createSingleDeviceApi() {
        String testReq =
                "https://uk.mobicontrolcloud.com/MobiControl/api/devices/AndroidPlus0001";

        String apiReq = ApiModel.DevicesApi.Builder(authority, "AndroidPlus0001").build();
        String msg = "\n" + this.getClass().getSimpleName() + "\nUrl created does not match with the test one: ";
        assertTrue(msg + apiReq, apiReq.equals(testReq));
    }

    @Test
    public void getAllCustomData(){

        String testReq = "https://uk.mobicontrolcloud.com/MobiControl/api/devices/" +
                "collectedData?startDate=2015-12-19T16%3A39%3A57-02%3A00&endDate=2015-12-19T16%3A39%3A57-02%3A00" +
                "&builtInDataType=SuccessCalls&skip=10&take=50";


        String apiReq = ApiModel.DevicesApi.Builder(authority)
                .getCollectedData("2015-12-19T16:39:57-02:00","2015-12-19T16:39:57-02:00", ComplexDataType.BuiltInDataType.SuccessCalls,null)
                .skip(10).take(50).build();

        String msg = "\n" + this.getClass().getSimpleName() + "\nUrl created does not match with the test one: ";

        assertTrue(msg + apiReq, apiReq.equals(testReq));

    }

    @Test
    public void getCustomDataForDeviceAPi() {
        String testReq = "https://uk.mobicontrolcloud.com/MobiControl/api/devices/" +
                "AndroidPlus0001/collectedData?startDate=2015-12-19T16%3A39%3A57-02%3A00&endDate=2015-12-19T16%3A39%3A57-02%3A00";


        String startDate = "2015-12-19T16:39:57-02:00";
        String endDate = "2015-12-19T16:39:57-02:00";
        String devId = "AndroidPlus0001";
        String msg = "\n" + this.getClass().getSimpleName() + "\nUrl created does not match with the test one: ";


        String apiReq = ApiModel.DevicesApi.Builder(authority, devId)
                .getCollectedData(startDate, endDate, null, null).build();


        assertTrue(msg + apiReq, apiReq.equals(testReq));
    }

    @Test
    public void createGroupsApi() {

    }

    @Test
    public void createServerApi() {

    }
}
