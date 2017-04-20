package com.mdmobile.pocketconsole.dataBase;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

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


        public final Uri builUriWithDeviceID(@NonNull String deviceID) {
            return CONTENT_URI.buildUpon().appendPath(deviceID).build();
        }

        public final String getDeviceIdFromUri(@NonNull Uri uri) {
            //Check if URI provided is device URI
            if (uri.toString().startsWith(CONTENT_URI.toString())) {
                return uri.getLastPathSegment();
            } else {
                return null;
            }
        }
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

        public final Uri buildUriWithName(@NonNull String customAttribute) {
            return CONTENT_URI.buildUpon().appendPath(customAttribute).build();
        }

        public final String getNameFromURI(@NonNull Uri uri) {
            //Check if URI provided is custom attr URI
            if (uri.toString().startsWith(CONTENT_URI.toString())) {
                return uri.getLastPathSegment();
            } else {
                return null;
            }
        }
    }


    //Link table n -> n relation from devices and custom attributes
    public static class CustomAttributeDevice implements BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon()
                .appendPath(CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME).build();

        //Columns
        public static final String DEVICE_ID = "DeviceID";
        public static final String CUSTOM_ATTRIBUTE_ID = "CustomAttributeID";

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


        public final Uri buildUriWithName(@NonNull String customDataName) {
            return CONTENT_URI.buildUpon().appendPath(customDataName).build();
        }

        public final String getNameFromURI(@NonNull Uri uri) {
            //Check if URI provided is custom attr URI
            if (uri.toString().startsWith(CONTENT_URI.toString())) {
                return uri.getLastPathSegment();
            } else {
                return null;
            }
        }
    }


    //Represent the link table for n -> n relation between Devices and CustomData
    public static class CustomDataDevice implements BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_DEVICE_TABLE_NAME).build();

        //Columns
        public static final String DEVICE_ID = "DeviceID";
        public static final String CUSTOM_DATA_ID = "CustomDataID";

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

    }
}
