package com.mdmobile.pocketconsole.dbTesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.fakeData.FakeJSON;
import com.mdmobile.pocketconsole.gson.Device;
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
        Device[] devicesArray = getDeviceFromJson();

        ContentValues deviceValues = com.mdmobile.pocketconsole.utils.DbData.getDeviceContentValues(devicesArray[0]);

        Uri newDev = mContext.getApplicationContext().getContentResolver().insert(McContract.Device.CONTENT_URI, deviceValues);
        assertNotNull("Device was not created", newDev);
        String id = McContract.Device.getDeviceIdFromUri(newDev);
        assertNotNull(id);
        assertFalse("New device id is not valid", id.equals("-1"));

        Cursor c = db.rawQuery("SELECT * from " + McContract.DEVICE_TABLE_NAME, null);
        assertTrue("No device found", c.moveToFirst());
        do {
            assertFalse("Data error found device online: 1", c.getInt(c.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1);
            assertTrue("Data error found device Memory doesn't match", c.getInt(c.getColumnIndex(McContract.Device.COLUMN_AVAILABLE_MEMORY)) == 306085888);

        } while (c.moveToNext());
        c.close();
    }

    private void createNewDevicesBulkImport() {
        Device[] devicesArray = getDeviceFromJson();
        ContentValues[] devicesValues = com.mdmobile.pocketconsole.utils.DbData.bulkFormatDeviceData(devicesArray);
        int newDev = mContext.getApplicationContext().getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
        assertFalse("Not all devices have been inserted correctly", newDev == 0);
    }


    private Device[] getDeviceFromJson() {
        Type deviceCollectionType = new TypeToken<ArrayList<Device>>() {
        }.getType();

        ArrayList<Device> device = new Gson().fromJson(FakeJSON.devicesJson, deviceCollectionType);
        Device[] devicesArray = new Device[device.size()];
        device.toArray(devicesArray);
        assertTrue("Device name doesn't match: " + devicesArray[0].getDeviceName(),
                devicesArray[0].getDeviceName().equals("(John) Honeywell Dolphin Black 70e 00001"));
        assertTrue("Device name doesn't match: " + devicesArray[1].getDeviceName(),
                devicesArray[1].getDeviceName().equals("(John) Intermec CN70 00001"));

        return devicesArray;
    }

}

