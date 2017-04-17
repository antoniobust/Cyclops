package com.mdmobile.pocketconsole.dataBase;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    //Defines the schema
    public static final String CONTENT_AUTHORITY = "com.mdmobile.pocketconsole";
    //DB tables
    public static final String DEVICE_TABLE_NAME = "Device";
    public static final String COMPLIANCE_ITEM_TABLE_NAME = "ComplianceItem";
    public static final String CUSTOM_ATTRIBUTE_TABLE_NAME = "CustomAttribute";
    public static final String CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME = "CustomAttributeDevice";
    public static final String PROFILE_TABLE_NAME = "Profile";
    public static final String PROFILE_DEVICE_TABLE_NAME = "ProfileDevice";
    public static final String PAYLOAD_TABLE_NAME = "Payload";
    public static final String PAYLOAD_CONFIGURATION_TYPE_TABLE_NAME = "PayloadType";
    public static final String PAYLOAD_SUB_TYPE_TABLE_NAME = "PayloadSubType";
    public static final String PROFILE_PACKAGE_TABLE_NAME = "ProfilePackage";
    public static final String CUSTOM_DATA_TABLE_NAME = "CustomData";
    public static final String CUSTOM_DATA_DEVICE_TABLE_NAME = "CustomDataDevice";
    public static final String MANAGEMENT_SERVER_TABLE_NAME = "ManagementServer";
    public static final String DEPLOYMENT_SERVER_TABLE_NAME = "DeploymentServer";
    //DB URI
    private static final Uri DB_URI = Uri.parse("content://").buildUpon()
            .authority(CONTENT_AUTHORITY)
            .build();


    private DbContract() {
    }

    //Represent Device table
    public static class Device implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEVICE_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEVICE_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEVICE_TABLE_NAME;

        //Columns
        public final static String COLUMN_KIND = "Kind";
        public final static String COLUMN_COMPLIANCE_STATUS = "ComplianceStatus";
        public final static String COLUMN_DEVICE_ID = "DeviceId";
        public final static String COLUMN_DEVICE_NAME = "DeviceName";
        public final static String COLUMN_ENROLLMENT_TIME = "EnrollmentTime";
        public final static String COLUMN_FAMILY = "Family";
        public final static String COLUMN_HOST_NAME = "HostName";
        public final static String COLUMN_AGENT_ONLINE = "Online";
        public final static String COLUMN_VIRTUAL = "Virtual";
        public final static String COLUMN_MAC_ADDRESS = "MAC";
        public final static String COLUMN_MANUFACTURER = "Manufacturer";
        public final static String COLUMN_MODE = "Mode";
        public final static String COLUMN_MODEL = "Model";
        public final static String COLUMN_OS_VERSION = "OSVersion";
        public final static String COLUMN_PATH = "Path";
        public final static String COLUMN_PLATFORM = "Platform";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + DEVICE_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + COLUMN_DEVICE_ID + " TEXT UNIQUE NOT NULL, "
                + COLUMN_KIND + " INTEGER, "
                + COLUMN_DEVICE_NAME + " TEXT, "
                + COLUMN_AGENT_ONLINE + " INTEGER, "
                + COLUMN_FAMILY + " INTEGER, "
                + COLUMN_HOST_NAME + " TEXT, "
                + COLUMN_ENROLLMENT_TIME + " TEXT, "
                + COLUMN_COMPLIANCE_STATUS + " INTEGER, "
                + COLUMN_VIRTUAL + " INTEGER, "
                + COLUMN_MAC_ADDRESS + " TEXT, "
                + COLUMN_MANUFACTURER + " TEXT, "
                + COLUMN_MODE + " INTEGER, "
                + COLUMN_MODEL + " TEXT, "
                + COLUMN_OS_VERSION + " TEXT, "
                + COLUMN_PATH + " TEXT, "
                + COLUMN_PLATFORM + " INTEGER"
                + ");";
    }


    ////Represent Compliance table
    public static class ComplianceItem implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(COMPLIANCE_ITEM_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + COMPLIANCE_ITEM_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + COMPLIANCE_ITEM_TABLE_NAME;

        //Columns
        public final static String DEV_ID = "DeviceID";
        public final static String COMPLIANCE_TYPE = "ComplianceType";
        public final static String COMPLIANCE_VALUE = "ComplianceValue";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + COMPLIANCE_ITEM_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + DEV_ID + " INT NOT NULL, "
                + COMPLIANCE_TYPE + " INTEGER, "
                + COMPLIANCE_VALUE + " TEXT, "
                + "FOREIGN KEY(" + DEV_ID + ") REFERENCES " + DEVICE_TABLE_NAME + " (" + Device._ID + ")"
                + ");";
    }


    //Represent Custom Attribute table
    public static class CustomAttribute implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_ATTRIBUTE_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_ATTRIBUTE_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_ATTRIBUTE_TABLE_NAME;

        //Columns
        public final static String NAME = "Name";
        public final static String VALUE = "Value";
        public final static String DATA_TYPE = "DataType";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + CUSTOM_ATTRIBUTE_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + NAME + " TEXT, "
                + VALUE + " TEXT, "
                + DATA_TYPE + " INTEGER"
                + ");";
    }


    //Link table n -> n relation from devices and custom attributes
    public static class CustomAttributeDevice implements BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon()
                .appendPath(CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME).build();


        //Columns
        public static final String DEVICE_ID = "DeviceID";
        public static final String CUSTOM_ATTRIBUTE_ID = "CustomAttributeID";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + DEVICE_ID + " INTEGER, "
                + CUSTOM_ATTRIBUTE_ID + " INTEGER, "
                + "FOREIGN KEY(" + DEVICE_ID + ") REFERENCES " + DEVICE_TABLE_NAME + " (" + Device._ID + "),"
                + "FOREIGN KEY(" + CUSTOM_ATTRIBUTE_ID + ") REFERENCES " + CUSTOM_ATTRIBUTE_TABLE_NAME + " (" + CustomAttribute._ID + ")"
                + ");";
    }


    //Represent custom data table
    public static class CustomData implements BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_DATA_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_DATA_TABLE_NAME;

        //Columns
        public static final String KIND = "Kind";
        public static final String TIME = "Time";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + CUSTOM_DATA_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + KIND + " INTEGER, "
                + TIME + " TEXT"
                + ");";
    }


    //Represent the link table for n -> n relation between Devices and CustomData
    public static class CustomDataDevice implements BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_DEVICE_TABLE_NAME).build();

        //Columns
        public static final String DEVICE_ID = "DeviceID";
        public static final String CUSTOM_DATA_ID = "CustomDataID";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + CUSTOM_DATA_DEVICE_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + DEVICE_ID + " INTEGER, "
                + CUSTOM_DATA_ID + " INTEGER, "
                + "FOREIGN KEY(" + DEVICE_ID + ") REFERENCES " + DEVICE_TABLE_NAME + " (" + Device._ID + "),"
                + "FOREIGN KEY(" + CUSTOM_DATA_ID + ") REFERENCES " + CUSTOM_DATA_TABLE_NAME + " (" + CustomData._ID + ")"
                + ");";
    }


//    //Represent Profile table
//    public static class Profile implements BaseColumns {
//        //Table Uri
//        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_TABLE_NAME).build();
//
//        //Columns
//        public final static String REFERENCE_ID = "ReferenceId";
//        public final static String NAME = "Name";
//        public final static String VERSION_NUMBER = "VersionNumber";
//        public final static String STATUS = "Status";
//        public final static String MANDATORY = "Mandatory";
//        public final static String ASSIGNMET_DATE = "AssignmentDate";
//        public final static String PROFILE_CONFIGURATION_ID = "ProfileConfigurationID";
//        public final static String PROFILE_PACKAGE_ID = "ProfilePackageID";
//    }
//
//
//    //Link table n -> n relation from devices and custom profiles
//    public static class ProfileDevice implements BaseColumns {
//        //Table URI
//        public static final Uri CONTENT_URI = DB_URI.buildUpon()
//                .appendPath(PROFILE_DEVICE_TABLE_NAME).build();
//
//        //Columns
//        public static final String DEVICE_ID = "DeviceID";
//        public static final String PROFILE_ID = "ProfileID";
//    }
//
//
//    //Represent a payload table
//    public static class Payload implements BaseColumns {
//        //Table Uri
//        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PAYLOAD_TABLE_NAME).build();
//
//        //Columns
//        public final static String NAME = "Name";
//        public final static String PAYLOAD_TYPE_ID = "PayloadTypeID";
//        public final static String STATUS = "Status";
//
//    }
//
//
//    //Represent the payload sub type table
//    public static class PayloadSubType implements BaseColumns {
//        //Table Uri
//        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PAYLOAD_SUB_TYPE_TABLE_NAME).build();
//
//        //Columns
//        public final static String NAME = "Name";
//    }
//
//
//    //Represent a package table
//    public static class ProfilePackage implements BaseColumns {
//        //Table Uri
//        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_PACKAGE_TABLE_NAME).build();
//
//        //Columns
//        public final static String NAME = "Name";
//        public final static String VERSION = "Version";
//        public final static String SIZE = "Size";
//        public final static String STATUS = "Status";
//        public final static String REFERNECE_ID = "ReferenceId";
//
//    }


    //Represent Management server table
    public static class ManagementServer implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(MANAGEMENT_SERVER_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + MANAGEMENT_SERVER_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + MANAGEMENT_SERVER_TABLE_NAME;


        //Columns
        public final static String PRIMARY_MANAGEMENT_ADDRESS = "PrimaryManagementAddress";
        public final static String SECONDARY_MANAGEMENT_ADDRESS = "SecondaryManagementAddress";
        public final static String FULLY_QUALIFIED_NAME = "Fqdn";
        public final static String PORT_NUMBER = "PortNumber";
        public final static String DESCRIPTION = "Description";
        public final static String STATUS_TIME = "StatusTime";
        public final static String MAC_ADRESS = "MacAddress";
        public final static String TOTAL_USER_COUNT = "TotalConsoleUsers";
        public final static String NAME = "Name";
        public final static String STATUS = "Status";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + MANAGEMENT_SERVER_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + PRIMARY_MANAGEMENT_ADDRESS + " TEXT, "
                + SECONDARY_MANAGEMENT_ADDRESS + " TEXT, "
                + FULLY_QUALIFIED_NAME + " TEXT, "
                + PORT_NUMBER + " INTEGER, "
                + DESCRIPTION + " TEXT, "
                + STATUS_TIME + " TEXT, "
                + MAC_ADRESS + " TEXT, "
                + TOTAL_USER_COUNT + " INTEGER, "
                + NAME + " TEXT, "
                + STATUS + " TEXT "
                + ");";
    }


    //Represent deployment server table
    public static class DeploymentServer implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEPLOYMENT_SERVER_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEPLOYMENT_SERVER_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEPLOYMENT_SERVER_TABLE_NAME;

        //Columns
        public final static String NAME = "Name";
        public final static String STATUS = "Status";
        public final static String CONNECTED = "Connected";
        public final static String PRIMARY_AGENT_ADDRESS = "PrimaryAgentAddress";
        public final static String SECONDARY_AGENT_ADDRESS = "SecondaryAgentAddress";
        public final static String DEVICE_MANAGEMENT_ADDRESS = "DeviceManagementAddress";
        public final static String PULSE_TIMEOUT = "PulseTimeout";
        public final static String RULE_RELOAD = "RuleReload";
        public final static String SCHEDULE_INTERVAL = "ScheduleInterval";
        public final static String MIN_THREADS = "MinThreads";
        public final static String MAX_THREADS = "MaxThreads";
        public final static String MAX_BURST_THREADS = "MaxBurstThreads";
        public final static String CURRENT_THREAD_COUNT = "CurrentThreadCount";
        public final static String PULSE_WAIT_INTERVAL = "PulseWaitInterval";
        public final static String DEVICES_CONNECTED = "DevicesConnectedCount";
        public final static String MANAGERS_CONNECTED = "ManagersConnectedCount";
        public final static String QUEUE_LENGTH = "QueueLength";

        final static String CREATE_TABLE_QUERY = " CREATE TABLE " + MANAGEMENT_SERVER_TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                + NAME + " TEXT, "
                + STATUS + " TEXT, "
                + CONNECTED + " TEXT, "
                + PRIMARY_AGENT_ADDRESS + " INTEGER, "
                + SECONDARY_AGENT_ADDRESS + " TEXT, "
                + DEVICE_MANAGEMENT_ADDRESS + " TEXT, "
                + PULSE_TIMEOUT + " TEXT, "
                + RULE_RELOAD + " INTEGER, "
                + SCHEDULE_INTERVAL + " TEXT, "
                + MIN_THREADS + " TEXT "
                + MAX_THREADS + " INTEGER, "
                + MAX_BURST_THREADS + " TEXT, "
                + CURRENT_THREAD_COUNT + " TEXT, "
                + PULSE_WAIT_INTERVAL + " TEXT, "
                + DEVICES_CONNECTED + " INTEGER, "
                + MANAGERS_CONNECTED + " TEXT, "
                + QUEUE_LENGTH + " TEXT "
                + ");";
    }
}
