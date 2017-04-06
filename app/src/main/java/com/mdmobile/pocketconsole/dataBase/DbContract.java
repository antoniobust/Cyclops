package com.mdmobile.pocketconsole.dataBase;


import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    //Defines the schema
    public static final String AUTHORITY = "com.mdmobile.pocketconsole";

    //DB tables
    public static final String DEVICE_TABLE_PATH = "Device";
    public static final String COMPLIANCE_ITEM_TABLE_PATH = "ComplianceItem";
    public static final String CUSTOM_DATA_TABLE_PATH = "CustomAttribute";
    public static final String PROFILE_TABLE_PATH = "Profile";
    public static final String PAYLOAD_TABLE_PATH = "Payload";
    public static final String PAYLOAD_TYPE_TABLE_PATH = "PayloadType";
    public static final String PAYLOAD_SUB_TYPE_TABLE_PATH = "PayloadSubType";
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

        //Columns
        public final static String DEV_ID = "DeviceID";
        public final static String COMPLIANCE_ITEM = "ComplianceType";
        public final static String COMPLIANCE_VALUE = "ComplianceValue";
    }

    //Represent CustomData table
    public static final class CustomAttributes implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_DATA_TABLE_PATH).build();

        //Columns
        public final static String NAME = "Name";
        public final static String VALUE = "Value";
        public final static String DATA_TYPE = "DataType";
    }

    //Represent Profile table
    public static final class Profile implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_TABLE_PATH).build();

        //Columns
        public final static String REFERENCE_ID = "ReferenceId";
        public final static String NAME = "Name";
        public final static String VERSION_NUMBER = "VersionNumber";
        public final static String STATUS = "Status";
        public final static String MANDATORY = "Mandatory";
        public final static String ASSIGNMET_DATE = "AssignmentDate";
        public final static String PROFILE_CONFIGURATION_ID = "ProfileConfigurationID";
        public final static String PROFILE_PACKAGE_ID = "ProfilePackageID";
    }

    //Represent a payload table
    public static final class Payload implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PAYLOAD_TABLE_PATH).build();

        //Columns
        public final static String NAME = "Name";
        public final static String PAYLOAD_TYPE_ID = "PayloadTypeID";
        public final static String STATUS = "Status";

    }

    //Represent the payload type table
    public static final class PayloadType implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PAYLOAD_TYPE_TABLE_PATH).build();

        //Columns
        public final static String NAME = "Name";
        public final static String SUBTYPE_ID = "SubtypeID";
    }

    //Represent the payload type table
    public static final class PayloadSubType implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PAYLOAD_SUB_TYPE_TABLE_PATH).build();

        //Columns
        public final static String NAME = "Name";
    }

    //Represent a package table
    public static final class ProfilePackage implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_PACKAGE_TABLE_PATH).build();

        //Columns
        public final static String NAME = "Name";
        public final static String VERSION = "Version";
        public final static String SIZE = "Size";
        public final static String STATUS = "Status";
        public final static String REFERNECE_ID = "ReferenceId";

    }

    //Represent Management server table
    public static final class ManagementServer implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(MANAGEMENT_SERVER_TABLE_PATH).build();

        //Columns
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
    public static final class DeploymentServer implements BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEPLOYMENT_SERVER_TABLE_PATH).build();

        //Columns
        public final static String NAME = "Name";
        public final static String STATUS = "Status";
        public final static String CONNECTED = "Connected";
        public final static String PRIMARY_MANAGMENT_ADDRESS = "PrimaryManagementAddress";
        public final static String SECONDARY_MANAGEMENT_ADDRESS = "SecondaryManagementAddress";
        public final static String PRIMARY_AGENT_ADDRESS = "PrimaryAgentAddress";
        public final static String SECONDARY_AGENT_ADDRESS = "SecondaryAgentAddress";
        public final static String DEVICE_MANAGMENT_ADRESS = "DeviceManagementAddress";
        public final static String PULSE_TIMEOUT = "PulseTimeout";
        public final static String RULE_RELOAD = "RuleReload";
        public final static String SCHEDULE_INTERVAL = "ScheduleInterval";
        public final static String MIN_THREADS = "MinThreads";
        public final static String MAX_THREADS = "MaxThreads";
        public final static String MAX_BURST_THREADS = "MaxBurstThreads";
        public final static String CURRENT_THREAD_COUNT = "CurrentThreadCount";
        public final static String PULSE_WAIT_INTERVAL = "PulseWaitInterval";
        public final static String DEVICES_CONNCTED = "DevicesConnectedCount";
        public final static String MANGERS_CONNECTED = "ManagersConnectedCount";
        public final static String QUEUE_LENGTH = "QueueLength";
    }


}
