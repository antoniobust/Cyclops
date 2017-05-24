package com.mdmobile.pocketconsole.dbTesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Test db data manipulation
 */

@RunWith(AndroidJUnit4.class)
public class DbData {

    private Context mContext;
    private SQLiteDatabase db;

    private void deleteExistingDB() {
        mContext.deleteDatabase(McHelper.DB_NAME);
    }

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        deleteExistingDB();
        db = new McHelper(mContext).getWritableDatabase();
        assertTrue("Db was not created properly", db.isOpen());

    }

    @Test
    public void testInsertDevice() {
        createSingleNewDevice();
        createNewDevicesBulkImport();
    }


    private void createSingleNewDevice() {
        ArrayList<BasicDevice> devicesArray = getDeviceFromJson();

        ContentValues deviceValues = com.mdmobile.pocketconsole.utils.DbData.formatDeviceData(devicesArray.get(0));

        Uri newDev = mContext.getApplicationContext().getContentResolver().insert(McContract.Device.CONTENT_URI, deviceValues);
        assertNotNull("Device was not created", newDev);
        String id = McContract.Device.getDeviceIdFromUri(newDev);
        assertNotNull(id);
        assertFalse("New device id is not valid", id.equals("-1"));

        Cursor c = db.rawQuery("SELECT * from " + McContract.DEVICE_TABLE_NAME, null);
        assertTrue("No device found", c.moveToFirst());

        c.close();
    }

    private void createNewDevicesBulkImport() {
        ArrayList<BasicDevice> devicesArray = getDeviceFromJson();
        int newDev = -1;

        if (devicesArray.size() > 1) {
            ContentValues[] devicesValues = com.mdmobile.pocketconsole.utils.DbData.bulkFormatDeviceData(devicesArray);
            newDev = mContext.getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
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

