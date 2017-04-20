package com.mdmobile.pocketconsole.dbTesting;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mdmobile.pocketconsole.dataBase.DbContract;
import com.mdmobile.pocketconsole.dataBase.DbHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TableStructureTest {

    Context mContext;
    SQLiteDatabase db;

    private void deleteExistingDB() {
        mContext.deleteDatabase(DbHelper.DB_NAME);
    }

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        deleteExistingDB();
        db = new DbHelper(mContext).getWritableDatabase();
        assertTrue("Db was not created properly", db.isOpen());

    }


    @Test
    public void testCreateDb() {

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master where type = 'table'", null);
        assertTrue("Cursor is empty", c.moveToFirst());

        //Contains the tables name
        HashSet<String> tableNames = new HashSet<>();
        tableNames.add(DbContract.DEVICE_TABLE_NAME);
        tableNames.add(DbContract.COMPLIANCE_ITEM_TABLE_NAME);
        tableNames.add(DbContract.CUSTOM_ATTRIBUTE_TABLE_NAME);
        tableNames.add(DbContract.CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME);
        tableNames.add(DbContract.CUSTOM_DATA_TABLE_NAME);
        tableNames.add(DbContract.CUSTOM_DATA_DEVICE_TABLE_NAME);
        tableNames.add(DbContract.DEPLOYMENT_SERVER_TABLE_NAME);
        tableNames.add(DbContract.MANAGEMENT_SERVER_TABLE_NAME);

        //Remove from table name set table returned in the cursor
        HashSet<String> testTableNames = new HashSet<>(tableNames);
        do {
            testTableNames.remove(c.getString(0));
        } while (c.moveToNext());
    }



}
