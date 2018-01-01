package com.mdmobile.pocketconsole.dbTesting;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.dataModels.api.Profile;
import com.mdmobile.pocketconsole.dataModels.api.RuntimeTypeAdapterFactory;
import com.mdmobile.pocketconsole.dataModels.api.ServerInfo;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidForWorkDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidGenericDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidPlusDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.BasicDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.IosDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungElmDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungKnoxDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsCE;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsDesktop;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsDesktopLegacy;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsPhone;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsRuntime;
import com.mdmobile.pocketconsole.dataTypes.ComplexDataType;
import com.mdmobile.pocketconsole.fakeData.FakeJSON;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.provider.McHelper;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Test db data manipulation
 */

@RunWith(AndroidJUnit4.class)
public class DbData extends AndroidJUnitRunner {

    @BeforeClass
    public static void setUp() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(McHelper.DB_NAME);
        SQLiteDatabase db = new McHelper(InstrumentationRegistry.getTargetContext()).getWritableDatabase();
        assertTrue("DB didn't open", db.isOpen());
        db.close();

    }


    @Test
    public void InsertDevice() {
        createSingleNewDevice();
        createNewDevicesBulkImport();
    }

    @Test
    public void DeleteDevice() {
        createNewDevicesBulkImport();
        Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(McContract.Device.CONTENT_URI, null, null, null, null);
        assertTrue(c != null);
        c.moveToFirst();
        String devId = c.getString(c.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));
        int deleted = InstrumentationRegistry.getTargetContext().getContentResolver().delete(McContract.Device.buildUriWithDeviceID(devId), null, null);
        c.close();
        assertTrue("Device (" + devId + ") not deleted from DB ", deleted == 1);
    }

    @Test
    public void DeviceSelection() {
        createNewDevicesBulkImport();
        Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(McContract.Device.CONTENT_URI, null, null, null, null);
        assertTrue("No device found", c != null && c.moveToNext());
        int count = 0;
        HashSet<String> IDs = new HashSet<>(c.getCount());
        while (!c.isLast()) {
            count++;
            IDs.add(c.getString(c.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID)));
            c.moveToNext();
        }
        c.close();
        assertTrue("Device count: " + count, count > 1);
        assertTrue("Some IDs are duplicated" + IDs.toString(), IDs.size() == count);
    }

    @Test
    public void managementServer() {
        insertMs();
        Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(McContract.MsInfo.CONTENT_URI, null, null, null, null);
        assertNotNull("Null cursor returned for MS ", c);
        assertTrue("No MS info found", c.moveToFirst());

//        int deleted = InstrumentationRegistry.getTargetContext().getContentResolver().delete(McContract.MsInfo.CONTENT_URI, null, null);
//        assertTrue("MS not deleted properly, deleted: " + deleted + " records", deleted > 0);
    }

    @Test
    public void deploymentServer() {
        insertDs();
        Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(McContract.DsInfo.CONTENT_URI, null, null, null, null);
        assertNotNull("Null cursor returned for DS ", c);
        assertTrue("No DS info found", c.moveToFirst());

//        int deleted = InstrumentationRegistry.getTargetContext().getContentResolver().delete(McContract.DsInfo.CONTENT_URI, null, null);
//        assertTrue("DS not deleted properly, deleted: " + deleted + " records", deleted > 0);
    }

    @Test
    public void TestStandardScript() {

        Cursor c = InstrumentationRegistry.getContext().getContentResolver().query(McContract.Script.CONTENT_URI, null, null, null, null);
        assertTrue("No script found", c != null && c.moveToFirst());
        String[] scripts = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.default_script_titles);
        HashSet<String> titleSet = new HashSet<>(Arrays.asList(scripts));

        int initialCount = titleSet.size();
        assertTrue("Saved scripts do not match the standard ones", initialCount == c.getCount());

        do {
            titleSet.add(c.getString(c.getColumnIndex(McContract.Script.TITLE)));
            c.moveToNext();
        } while (c.isLast());
        c.close();

        assertTrue("Additional scripts found in DB" + titleSet.toString(), titleSet.size() == initialCount);
    }

    @Test
    public void TestProfile() {
        ArrayList<Profile> profiles = getProfilesFromJson();
        assertTrue("Error parsing profiles", profiles != null && !profiles.isEmpty());

        InsertDevice();
        ContentValues[] val = com.mdmobile.pocketconsole.utils.DbData.prepareProfilesValue(profiles);
        Cursor devices = InstrumentationRegistry.getTargetContext().getContentResolver().query(McContract.Device.CONTENT_URI, null, null, null, null);
        devices.moveToFirst();
        String devId = devices.getString(devices.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID));

        Uri uri = InstrumentationRegistry.getContext().getContentResolver().insert(McContract.Profile.buildUriWithDeviceId(devId), val[0]);
        assertNotNull("Error inserting profile, uri is null", uri);


        String profileId = profiles.get(0).getReferenceId();

        assertTrue("Profile id: " + profileId + " has not been deleted", InstrumentationRegistry.getTargetContext()
                .getContentResolver().delete(McContract.Profile.buildUriWithID(profileId), null, null) == 1);

    }


    private void createSingleNewDevice() {
        ArrayList<BasicDevice> devicesArray = getDeviceFromJson();

        ContentValues deviceValues = com.mdmobile.pocketconsole.utils.DbData.prepareDeviceValues(devicesArray.get(0));

        Uri newDev = InstrumentationRegistry.getContext().getContentResolver().insert(McContract.Device.CONTENT_URI, deviceValues);
        assertNotNull("Device was not created", newDev);
        String id = McContract.Device.getDeviceIdFromUri(newDev);
        assertNotNull(id);
        assertFalse("New device id is not valid", id.equals("-1"));
    }

    private void createNewDevicesBulkImport() {
        ArrayList<BasicDevice> devicesArray = getDeviceFromJson();
        int newDev = -1;

        if (devicesArray.size() > 1) {
            ContentValues[] devicesValues = com.mdmobile.pocketconsole.utils.DbData.prepareDeviceValues(devicesArray);
            newDev = InstrumentationRegistry.getTargetContext().getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
        }
        assertFalse("Not all devices have been inserted correctly", newDev == 0);
    }


    private ArrayList<Profile> getProfilesFromJson() {
        Type type = new TypeToken<List<Profile>>() {
        }.getType();

        Gson gson = new Gson();
        return gson.fromJson(FakeJSON.profileJson, type);
    }

    private ArrayList<BasicDevice> getDeviceFromJson() {
        Type deviceCollectionType = new TypeToken<ArrayList<BasicDevice>>() {
        }.getType();

        final RuntimeTypeAdapterFactory<BasicDevice> typeFactory = RuntimeTypeAdapterFactory
                .of(BasicDevice.class, "Kind")
                .registerSubtype(IosDevice.class, ComplexDataType.DeviceKind.iOS.toString())
                .registerSubtype(AndroidGenericDevice.class, ComplexDataType.DeviceKind.AndroidGeneric.toString())
                .registerSubtype(AndroidForWorkDevice.class, ComplexDataType.DeviceKind.AndroidForWork.toString())
                .registerSubtype(AndroidPlusDevice.class, ComplexDataType.DeviceKind.AndroidPlus.toString())
                .registerSubtype(SamsungKnoxDevice.class, ComplexDataType.DeviceKind.AndroidKnox.toString())
                .registerSubtype(SamsungElmDevice.class, ComplexDataType.DeviceKind.AndroidElm.toString())
                .registerSubtype(WindowsDesktop.class, ComplexDataType.DeviceKind.WindowsDesktop.toString())
                .registerSubtype(WindowsDesktopLegacy.class, ComplexDataType.DeviceKind.WindowsDesktopLegacy.toString())
                .registerSubtype(WindowsPhone.class, ComplexDataType.DeviceKind.WindowsPhone.toString())
                .registerSubtype(WindowsRuntime.class, ComplexDataType.DeviceKind.WindowsRuntime.toString())
                .registerSubtype(WindowsCE.class, ComplexDataType.DeviceKind.WindowsCE.toString());


        Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();

        return gson.fromJson(FakeJSON.devicesJson, deviceCollectionType);
    }

    private void insertMs() {
        ArrayList<ServerInfo.ManagementServer> msArray = getMsFromJson();
        ContentValues[] msValues = com.mdmobile.pocketconsole.utils.DbData.prepareMsValues(msArray);
        int inserted = InstrumentationRegistry.getContext().getContentResolver().bulkInsert(McContract.MsInfo.CONTENT_URI, msValues);
        assertTrue("MS not inserted", inserted > 0);
    }

    private void insertDs() {
        ArrayList<ServerInfo.DeploymentServer> dsArray = getDsFromJson();
        ContentValues[] dsValues = com.mdmobile.pocketconsole.utils.DbData.prepareDsValues(dsArray);
        int inserted = InstrumentationRegistry.getContext().getContentResolver().bulkInsert(McContract.DsInfo.CONTENT_URI, dsValues);
        assertTrue("DS not inserted", inserted > 0);
    }

    private ArrayList<ServerInfo.ManagementServer> getMsFromJson() {
        ServerInfo servers = new Gson().fromJson(FakeJSON.serverJson, ServerInfo.class);
        return new ArrayList<>(servers.getManagementServers());
    }

    private ArrayList<ServerInfo.DeploymentServer> getDsFromJson() {
        ServerInfo servers = new Gson().fromJson(FakeJSON.serverJson, ServerInfo.class);
        return new ArrayList<>(servers.getDeploymentServers());
    }
}

