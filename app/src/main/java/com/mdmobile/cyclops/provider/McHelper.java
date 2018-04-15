package com.mdmobile.cyclops.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.utils.DbData;

import static com.mdmobile.cyclops.provider.McContract.COMPLIANCE_ITEM_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.CUSTOM_ATTRIBUTE_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.CUSTOM_DATA_DEVICE_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.CUSTOM_DATA_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.CustomAttributeDevice.CUSTOM_ATTRIBUTE_ID;
import static com.mdmobile.cyclops.provider.McContract.DEPLOYMENT_SERVER_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.DEVICE_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.INSTALLED_APPLICATION_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.MANAGEMENT_SERVER_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.PROFILE_DEVICE_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.PROFILE_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.SCRIPT_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.SERVER_INFO_TABLE_NAME;
import static com.mdmobile.cyclops.provider.McContract.ScriptColumns.DESCRIPTION;
import static com.mdmobile.cyclops.provider.McContract.ScriptColumns.SCRIPT;
import static com.mdmobile.cyclops.provider.McContract.ScriptColumns.TITLE;
import static com.mdmobile.cyclops.provider.McContract.USER_TABLE_NAME;


public class McHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "PocketConsole.db";
    private static final int DB_VERSION = 33;
    private Context mContext;

    public McHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create device table
        db.execSQL(" CREATE TABLE " + McContract.DEVICE_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + McContract.Device.COLUMN_DEVICE_ID + " TEXT NOT NULL,"
                + McContract.Device.COLUMN_SERVER_ID + " INTEGER NOT NULL,"
                + McContract.Device.COLUMN_KIND + " TEXT,"
                + McContract.Device.COLUMN_DEVICE_NAME + " TEXT,"
                + McContract.Device.COLUMN_AGENT_ONLINE + " INTEGER,"
                + McContract.Device.COLUMN_FAMILY + " TEXT,"
                + McContract.Device.COLUMN_HOST_NAME + " TEXT,"
                + McContract.Device.COLUMN_ENROLLMENT_TIME + " TEXT,"
                + McContract.Device.COLUMN_COMPLIANCE_STATUS + " INTEGER,"
                + McContract.Device.COLUMN_VIRTUAL + " INTEGER,"
                + McContract.Device.COLUMN_MAC_ADDRESS + " TEXT,"
                + McContract.Device.COLUMN_MANUFACTURER + " TEXT,"
                + McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE + " INTEGER,"
                + McContract.Device.COLUMN_AVAILABLE_MEMORY + " INTEGER,"
                + McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE + " INTEGER,"
                + McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE + " INTEGER,"
                + McContract.Device.COLUMN_TOTAL_MEMORY + " INTEGER,"
                + McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE + " INTEGER,"
                + McContract.Device.COLUMN_TOTAL_STORAGE + " INTEGER,"
                + McContract.Device.COLUMN_MODE + " INTEGER,"
                + McContract.Device.COLUMN_MODEL + " TEXT,"
                + McContract.Device.COLUMN_OS_VERSION + " TEXT,"
                + McContract.Device.COLUMN_PATH + " TEXT,"
                + McContract.Device.COLUMN_PLATFORM + " INTEGER,"
                + McContract.Device.COLUMN_EXTRA_INFO + " TEXT,"
                + "UNIQUE(" + McContract.Device.COLUMN_DEVICE_ID + ") ON CONFLICT REPLACE, "
                + "FOREIGN KEY(" + McContract.Device.COLUMN_SERVER_ID + ") REFERENCES "
                + McContract.SERVER_INFO_TABLE_NAME + "(" + McContract.ServerInfo._ID + "));");

        //Create MsInfo table
        db.execSQL(" CREATE TABLE " + MANAGEMENT_SERVER_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + McContract.MsInfo.FULLY_QUALIFIED_NAME + " TEXT, "
                + McContract.MsInfo.PORT_NUMBER + " INTEGER, "
                + McContract.MsInfo.DESCRIPTION + " TEXT, "
                + McContract.MsInfo.STATUS_TIME + " TEXT, "
                + McContract.MsInfo.MAC_ADDRESS + " TEXT, "
                + McContract.MsInfo.TOTAL_USER_COUNT + " INTEGER, "
                + McContract.MsInfo.NAME + " TEXT, "
                + McContract.MsInfo.STATUS + " TEXT "
                + ");");

        //Create DsInfo table
        db.execSQL(" CREATE TABLE " + DEPLOYMENT_SERVER_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.DsInfo.NAME + " TEXT, "
                + McContract.DsInfo.STATUS + " TEXT, "
                + McContract.DsInfo.CONNECTED + " TEXT, "
                + McContract.DsInfo.PRIMARY_AGENT_ADDRESS + " TEXT, "
                + McContract.DsInfo.SECONDARY_AGENT_ADDRESS + " TEXT, "
                + McContract.DsInfo.DEVICE_MANAGEMENT_ADDRESS + " TEXT, "
                + McContract.DsInfo.PRIMARY_MANAGEMENT_ADDRESS + " TEXT, "
                + McContract.DsInfo.SECONDARY_MANAGEMENT_ADDRESS + " TEXT, "
                + McContract.DsInfo.PULSE_TIMEOUT + " INTEGER, "
                + McContract.DsInfo.RULE_RELOAD + " INTEGER, "
                + McContract.DsInfo.SCHEDULE_INTERVAL + " INTEGER, "
                + McContract.DsInfo.MIN_THREADS + " INTEGER, "
                + McContract.DsInfo.MAX_THREADS + " INTEGER, "
                + McContract.DsInfo.MAX_BURST_THREADS + " INTEGER, "
                + McContract.DsInfo.CURRENT_THREAD_COUNT + " INTEGER, "
                + McContract.DsInfo.PULSE_WAIT_INTERVAL + " INTEGER, "
                + McContract.DsInfo.DEVICES_CONNECTED + " INTEGER, "
                + McContract.DsInfo.MANAGERS_CONNECTED + " INTEGER, "
                + McContract.DsInfo.QUEUE_LENGTH + " INTEGER "
                + ");");

        //Create CustomData tables
        db.execSQL(" CREATE TABLE " + CUSTOM_DATA_TABLE_NAME
                + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + McContract.CustomData.KIND + " INTEGER, "
                + McContract.CustomData.TIME + " TEXT"
                + ");");

        db.execSQL(" CREATE TABLE " + CUSTOM_DATA_DEVICE_TABLE_NAME
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

        db.execSQL(" CREATE TABLE " + CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME
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

        //Create Installed Applications table
        db.execSQL("CREATE TABLE " + McContract.INSTALLED_APPLICATION_TABLE_NAME + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + McContract.InstalledApplications.DEVICE_ID + " TEXT NOT NULL, "
                + McContract.InstalledApplications.APPLICATION_NAME + " TEXT,"
                + McContract.InstalledApplications.APPLICATION_ID + " TEXT  NOT NULL, "
                + McContract.InstalledApplications.APPLICATION_SIZE + " TEXT, "
                + McContract.InstalledApplications.APPLICATION_DATA_USED + " TEXT, "
                + McContract.InstalledApplications.APPLICATION_VERSION + " TEXT, "
                + McContract.InstalledApplications.APPLICATION_BUILD_NUMBER + " TEXT, "
                + McContract.InstalledApplications.APPLICATION_STATUS + " TEXT, "
                + "FOREIGN KEY(" + McContract.InstalledApplications.DEVICE_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + " (" + McContract.Device.COLUMN_DEVICE_ID + "), "
                + "UNIQUE(" + McContract.InstalledApplications.APPLICATION_ID + ") ON CONFLICT REPLACE );");


        //Create Script table
        db.execSQL("CREATE TABLE " + McContract.SCRIPT_TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT NOT NULL, "
                + DESCRIPTION + " TEXT, "
                + SCRIPT + " TEXT NOT NULL );");

        //Create user table
        db.execSQL("CREATE TABLE " + McContract.USER_TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + McContract.UserInfo.NAME + " TEXT NOT NULL, "
                + McContract.UserInfo.DISPLAYED_NAME + " TEXT, "
                + McContract.UserInfo.KIND + " TEXT, "
                + McContract.UserInfo.IS_LOCKED + " INTEGER, "
                + McContract.UserInfo.IS_EULA_ACCEPTED + " INTEGER, "
                + McContract.UserInfo.EULA_ACCEPTANCE_DATE + " TEXT, "
                + McContract.UserInfo.NUMBER_OF_FAILED_LOGIN + " INTEGER );");

        //Create server info table
        db.execSQL("CREATE TABLE " + McContract.SERVER_INFO_TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + McContract.ServerInfo.CLIENT_ID + " TEXT NOT NULL, "
                + McContract.ServerInfo.CLIENT_SECRET + " TEXT NOT NULL, "
                + McContract.ServerInfo.NAME + " TEXT NOT NULL,"
                + McContract.ServerInfo.SERVER_ADDRESS + " TEXT,"
                + "UNIQUE(" + McContract.ServerInfo.NAME + ") ON CONFLICT ABORT );");

        //Create profile table
        db.execSQL("CREATE TABLE " + McContract.PROFILE_TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + McContract.Profile.REFERENCE_ID + "  NOT NULL, "
                + McContract.Profile.NAME + " TEXT NOT NULL, "
                + McContract.Profile.STATUS + " TEXT NOT NULL, "
                + McContract.Profile.ASSIGNMENT_DATE + " TEXT, "
                + McContract.Profile.IS_MANDATORY + " INTEGER, "
                + McContract.Profile.VERSION_NUMBER + " INTEGER, "
                + "UNIQUE(" + McContract.Profile.REFERENCE_ID + ") ON CONFLICT REPLACE );");

        //Create profile device hook table
        db.execSQL("CREATE TABLE " + McContract.PROFILE_DEVICE_TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + McContract.ProfileDevice.DEVICE_ID + " TEXT NOT NULL, "
                + McContract.ProfileDevice.PROFILE_ID + " TEXT NOT NULL, "
                + " FOREIGN KEY (" + McContract.ProfileDevice.DEVICE_ID + ") REFERENCES "
                + DEVICE_TABLE_NAME + "(" + McContract.Device.COLUMN_DEVICE_ID + "), "
                + " FOREIGN KEY(" + McContract.ProfileDevice.PROFILE_ID + ") REFERENCES "
                + PROFILE_TABLE_NAME + "( " + McContract.Profile.REFERENCE_ID + ") ON DELETE CASCADE);");

        //Triggers

        //Whenever we delete a device we delete related installed apps
        db.execSQL("CREATE TRIGGER RemoveDeviceApps BEFORE DELETE ON " + McContract.DEVICE_TABLE_NAME
                + " BEGIN "
                + "DELETE FROM " + McContract.INSTALLED_APPLICATION_TABLE_NAME
                + " WHERE " + McContract.INSTALLED_APPLICATION_TABLE_NAME + "." + McContract.InstalledApplications.DEVICE_ID
                + "= OLD." + McContract.Device.COLUMN_DEVICE_ID + ";"
                + "END;");
        //Whenever we delete a device we delete references in PROFILE-DEVICE lookup table
        db.execSQL("CREATE TRIGGER RemoveDeviceProfiles BEFORE DELETE ON " + McContract.DEVICE_TABLE_NAME
                + " BEGIN "
                + "DELETE FROM " + McContract.PROFILE_DEVICE_TABLE_NAME
                + " WHERE " + McContract.PROFILE_DEVICE_TABLE_NAME + "." + McContract.ProfileDevice.DEVICE_ID
                + "= OLD." + McContract.Device.COLUMN_DEVICE_ID + ";"
                + "END;");

        //Whenever we delete a server we delete all devices belonging to the server
        db.execSQL("CREATE TRIGGER RemoveDevices BEFORE DELETE ON " + McContract.SERVER_INFO_TABLE_NAME
                + " BEGIN "
                + " DELETE FROM " + McContract.DEVICE_TABLE_NAME
                + " WHERE " + McContract.DEVICE_TABLE_NAME + "." + McContract.Device.COLUMN_SERVER_ID
                + "=OLD." + McContract.ServerInfo._ID + ";"
                + "END;");


        insertStandardScript(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DEVICE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MANAGEMENT_SERVER_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DEPLOYMENT_SERVER_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CUSTOM_DATA_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CUSTOM_DATA_DEVICE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CUSTOM_ATTRIBUTE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COMPLIANCE_ITEM_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + INSTALLED_APPLICATION_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SCRIPT_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SERVER_INFO_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROFILE_DEVICE_TABLE_NAME + ";");


        onCreate(sqLiteDatabase);
    }

    private void insertStandardScript(SQLiteDatabase db) {
        Resources res = mContext.getResources();
        String[] titles = res.getStringArray(R.array.default_script_titles);
        String[] descriptions = res.getStringArray(R.array.default_script_descriptions);
        String[] scripts = res.getStringArray(R.array.default_script);
        ContentValues scriptValues;
        for (int i = 0; i < titles.length; i++) {
            scriptValues = DbData.prepareScriptValues(titles[i], descriptions[i], scripts[i]);
            db.insert(McContract.SCRIPT_TABLE_NAME, null, scriptValues);
        }
    }

}
