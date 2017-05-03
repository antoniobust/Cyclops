package com.mdmobile.pocketconsole.dbTesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.mdmobile.pocketconsole.fakeData.FakeJSON;
import com.mdmobile.pocketconsole.gson.Device;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.provider.McHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Integer.getInteger;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Test db data manipulation
 */

@RunWith(AndroidJUnit4.class)
public class DbData {

    Context mContext;
    SQLiteDatabase db;

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
    public void createNewDevice() {

        Gson gson = new Gson();
        Device device = gson.fromJson(FakeJSON.deviceJson,Device.class);
        assertTrue("Device name doesn't match: "+device.getDeviceName(),
                device.getDeviceName().equals("(John) Honeywell Dolphin Black 70e 00001"));

        ContentValues deviceValues = com.mdmobile.pocketconsole.utils.DbData.getDeviceContentValues(device);

        Uri newDev = mContext.getApplicationContext().getContentResolver().insert(McContract.Device.CONTENT_URI, deviceValues);
        assertNotNull("Device was not created", newDev);
        String id = McContract.Device.getDeviceIdFromUri(newDev);
        assertFalse("New device id is not valid", id!= null && id.equals("-1"));

        //TODO:test values inserted
        Cursor c = db.rawQuery("SELECT * from " + McContract.DEVICE_TABLE_NAME, null);
        assertTrue("No device found", c.moveToFirst());
        do {
            assertTrue("Data error found device online: 0", c.getInt(c.getColumnIndex(McContract.Device.COLUMN_AGENT_ONLINE)) == 1);
        } while (c.moveToNext());
        c.close();
    }

}

