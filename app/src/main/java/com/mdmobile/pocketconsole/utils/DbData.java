package com.mdmobile.pocketconsole.utils;

import android.content.ContentValues;

import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Helper methods to get data ready for DB
 */

public class DbData {

    public static ContentValues getDeviceContentValues(
            final String kind, final int complianceStatus, final String deviceID, final String deviceName, final String enrollmentTime,
            final String family, final String hostName, final int onLine, final int virtual, final String mac, final String manufacturer,
            final String mode, final String model, final String osVersion, final String path, final String platform,
            final String agentVersion, final int availableExternalStorage, final int availableMemory, final int availableSdCardStorage,
            final int totExternalStorage, final int totMemory, final int totSdCardStorage,
            final int totStorage, final int backupBatteryStatus, final int batteryStatus, final String cellularCarrier,
            final int cellularSignalStrength, final String deviceTerms, final String deviceUserInfo,
            final int exchangeBlocked, final String exchangeStatus, final String imeiMeidEsn, final int inRoaming,
            final String ipv6, final int isAgentCompatible, final int isAgentless, final int isLearning,
            final String lastCheckin, final String lastAgentConnection, final String lastAgentDisconnection,
            final String lastLoggedOnAt, final String lastLoggedUser, final String networkConnectionType, final String networkRSSI,
            final String networkSSID, final int passcodeEnabled, final String phoneNum, final String processor, final String subscriberNum) {

        ContentValues deviceValues = new ContentValues(16);

        deviceValues.put(McContract.Device.COLUMN_KIND, kind);
        deviceValues.put(McContract.Device.COLUMN_COMPLIANCE_STATUS, complianceStatus);
        deviceValues.put(McContract.Device.COLUMN_DEVICE_ID, deviceID);
        deviceValues.put(McContract.Device.COLUMN_DEVICE_NAME, deviceName);
        deviceValues.put(McContract.Device.COLUMN_ENROLLMENT_TIME, enrollmentTime);
        deviceValues.put(McContract.Device.COLUMN_FAMILY, family);
        deviceValues.put(McContract.Device.COLUMN_HOST_NAME, hostName);
        deviceValues.put(McContract.Device.COLUMN_AGENT_ONLINE, onLine);
        deviceValues.put(McContract.Device.COLUMN_VIRTUAL, virtual);
        deviceValues.put(McContract.Device.COLUMN_MAC_ADDRESS, mac);
        deviceValues.put(McContract.Device.COLUMN_MANUFACTURER, manufacturer);
        deviceValues.put(McContract.Device.COLUMN_MODE, mode);
        deviceValues.put(McContract.Device.COLUMN_MODEL, model);
        deviceValues.put(McContract.Device.COLUMN_OS_VERSION, osVersion);
        deviceValues.put(McContract.Device.COLUMN_PATH, path);
        deviceValues.put(McContract.Device.COLUMN_PLATFORM, platform);
        deviceValues.put(McContract.Device.COLUMN_AGENT_VERSION, agentVersion);
        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, availableExternalStorage);
        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, availableMemory);
        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, availableSdCardStorage);
        deviceValues.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, totExternalStorage);
        deviceValues.put(McContract.Device.COLUMN_TOTAL_MEMORY, totMemory);
        deviceValues.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, totSdCardStorage);
        deviceValues.put(McContract.Device.COLUMN_TOTAL_STORAGE, totStorage);
        deviceValues.put(McContract.Device.COLUMN_BACKUP_BATTERY_STATUS, backupBatteryStatus);
        deviceValues.put(McContract.Device.COLUMN_BATTERY_STATUS, batteryStatus);
        deviceValues.put(McContract.Device.COLUMN_CELLULAR_CARRIER, cellularCarrier);
        deviceValues.put(McContract.Device.COLUMN_CELLULAR_SIGNAL_STRENGTH, cellularSignalStrength);
        deviceValues.put(McContract.Device.COLUMN_DEVICE_TERMS, deviceTerms);
        deviceValues.put(McContract.Device.COLUMN_DEVICE_USER_INFO, deviceUserInfo);
        deviceValues.put(McContract.Device.COLUMN_EXCHANGE_BLOCKED, exchangeBlocked);
        deviceValues.put(McContract.Device.COLUMN_EXCHANGE_STATUS, exchangeStatus);
        deviceValues.put(McContract.Device.COLUMN_IMEI_MEID_ESN, imeiMeidEsn);
        deviceValues.put(McContract.Device.COLUMN_IN_ROAMING, inRoaming);
        deviceValues.put(McContract.Device.COLUMN_IPV6, ipv6);
        deviceValues.put(McContract.Device.COLUMN_IS_AGENT_COMPATIBLE, isAgentCompatible);
        deviceValues.put(McContract.Device.COLUMN_IS_AGENTLESS, isAgentless);
        deviceValues.put(McContract.Device.COLUMN_IS_LEARNING, isLearning);
        deviceValues.put(McContract.Device.COLUMN_LAST_CHECKIN_TIME, lastCheckin);
        deviceValues.put(McContract.Device.COLUMN_LAST_AGENT_CONNECTION_TIME, lastAgentConnection);
        deviceValues.put(McContract.Device.COLUMN_LAST_AGENT_DISCONNECTION_TIME, lastAgentDisconnection);
        deviceValues.put(McContract.Device.COLUMN_LAST_LOGGED_ON_AT, lastLoggedOnAt);
        deviceValues.put(McContract.Device.COLUMN_LAST_LOGGED_USER, lastLoggedUser);
        deviceValues.put(McContract.Device.COLUMN_NETWORK_CONNECTION_TYPE, networkConnectionType);
        deviceValues.put(McContract.Device.COLUMN_NETWORK_RSSI, networkRSSI);
        deviceValues.put(McContract.Device.COLUMN_NETWORK_SSID, networkSSID);
        deviceValues.put(McContract.Device.COLUMN_PASSCODE_ENABLED, passcodeEnabled);
        deviceValues.put(McContract.Device.COLUMN_PHONE_NUMBER, phoneNum);
        deviceValues.put(McContract.Device.COLUMN_PROCESSOR, processor);
        deviceValues.put(McContract.Device.COLUMN_SUBSCRIBER_NUMBER, subscriberNum);


        return deviceValues;
    }

}
