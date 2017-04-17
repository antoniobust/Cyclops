package com.mdmobile.pocketconsole.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "PocketConsole.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create device table
        db.execSQL(DbContract.Device.CREATE_TABLE_QUERY);

        //Create ManagementServer table
        db.execSQL(DbContract.ManagementServer.CREATE_TABLE_QUERY);

        //Create DeploymentServer table
        db.execSQL(DbContract.DeploymentServer.CREATE_TABLE_QUERY);

        //Create CustomData tables
        db.execSQL(DbContract.CustomData.CREATE_TABLE_QUERY);
        db.execSQL(DbContract.CustomDataDevice.CREATE_TABLE_QUERY);

        //Create CustomAttribute tables
        db.execSQL(DbContract.CustomAttribute.CREATE_TABLE_QUERY);
        db.execSQL(DbContract.CustomAttributeDevice.CREATE_TABLE_QUERY);

        //Create ComplianceItem table
        db.execSQL(DbContract.ComplianceItem.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
