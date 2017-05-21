package com.mdmobile.pocketconsole.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.mdmobile.pocketconsole.provider.McContract.CUSTOM_ATTRIBUTE_TABLE_NAME;
import static com.mdmobile.pocketconsole.provider.McContract.CUSTOM_DATA_TABLE_NAME;
import static com.mdmobile.pocketconsole.provider.McContract.CustomAttributeDevice.CUSTOM_ATTRIBUTE_ID;
import static com.mdmobile.pocketconsole.provider.McContract.DEVICE_TABLE_NAME;
import static com.mdmobile.pocketconsole.provider.McContract.MANAGEMENT_SERVER_TABLE_NAME;


public class McHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "PocketConsole.db";
    private static final int DB_VERSION = 8;

    public McHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create device table
        db.execSQL(" CREATE TABLE " + McContract.DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + McContract.Device.COLUMN_DEVICE_ID + " TEXT UNIQUE NOT NULL, "
                + McContract.Device.COLUMN_KIND + " TEXT, "
                + McContract.Device.COLUMN_DEVICE_NAME + " TEXT, "
                + McContract.Device.COLUMN_AGENT_ONLINE + " INTEGER, "
                + McContract.Device.COLUMN_FAMILY + " TEXT, "
                + McContract.Device.COLUMN_HOST_NAME + " TEXT, "
                + McContract.Device.COLUMN_ENROLLMENT_TIME + " TEXT, "
                + McContract.Device.COLUMN_COMPLIANCE_STATUS + " INTEGER, "
                + McContract.Device.COLUMN_VIRTUAL + " INTEGER, "
                + McContract.Device.COLUMN_MAC_ADDRESS + " TEXT, "
                + McContract.Device.COLUMN_MANUFACTURER + " TEXT, "
                + McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE + " INTEGER, "
                + McContract.Device.COLUMN_AVAILABLE_MEMORY + " INTEGER, "
                + McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE + " INTEGER, "
                + McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE + " INTEGER, "
                + McContract.Device.COLUMN_TOTAL_MEMORY + " INTEGER, "
                + McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE + " INTEGER, "
                + McContract.Device.COLUMN_TOTAL_STORAGE + " INTEGER, "
                + McContract.Device.COLUMN_MODE + " INTEGER, "
                + McContract.Device.COLUMN_MODEL + " TEXT, "
                + McContract.Device.COLUMN_OS_VERSION + " TEXT, "
                + McContract.Device.COLUMN_PATH + " TEXT, "
                + McContract.Device.COLUMN_PLATFORM + " INTEGER, "
                + McContract.Device.COLUMN_EXTRA_INFO + " TEXT"
                + ");");


        //Create ManagementServer table
        db.execSQL(" CREATE TABLE " + MANAGEMENT_SERVER_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + McContract.ManagementServer.PRIMARY_MANAGEMENT_ADDRESS + " TEXT, "
                + McContract.ManagementServer.SECONDARY_MANAGEMENT_ADDRESS + " TEXT, "
                + McContract.ManagementServer.FULLY_QUALIFIED_NAME + " TEXT, "
                + McContract.ManagementServer.PORT_NUMBER + " INTEGER, "
                + McContract.ManagementServer.DESCRIPTION + " TEXT, "
                + McContract.ManagementServer.STATUS_TIME + " TEXT, "
                + McContract.ManagementServer.MAC_ADRESS + " TEXT, "
                + McContract.ManagementServer.TOTAL_USER_COUNT + " INTEGER, "
                + McContract.ManagementServer.NAME + " TEXT, "
                + McContract.ManagementServer.STATUS + " TEXT "
                + ");");

        //Create DeploymentServer table
        db.execSQL(" CREATE TABLE " + McContract.DEPLOYMENT_SERVER_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.DeploymentServer.NAME + " TEXT, "
                + McContract.DeploymentServer.STATUS + " TEXT, "
                + McContract.DeploymentServer.CONNECTED + " TEXT, "
                + McContract.DeploymentServer.PRIMARY_AGENT_ADDRESS + " TEXT, "
                + McContract.DeploymentServer.SECONDARY_AGENT_ADDRESS + " TEXT, "
                + McContract.DeploymentServer.DEVICE_MANAGEMENT_ADDRESS + " TEXT, "
                + McContract.DeploymentServer.PULSE_TIMEOUT + " TEXT, "
                + McContract.DeploymentServer.RULE_RELOAD + " INTEGER, "
                + McContract.DeploymentServer.SCHEDULE_INTERVAL + " TEXT, "
                + McContract.DeploymentServer.MIN_THREADS + " TEXT "
                + McContract.DeploymentServer.MAX_THREADS + " INTEGER, "
                + McContract.DeploymentServer.MAX_BURST_THREADS + " TEXT, "
                + McContract.DeploymentServer.CURRENT_THREAD_COUNT + " TEXT, "
                + McContract.DeploymentServer.PULSE_WAIT_INTERVAL + " TEXT, "
                + McContract.DeploymentServer.DEVICES_CONNECTED + " INTEGER, "
                + McContract.DeploymentServer.MANAGERS_CONNECTED + " TEXT, "
                + McContract.DeploymentServer.QUEUE_LENGTH + " TEXT "
                + ");");

        //Create CustomData tables
        db.execSQL(" CREATE TABLE " + CUSTOM_DATA_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.CustomData.KIND + " INTEGER, "
                + McContract.CustomData.TIME + " TEXT"
                + ");");

        db.execSQL(" CREATE TABLE " + McContract.CUSTOM_DATA_DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.CustomDataDevice.DEVICE_ID + " INTEGER, "
                + McContract.CustomDataDevice.CUSTOM_DATA_ID + " INTEGER, "
                + "FOREIGN KEY(" + McContract.CustomDataDevice.DEVICE_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + McContract.Device._ID + "),"
                + "FOREIGN KEY(" + McContract.CustomDataDevice.CUSTOM_DATA_ID + ") REFERENCES "
                + CUSTOM_DATA_TABLE_NAME + " (" + McContract.CustomData._ID + ")"
                + ");");

        //Create CustomAttribute tables
        db.execSQL(" CREATE TABLE " + CUSTOM_ATTRIBUTE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.CustomAttribute.NAME + " TEXT, "
                + McContract.CustomAttribute.VALUE + " TEXT, "
                + McContract.CustomAttribute.DATA_TYPE + " INTEGER"
                + ");");

        db.execSQL(" CREATE TABLE " + McContract.CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.CustomAttributeDevice.DEVICE_ID + " INTEGER, "
                + McContract.CustomAttributeDevice.CUSTOM_ATTRIBUTE_ID + " INTEGER, "
                + "FOREIGN KEY(" + McContract.CustomAttributeDevice.DEVICE_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + McContract.Device._ID + "),"
                + "FOREIGN KEY(" + CUSTOM_ATTRIBUTE_ID + ") REFERENCES "
                + CUSTOM_ATTRIBUTE_TABLE_NAME + " (" + McContract.CustomAttribute._ID + ")"
                + ");");

        //Create ComplianceItem table
        db.execSQL(" CREATE TABLE " + McContract.COMPLIANCE_ITEM_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.ComplianceItem.DEV_ID + " INT NOT NULL, "
                + McContract.ComplianceItem.COMPLIANCE_TYPE + " INTEGER, "
                + McContract.ComplianceItem.COMPLIANCE_VALUE + " TEXT, "
                + "FOREIGN KEY(" + McContract.ComplianceItem.DEV_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + McContract.Device._ID + ")"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
