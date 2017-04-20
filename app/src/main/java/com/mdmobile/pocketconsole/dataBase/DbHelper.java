package com.mdmobile.pocketconsole.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.mdmobile.pocketconsole.dataBase.DbContract.CUSTOM_ATTRIBUTE_TABLE_NAME;
import static com.mdmobile.pocketconsole.dataBase.DbContract.CUSTOM_DATA_TABLE_NAME;
import static com.mdmobile.pocketconsole.dataBase.DbContract.CustomAttributeDevice.CUSTOM_ATTRIBUTE_ID;
import static com.mdmobile.pocketconsole.dataBase.DbContract.DEVICE_TABLE_NAME;
import static com.mdmobile.pocketconsole.dataBase.DbContract.MANAGEMENT_SERVER_TABLE_NAME;


public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "PocketConsole.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create device table
        db.execSQL(" CREATE TABLE " + DbContract.DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.Device.COLUMN_DEVICE_ID + " TEXT UNIQUE NOT NULL, "
                + DbContract.Device.COLUMN_KIND + " INTEGER, "
                + DbContract.Device.COLUMN_DEVICE_NAME + " TEXT, "
                + DbContract.Device.COLUMN_AGENT_ONLINE + " INTEGER, "
                + DbContract.Device.COLUMN_FAMILY + " INTEGER, "
                + DbContract.Device.COLUMN_HOST_NAME + " TEXT, "
                + DbContract.Device.COLUMN_ENROLLMENT_TIME + " TEXT, "
                + DbContract.Device.COLUMN_COMPLIANCE_STATUS + " INTEGER, "
                + DbContract.Device.COLUMN_VIRTUAL + " INTEGER, "
                + DbContract.Device.COLUMN_MAC_ADDRESS + " TEXT, "
                + DbContract.Device.COLUMN_MANUFACTURER + " TEXT, "
                + DbContract.Device.COLUMN_MODE + " INTEGER, "
                + DbContract.Device.COLUMN_MODEL + " TEXT, "
                + DbContract.Device.COLUMN_OS_VERSION + " TEXT, "
                + DbContract.Device.COLUMN_PATH + " TEXT, "
                + DbContract.Device.COLUMN_PLATFORM + " INTEGER"
                + ");");

        //Create ManagementServer table
        db.execSQL(" CREATE TABLE " + MANAGEMENT_SERVER_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.ManagementServer.PRIMARY_MANAGEMENT_ADDRESS + " TEXT, "
                + DbContract.ManagementServer.SECONDARY_MANAGEMENT_ADDRESS + " TEXT, "
                + DbContract.ManagementServer.FULLY_QUALIFIED_NAME + " TEXT, "
                + DbContract.ManagementServer.PORT_NUMBER + " INTEGER, "
                + DbContract.ManagementServer.DESCRIPTION + " TEXT, "
                + DbContract.ManagementServer.STATUS_TIME + " TEXT, "
                + DbContract.ManagementServer.MAC_ADRESS + " TEXT, "
                + DbContract.ManagementServer.TOTAL_USER_COUNT + " INTEGER, "
                + DbContract.ManagementServer.NAME + " TEXT, "
                + DbContract.ManagementServer.STATUS + " TEXT "
                + ");");

        //Create DeploymentServer table
        db.execSQL(" CREATE TABLE " + DbContract.DEPLOYMENT_SERVER_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.DeploymentServer.NAME + " TEXT, "
                + DbContract.DeploymentServer.STATUS + " TEXT, "
                + DbContract.DeploymentServer.CONNECTED + " TEXT, "
                + DbContract.DeploymentServer.PRIMARY_AGENT_ADDRESS + " TEXT, "
                + DbContract.DeploymentServer.SECONDARY_AGENT_ADDRESS + " TEXT, "
                + DbContract.DeploymentServer.DEVICE_MANAGEMENT_ADDRESS + " TEXT, "
                + DbContract.DeploymentServer.PULSE_TIMEOUT + " TEXT, "
                + DbContract.DeploymentServer.RULE_RELOAD + " INTEGER, "
                + DbContract.DeploymentServer.SCHEDULE_INTERVAL + " TEXT, "
                + DbContract.DeploymentServer.MIN_THREADS + " TEXT "
                + DbContract.DeploymentServer.MAX_THREADS + " INTEGER, "
                + DbContract.DeploymentServer.MAX_BURST_THREADS + " TEXT, "
                + DbContract.DeploymentServer.CURRENT_THREAD_COUNT + " TEXT, "
                + DbContract.DeploymentServer.PULSE_WAIT_INTERVAL + " TEXT, "
                + DbContract.DeploymentServer.DEVICES_CONNECTED + " INTEGER, "
                + DbContract.DeploymentServer.MANAGERS_CONNECTED + " TEXT, "
                + DbContract.DeploymentServer.QUEUE_LENGTH + " TEXT "
                + ");");

        //Create CustomData tables
        db.execSQL(" CREATE TABLE " + CUSTOM_DATA_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.CustomData.KIND + " INTEGER, "
                + DbContract.CustomData.TIME + " TEXT"
                + ");");

        db.execSQL(" CREATE TABLE " + DbContract.CUSTOM_DATA_DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.CustomDataDevice.DEVICE_ID + " INTEGER, "
                + DbContract.CustomDataDevice.CUSTOM_DATA_ID + " INTEGER, "
                + "FOREIGN KEY(" + DbContract.CustomDataDevice.DEVICE_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + DbContract.Device._ID + "),"
                + "FOREIGN KEY(" + DbContract.CustomDataDevice.CUSTOM_DATA_ID + ") REFERENCES "
                + CUSTOM_DATA_TABLE_NAME + " (" + DbContract.CustomData._ID + ")"
                + ");");

        //Create CustomAttribute tables
        db.execSQL(" CREATE TABLE " + CUSTOM_ATTRIBUTE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.CustomAttribute.NAME + " TEXT, "
                + DbContract.CustomAttribute.VALUE + " TEXT, "
                + DbContract.CustomAttribute.DATA_TYPE + " INTEGER"
                + ");");

        db.execSQL(" CREATE TABLE " + DbContract.CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.CustomAttributeDevice.DEVICE_ID + " INTEGER, "
                + DbContract.CustomAttributeDevice.CUSTOM_ATTRIBUTE_ID + " INTEGER, "
                + "FOREIGN KEY(" + DbContract.CustomAttributeDevice.DEVICE_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + DbContract.Device._ID + "),"
                + "FOREIGN KEY(" + CUSTOM_ATTRIBUTE_ID + ") REFERENCES "
                + CUSTOM_ATTRIBUTE_TABLE_NAME + " (" + DbContract.CustomAttribute._ID + ")"
                + ");");

        //Create ComplianceItem table
        db.execSQL(" CREATE TABLE " + DbContract.COMPLIANCE_ITEM_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + DbContract.ComplianceItem.DEV_ID + " INT NOT NULL, "
                + DbContract.ComplianceItem.COMPLIANCE_TYPE + " INTEGER, "
                + DbContract.ComplianceItem.COMPLIANCE_VALUE + " TEXT, "
                + "FOREIGN KEY(" + DbContract.ComplianceItem.DEV_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + DbContract.Device._ID + ")"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
