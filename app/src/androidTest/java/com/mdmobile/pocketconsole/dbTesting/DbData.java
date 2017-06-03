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
import com.mdmobile.pocketconsole.dataTypes.ComplexDataType;
import com.mdmobile.pocketconsole.fakeData.FakeJSON;
import com.mdmobile.pocketconsole.gson.RuntimeTypeAdapterFactory;
import com.mdmobile.pocketconsole.gson.devices.AndroidForWorkDevice;
import com.mdmobile.pocketconsole.gson.devices.AndroidGenericDevice;
import com.mdmobile.pocketconsole.gson.devices.AndroidPlusDevice;
import com.mdmobile.pocketconsole.gson.devices.BasicDevice;
import com.mdmobile.pocketconsole.gson.devices.IosDevice;
import com.mdmobile.pocketconsole.gson.devices.SamsungElmDevice;
import com.mdmobile.pocketconsole.gson.devices.SamsungKnoxDevice;
import com.mdmobile.pocketconsole.gson.devices.WindowsCE;
import com.mdmobile.pocketconsole.gson.devices.WindowsDesktop;
import com.mdmobile.pocketconsole.gson.devices.WindowsDesktopLegacy;
import com.mdmobile.pocketconsole.gson.devices.WindowsPhone;
import com.mdmobile.pocketconsole.gson.devices.WindowsRuntime;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.provider.McHelper;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    public void testInsertDevice() {
        createSingleNewDevice();
        createNewDevicesBulkImport();
    }

    @Test
    public void TestDeviceSelection() {
        createNewDevicesBulkImport();
        Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(McContract.Device.CONTENT_URI, null, null, null, null);
        assertTrue("No device found", c.moveToNext());
        int count = 0;
        HashSet<String> IDs = new HashSet<>(c.getCount());
        while (!c.isLast()) {
            count++;
            IDs.add(c.getString(c.getColumnIndex(McContract.Device.COLUMN_DEVICE_ID)));
            c.moveToNext();
        }
        assertTrue("Device count: " + count, count > 1);
        assertTrue("Some IDs are duplicated"+ IDs.toString(), IDs.size() == count);

    }


    private void createSingleNewDevice() {
        ArrayList<BasicDevice> devicesArray = getDeviceFromJson();

        ContentValues deviceValues = com.mdmobile.pocketconsole.utils.DbData.formatDeviceData(devicesArray.get(0));

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
            ContentValues[] devicesValues = com.mdmobile.pocketconsole.utils.DbData.bulkFormatDeviceData(devicesArray);
            newDev = InstrumentationRegistry.getTargetContext().getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
        }
        assertFalse("Not all devices have been inserted correctly", newDev == 0);
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
}

