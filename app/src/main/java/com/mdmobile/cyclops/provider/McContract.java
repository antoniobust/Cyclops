package com.mdmobile.cyclops.provider;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;

public class McContract {

    //Defines the schema
    public static final String CONTENT_AUTHORITY = "com.mdmobile.cyclops";
    //DB tables
    //TODO: create user info table
    public static final String DEVICE_TABLE_NAME = "DeviceInfo";
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
    public static final String INSTALLED_APPLICATION_TABLE_NAME = "InstalledApps";
    public static final String MANAGEMENT_SERVER_TABLE_NAME = "MsInfo";
    public static final String DEPLOYMENT_SERVER_TABLE_NAME = "DsInfo";
    public static final String SCRIPT_TABLE_NAME = "Script";
    public static final String USER_TABLE_NAME = "UserInfo";
    public static final String SERVER_INFO_TABLE_NAME = "ServerInfo";
    //Content type
    private static final String CONTENT_TYPE_BASE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";
    private static final String CONTENT_TYPE_ITEM_BASE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";
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

    public static String makeItemContentType(@NonNull String tableName) {
        return CONTENT_TYPE_ITEM_BASE + tableName;
    }

    private static Uri appendServerUriPath(Uri baseUri, String serverName) {
        return baseUri.buildUpon().appendPath("server").appendPath(serverName).build();
    }

    public static String getServerNameFromUri(Uri uri) {
        return uri.getLastPathSegment();
    }

    public static Uri buildUriWithServerName(Uri contentUri, String serverName) {
        return contentUri.buildUpon().appendPath("server").appendPath(serverName).build();
    }

    public static String getServerIdFromUri(Uri uri) {
        return uri.getLastPathSegment();
    }

    // ************ Table's columns interfaces **********************
    interface DeviceInfo {
        //Columns
        String COLUMN_SERVER_ID = "ServerID";
        String COLUMN_KIND = "Kind";
        String COLUMN_COMPLIANCE_STATUS = "ComplianceStatus";
        String COLUMN_DEVICE_ID = "DeviceId";
        String COLUMN_DEVICE_NAME = "DeviceName";
        String COLUMN_FAMILY = "Family";
        String COLUMN_HOST_NAME = "HostName";
        String COLUMN_AGENT_ONLINE = "IsAgentOnline";
        String COLUMN_VIRTUAL = "Virtual";
        String COLUMN_MAC_ADDRESS = "MAC";
        String COLUMN_MANUFACTURER = "Manufacturer";
        String COLUMN_MODE = "Mode";
        String COLUMN_MODEL = "Model";
        String COLUMN_OS_VERSION = "OSVersion";
        String COLUMN_PATH = "Path";
        String COLUMN_PLATFORM = "Platform";
        String COLUMN_AVAILABLE_EXTERNAL_STORAGE = "AvailableExternalStorage";
        String COLUMN_AVAILABLE_MEMORY = "AvailableMemory";
        String COLUMN_AVAILABLE_SD_CARD_STORAGE = "AvailableSdCardStorage";
        String COLUMN_TOTAL_EXTERNAL_STORAGE = "TotExternalMemory";
        String COLUMN_TOTAL_MEMORY = "TotMemory";
        String COLUMN_TOTAL_SD_CARD_STORAGE = "TotSdCardStorage";
        String COLUMN_TOTAL_STORAGE = "TotStorage";
        String COLUMN_ENROLLMENT_TIME = "EnrollmentTime";
        String COLUMN_EXTRA_INFO = "ExtraDeviceInfo";
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

    interface MsInfoColumns {
        //Columns
        String SERVER_ID = "ServerId";
        String FULLY_QUALIFIED_NAME = "Fqdn";
        String PORT_NUMBER = "PortNumber";
        String DESCRIPTION = "Description";
        String STATUS_TIME = "StatusTime";
        String MAC_ADDRESS = "MacAddress";
        String TOTAL_USER_COUNT = "TotalConsoleUsers";
        String NAME = "Name";
        String STATUS = "Status";
    }

    interface InstalledAppsColumns {
        String DEVICE_ID = "DeviceID";
        String APPLICATION_ID = "ApplicationID";
        String APPLICATION_NAME = "Name";
        String APPLICATION_VERSION = "MajorVersion";
        String APPLICATION_BUILD_NUMBER = "BuildVersion";
        String APPLICATION_SIZE = "Size";
        String APPLICATION_DATA_USED = "DataCached";
        String APPLICATION_STATUS = "Status";
    }

    interface DsInfoColumns {
        //Columns
        String SERVER_ID = "ServerId";
        String NAME = "Name";
        String STATUS = "Status";
        String CONNECTED = "Connected";
        String PRIMARY_AGENT_ADDRESS = "PrimaryAgentAddress";
        String SECONDARY_AGENT_ADDRESS = "SecondaryAgentAddress";
        String DEVICE_MANAGEMENT_ADDRESS = "DeviceManagementAddress";
        String PRIMARY_MANAGEMENT_ADDRESS = "PrimaryManagementAddress";
        String SECONDARY_MANAGEMENT_ADDRESS = "SecondaryManagementAddress";
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

    interface ScriptColumns {
        String TITLE = "Title";
        String DESCRIPTION = "Description";
        String SCRIPT = "Script";
    }

    //***************************************************************


    interface UserInfoColumns {
        String NAME = "Name";
        String DISPLAYED_NAME = "DisplayedName";
        String KIND = "Kind";
        String IS_EULA_ACCEPTED = "EulaAccepted";
        String EULA_ACCEPTANCE_DATE = "EulaAcceptanceDate";
        String IS_LOCKED = "IsLocked";
        String NUMBER_OF_FAILED_LOGIN = "NumberOfFailedLogin";
        String SERVER_ID = "ServerId";
    }

    //If these column name are changed, check settings xml @xml/instance_preference
    interface ServerInfoColumns {
        String NAME = "Name";
        String CLIENT_ID = "ClientId";
        String CLIENT_SECRET = "ClientSecret";
        String SERVER_ADDRESS = "Address";
        String SERVER_MAJOR_VERSION = "MajorVersion";
        String SERVER_BUILD_NUMBER = "BuildNumber";
    }


    interface ProfileColumns {
        String REFERENCE_ID = "ReferenceId";
        String NAME = "Name";
        String VERSION_NUMBER = "VersionNumber";
        String STATUS = "Status";
        String IS_MANDATORY = "IsMandatory";
        String ASSIGNMENT_DATE = "AssignmentDate";
    }

    //Represent Device table
    public static class Device implements DeviceInfo, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEVICE_TABLE_NAME).build();
        public static final String[] FULL_PROJECTION = {
                McContract.DEVICE_TABLE_NAME + "." + _ID, COLUMN_KIND, COLUMN_COMPLIANCE_STATUS, COLUMN_DEVICE_ID, COLUMN_DEVICE_NAME, COLUMN_FAMILY, COLUMN_HOST_NAME,
                COLUMN_AGENT_ONLINE, COLUMN_VIRTUAL, COLUMN_MAC_ADDRESS, COLUMN_MANUFACTURER, COLUMN_MODE, COLUMN_MODEL,
                COLUMN_OS_VERSION, COLUMN_PATH, COLUMN_PLATFORM, COLUMN_AVAILABLE_EXTERNAL_STORAGE, COLUMN_AVAILABLE_MEMORY,
                COLUMN_AVAILABLE_SD_CARD_STORAGE, COLUMN_TOTAL_EXTERNAL_STORAGE, COLUMN_TOTAL_MEMORY, COLUMN_TOTAL_SD_CARD_STORAGE,
                COLUMN_TOTAL_STORAGE, COLUMN_ENROLLMENT_TIME, COLUMN_EXTRA_INFO
        };

        public static Uri buildUriWithDeviceID(@NonNull String deviceID) {
            return CONTENT_URI.buildUpon().appendPath(deviceID).build();
        }

        public static Uri buildUriWithID(long ID) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(ID)).build();
        }

        public static String getDeviceIdFromUri(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildUriWithGroup(@NonNull String column) {
            return CONTENT_URI.buildUpon().appendPath("GROUP_BY").appendPath(column).build();
        }

        public static Uri buildUriWithServerName(@NonNull String serverName) {
            return appendServerUriPath(CONTENT_URI, serverName);
        }

        public static String getGroupByFromUri(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    ////Represent Compliance table
    public static class ComplianceItem implements ComplianceItemColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(COMPLIANCE_ITEM_TABLE_NAME).build();

        public static Uri buildUriWithID(long ID) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(ID)).build();
        }

        public static String getComplianceIdFromUri(@NonNull Uri uri) {
            //Check if URI provided is device URI
            if (uri.toString().startsWith(CONTENT_URI.toString())) {
                return uri.getLastPathSegment();
            } else {
                return null;
            }
        }
    }

    //Represent Custom Attribute table
    public static class CustomAttribute implements CustomAttributeColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(CUSTOM_ATTRIBUTE_TABLE_NAME).build();

        public static Uri buildUriWithID(long ID) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(ID)).build();
        }

        public static String getCustomAttributeFromUri(@NonNull Uri uri) {
            //Check if URI provided is device URI
            if (uri.toString().startsWith(CONTENT_URI.toString())) {
                return uri.getLastPathSegment();
            } else {
                return null;
            }
        }

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

    //Represent Profile table
    public static class Profile implements ProfileColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(PROFILE_TABLE_NAME).build();

        //Full projection
        public static final String[] FULL_PROJECTION = {
                McContract.PROFILE_TABLE_NAME + "." + _ID, Profile.NAME, Profile.REFERENCE_ID, Profile.ASSIGNMENT_DATE,
                Profile.IS_MANDATORY, Profile.STATUS, Profile.VERSION_NUMBER};

        public static Uri buildUriWithID(@NonNull String deviceID) {
            return CONTENT_URI.buildUpon().appendPath(deviceID).build();
        }

        public static Uri buildUriWithDeviceId(@NonNull String profileId) {
            return CONTENT_URI.buildUpon().appendPath("device").appendPath(profileId).build();
        }

        public static String getUriId(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    //Link table n -> n relation from devices and profiles
    public static class ProfileDevice implements BaseColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon()
                .appendPath(PROFILE_DEVICE_TABLE_NAME).build();

        //Columns
        public static final String DEVICE_ID = "DeviceID";
        public static final String PROFILE_ID = "ProfileID";
    }

    public static class InstalledApplications implements BaseColumns, InstalledAppsColumns {
        //Table URI
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(INSTALLED_APPLICATION_TABLE_NAME).build();

        //Projection
        public static final String[] FULL_PROJECTION = {McContract.INSTALLED_APPLICATION_TABLE_NAME + "." + _ID,
                InstalledApplications.DEVICE_ID, InstalledApplications.APPLICATION_NAME, InstalledApplications.APPLICATION_STATUS,
                InstalledApplications.APPLICATION_ID, InstalledApplications.APPLICATION_SIZE, InstalledApplications.APPLICATION_VERSION,
                InstalledApplications.APPLICATION_BUILD_NUMBER, InstalledApplications.APPLICATION_DATA_USED};

        //Methods
        public static String getAppIdFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static String getAppPackageNameFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildUriWithId(long applicationId) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(applicationId)).build();
        }

        public static Uri buildUriWithDevId(String deviceID) {
            return CONTENT_URI.buildUpon().appendPath(deviceID).build();
        }

        public static String getDeviceIdFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildUriWithAppPackageName(String applicationId) {
            return CONTENT_URI.buildUpon().appendPath(applicationId).build();
        }
    }

    //Represent Management server table
    public static class MsInfo implements MsInfoColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(MANAGEMENT_SERVER_TABLE_NAME).build();

        public static Uri buildUriWithMsId(long id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri buildUriWithServerName(@NonNull String serverName) {
            return appendServerUriPath(CONTENT_URI, serverName);
        }

        public static String getServerNameFromUri(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }

    }

    //Represent deployment server table
    public static class DsInfo implements DsInfoColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(DEPLOYMENT_SERVER_TABLE_NAME).build();

        public static Uri buildUriWithDsId(long id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static Uri buildUriWithServerName(@NonNull String serverName) {
            return appendServerUriPath(CONTENT_URI, serverName);
        }

        public static String getServerNameFromUri(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    //Represent the server added to the application
    public static class ServerInfo implements ServerInfoColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(SERVER_INFO_TABLE_NAME).build();

        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String getServerNameFromUri(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildServerInfoUriWithName(String serverName) {
            return CONTENT_URI.buildUpon().appendPath(serverName).build();
        }
    }

    public static class Script implements ScriptColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(SCRIPT_TABLE_NAME).build();

        //Helper uri methods
        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String getScriptIdFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class UserInfo implements UserInfoColumns, BaseColumns {
        //Table Uri
        public static final Uri CONTENT_URI = DB_URI.buildUpon().appendPath(USER_TABLE_NAME).build();

        public static Uri buildUriWithUserId(long id) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String getUserIdFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildUriWithServerName(@NonNull String serverName) {
            return appendServerUriPath(CONTENT_URI, serverName);
        }

        public static String getServerNameFromUri(@NonNull Uri uri) {
            return uri.getLastPathSegment();
        }
    }
}
