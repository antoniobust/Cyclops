package com.mdmobile.pocketconsole.dataBase;


import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    //Defines the schema
    public static final String AUTHORITY = "com.mdmobile.pocketconsole";

    //DB tables
    public static final String DEVICE_TABLE_PATH = "Device";
    public static final String COMPLIANCE_ITEM_TABLE_PATH = "ComplianceItem";
    public static final String CUSTOM_DATA_TABLE_PATH = "CustomData";
    public static final String PROFILE_TABLE_PATH = "Profile";
    public static final String PROFILE_CONFIGURATION_TABLE_PATH = "ProfileConfiguration";
    public static final String DEVICE_CONFIGURATION_TYPE_TABLE_PATH = "DeviceConfigurationType";
    public static final String DEVICE_CONFIGURATION_SUB_TYPE_TABLE_PATH = "DeviceConfigurationSubType";
    public static final String PROFILE_PACKAGE_TABLE_PATH = "ProfilePackage";
    public static final String MANAGEMENT_SERVER_TABLE_PATH = "ManagementServer";
    public static final String DEPLOYMENT_SERVER_TABLE_PATH = "DeploymentServer";


    //DB URI
    private static final Uri DB_URI = Uri.parse("content://").buildUpon()
            .authority(AUTHORITY)
            .build();

    //Represent Device table
    public static final class Device implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEVICE_TABLE_PATH).build();

        //Columns
        public final static String COLUMN_KIND = "Kind";
        public final static String COLUMN_COMPILANCE_STATUS = "ComplianceStatus";
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


    }

    ////Represent Compliance table
    public static final class ComplianceItem implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(COMPLIANCE_ITEM_TABLE_PATH).build();
    }

    //Represent CustomData table
    public static final class CustomData implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_TABLE_PATH).build();
    }

    //Represent Profile table
    public static final class Profile implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_TABLE_PATH).build();
    }

    //Represent Profile configuration table
    public static final class ProfileConfiguration implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_CONFIGURATION_TABLE_PATH).build();
    }

    //Represent the payload table
    public static final class DeviceConfigurationType implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEVICE_CONFIGURATION_TYPE_TABLE_PATH).build();
    }

    //Represent the payload type table
    public static final class DeviceConfigurationSubType implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEVICE_CONFIGURATION_SUB_TYPE_TABLE_PATH).build();
    }

    //Represent a package table
    public static final class ProfilePackage implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_PACKAGE_TABLE_PATH).build();
    }

    //Represent Management server table
    public static final class ManagementServer implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(MANAGEMENT_SERVER_TABLE_PATH).build();
    }

    //Represent deployment server table
    public static final class DeploymentServer implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEPLOYMENT_SERVER_TABLE_PATH).build();
    }


}
