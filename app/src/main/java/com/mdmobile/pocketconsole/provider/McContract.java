package com.mdmobile.pocketconsole.provider;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

public class McContract {

    //Defines the schema
    public static final String CONTENT_AUTHORITY = "com.mdmobile.pocketconsole";
    //Content type
    private static final String CONTENT_TYPE_BASE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";
    private static final String CONTENT_TYPE_ITEM_BASE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";
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

    private McContract() {
    }

    //Content Type Helper Methods
    public static String makeContentType(@NonNull String tableName) {
        return CONTENT_TYPE_BASE + tableName;
    }

    public static String makeItemContentType(@NonNull String tableName){
        return  CONTENT_TYPE_ITEM_BASE + tableName;
    }

    // ************ Table's columns interfaces **********************
    interface DeviceColumns {
        //Columns
        String COLUMN_KIND = "Kind";
        String COLUMN_COMPLIANCE_STATUS = "ComplianceStatus";
        String COLUMN_DEVICE_ID = "DeviceId";
        String COLUMN_DEVICE_NAME = "DeviceName";
        String COLUMN_ENROLLMENT_TIME = "EnrollmentTime";
        String COLUMN_FAMILY = "Family";
        String COLUMN_HOST_NAME = "HostName";
        String COLUMN_AGENT_ONLINE = "Online";
        String COLUMN_VIRTUAL = "Virtual";
        String COLUMN_MAC_ADDRESS = "MAC";
        String COLUMN_MANUFACTURER = "Manufacturer";
        String COLUMN_MODE = "Mode";
        String COLUMN_MODEL = "Model";
        String COLUMN_OS_VERSION = "OSVersion";
        String COLUMN_PATH = "Path";
        String COLUMN_PLATFORM = "Platform";
    }

    interface ComplianceItemColumns {
        //Columns
        String DEV_ID = "DeviceID";
        String COMPLIANCE_TYPE = "ComplianceType";
        String COMPLIANCE_VALUE = "ComplianceValue";
    }

    interface CustomAttributeColumns {
        //Columns
        String NAME = "Name";
        String VALUE = "Value";
        String DATA_TYPE = "DataType";
    }

    interface CustomAttributeDeviceColumns {
        //Columns
        String DEVICE_ID = "DeviceID";
        String CUSTOM_ATTRIBUTE_ID = "CustomAttributeID";
    }

    interface CustomDataColumns {
        //Columns
        String KIND = "Kind";
        String TIME = "Time";

    }

    interface CustomDataDeviceColumns {
        //Columns
        String DEVICE_ID = "DeviceID";
        String CUSTOM_DATA_ID = "CustomDataID";
    }

    interface ManagementServerColumns {
        //Columns
        String PRIMARY_MANAGEMENT_ADDRESS = "PrimaryManagementAddress";
        String SECONDARY_MANAGEMENT_ADDRESS = "SecondaryManagementAddress";
        String FULLY_QUALIFIED_NAME = "Fqdn";
        String PORT_NUMBER = "PortNumber";
        String DESCRIPTION = "Description";
        String STATUS_TIME = "StatusTime";
        String MAC_ADRESS = "MacAddress";
        String TOTAL_USER_COUNT = "TotalConsoleUsers";
        String NAME = "Name";
        String STATUS = "Status";
    }
    //***************************************************************

    interface DeploymentServerColumns {
        //Columns
        String NAME = "Name";
        String STATUS = "Status";
        String CONNECTED = "Connected";
        String PRIMARY_AGENT_ADDRESS = "PrimaryAgentAddress";
        String SECONDARY_AGENT_ADDRESS = "SecondaryAgentAddress";
        String DEVICE_MANAGEMENT_ADDRESS = "DeviceManagementAddress";
        String PULSE_TIMEOUT = "PulseTimeout";
        String RULE_RELOAD = "RuleReload";
        String SCHEDULE_INTERVAL = "ScheduleInterval";
        String MIN_THREADS = "MinThreads";
        String MAX_THREADS = "MaxThreads";
        String MAX_BURST_THREADS = "MaxBurstThreads";
        String CURRENT_THREAD_COUNT = "CurrentThreadCount";
        String PULSE_WAIT_INTERVAL = "PulseWaitInterval";
        String DEVICES_CONNECTED = "DevicesConnectedCount";
        String MANAGERS_CONNECTED = "ManagersConnectedCount";
        String QUEUE_LENGTH = "QueueLength";
    }

    //Represent Device table
    public static class Device implements DeviceColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEVICE_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEVICE_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEVICE_TABLE_NAME;


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
    public static class ComplianceItem implements ComplianceItemColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(COMPLIANCE_ITEM_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + COMPLIANCE_ITEM_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + COMPLIANCE_ITEM_TABLE_NAME;
    }


    //Represent Custom Attribute table
    public static class CustomAttribute implements CustomAttributeColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_ATTRIBUTE_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_ATTRIBUTE_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_ATTRIBUTE_TABLE_NAME;


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
    public static class CustomAttributeDevice implements CustomAttributeDeviceColumns, BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon()
                .appendPath(CUSTOM_ATTRIBUTE_DEVICE_TABLE_NAME).build();
    }


    //Represent custom data table
    public static class CustomData implements CustomDataColumns, BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_DATA_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + CUSTOM_DATA_TABLE_NAME;

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
    public static class CustomDataDevice implements CustomDataDeviceColumns, BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_DEVICE_TABLE_NAME).build();

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
    public static class ManagementServer implements ManagementServerColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(MANAGEMENT_SERVER_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + MANAGEMENT_SERVER_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + MANAGEMENT_SERVER_TABLE_NAME;

    }


    //Represent deployment server table
    public static class DeploymentServer implements DeploymentServerColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEPLOYMENT_SERVER_TABLE_NAME).build();

        //Content Type
        public static final String DIR_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEPLOYMENT_SERVER_TABLE_NAME;
        public static final String SINGLE_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY
                + DEPLOYMENT_SERVER_TABLE_NAME;

    }
}
