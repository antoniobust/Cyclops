package com.mdmobile.cyclops.dbTesting;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.provider.McHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TableStructureTest {

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
    public void testCreateDb() {

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master where type = 'table'", null);
        assertTrue("Cursor is empty", c.moveToFirst());

        //Contains the tables Name
        HashSet<String> tableNames = new HashSet<>();
        tableNames.add(McContract.DEVICE_TABLE_NAME);
        tableNames.add(McContract.COMPLIANCE_ITEM_TABLE_NAME);
        tableNames.add(McContract.CUSTOM_ATTRIBUTE_TABLE_NAME);
        tableNames.add(McContract.CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME);
        tableNames.add(McContract.CUSTOM_DATA_TABLE_NAME);
        tableNames.add(McContract.CUSTOM_DATA_DEVICE_TABLE_NAME);
        tableNames.add(McContract.DEPLOYMENT_SERVER_TABLE_NAME);
        tableNames.add(McContract.MANAGEMENT_SERVER_TABLE_NAME);
        tableNames.add(McContract.INSTALLED_APPLICATION_TABLE_NAME);
        tableNames.add(McContract.SCRIPT_TABLE_NAME);

        //Remove from table Name set table returned in the cursor
        HashSet<String> testTableNames = new HashSet<>(tableNames);
        do {
            testTableNames.remove(c.getString(0));
        } while (c.moveToNext());

        c.close();
    }


}
