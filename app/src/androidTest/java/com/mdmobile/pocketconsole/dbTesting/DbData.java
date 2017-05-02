package com.mdmobile.pocketconsole.dbTesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.provider.McHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void createNewDevice(){
        ContentValues deviceValues = com.mdmobile.pocketconsole.utils.DbData.getDeviceContentValues(
                "Android", 1,"deviceId23234","AndroidPlus0001","11:00","android plus","AntoHost",1,
                0,"00:00:00:ff","Samsung","enabled","S8","marshmellow","\\AntoFolder","platform",
                "13.3",100,100,100,100,100,100,100,90,95,"EE",50,"","",1,"offline","IMEI",0,"IPv6",
                1,0,0,"12:00","12:00","12:00","12:00","12:00","mobile","RSSI","SSID",1,"07475","exynos",
                "345");

        Uri newDev = mContext.getApplicationContext().getContentResolver().insert(McContract.Device.CONTENT_URI, deviceValues);
        assertNotNull("Device was not created",newDev);
        String id = McContract.Device.getDeviceIdFromUri(newDev);
        assertTrue("New device id is not valid", Integer.getInteger(id)>0);
    }

}

