package com.mdmobile.pocketconsole.utils;

import android.content.ContentValues;

import com.mdmobile.pocketconsole.gson.Device;
import com.mdmobile.pocketconsole.provider.McContract;

/**
 * Helper methods to get data ready for DB
 */

public class DbData {

    public static ContentValues getDeviceContentValues(Device device) {
        ContentValues deviceValues = new ContentValues(16);


        deviceValues.put(McContract.Device.COLUMN_KIND, device.getKind());
        deviceValues.put(McContract.Device.COLUMN_COMPLIANCE_STATUS, device.getComplianceStatus());
        deviceValues.put(McContract.Device.COLUMN_DEVICE_ID, device.getDeviceId());
        deviceValues.put(McContract.Device.COLUMN_DEVICE_NAME, device.getDeviceName());
        deviceValues.put(McContract.Device.COLUMN_ENROLLMENT_TIME, device.getEnrollmentTime());
        deviceValues.put(McContract.Device.COLUMN_FAMILY, device.getFamily());
        deviceValues.put(McContract.Device.COLUMN_HOST_NAME, device.getHostName());
        deviceValues.put(McContract.Device.COLUMN_AGENT_ONLINE, device.getAgentOnline());
        deviceValues.put(McContract.Device.COLUMN_VIRTUAL, device.getVirtual());
        deviceValues.put(McContract.Device.COLUMN_MAC_ADDRESS, device.getMACAddress());
        deviceValues.put(McContract.Device.COLUMN_MANUFACTURER, device.getManufacturer());
        deviceValues.put(McContract.Device.COLUMN_MODE, device.getMode());
        deviceValues.put(McContract.Device.COLUMN_MODEL, device.getModel());
        deviceValues.put(McContract.Device.COLUMN_OS_VERSION, device.getOSVersion());
        deviceValues.put(McContract.Device.COLUMN_PATH, device.getPath());
        deviceValues.put(McContract.Device.COLUMN_PLATFORM, device.getPlatform());
        deviceValues.put(McContract.Device.COLUMN_AGENT_VERSION, device.getAgentVersion());
        if(device.getMemory() != null) {
            deviceValues.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, device.getMemory().getAvailableExternalStorage());
            deviceValues.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, device.getMemory().getAvailableMemory());
            deviceValues.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, device.getMemory().getAvailableSDCardStorage());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, device.getMemory().getTotalExternalStorage());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_MEMORY, device.getMemory().getTotalMemory());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, device.getMemory().getTotalSDCardStorage());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_STORAGE, device.getMemory().getTotalStorage());
        }
        deviceValues.put(McContract.Device.COLUMN_BACKUP_BATTERY_STATUS, device.getBackupBatteryStatus());
        deviceValues.put(McContract.Device.COLUMN_BATTERY_STATUS, device.getBatteryStatus());
        deviceValues.put(McContract.Device.COLUMN_CELLULAR_CARRIER, device.getCellularCarrier());
        deviceValues.put(McContract.Device.COLUMN_CELLULAR_SIGNAL_STRENGTH, device.getCellularSignalStrength());
        deviceValues.put(McContract.Device.COLUMN_DEVICE_TERMS, device.getDeviceTerms());
        deviceValues.put(McContract.Device.COLUMN_EXCHANGE_BLOCKED, device.getExchangeBlocked());
        deviceValues.put(McContract.Device.COLUMN_EXCHANGE_STATUS, device.getExchangeStatus());
        deviceValues.put(McContract.Device.COLUMN_IMEI_MEID_ESN, device.getIMEI_MEID_ESN());
        deviceValues.put(McContract.Device.COLUMN_IN_ROAMING, device.getInRoaming());
        deviceValues.put(McContract.Device.COLUMN_IPV6, device.getIpv6());
        deviceValues.put(McContract.Device.COLUMN_IS_AGENT_COMPATIBLE, device.getAgentCompatible());
        deviceValues.put(McContract.Device.COLUMN_IS_AGENTLESS, device.getAgentless());
        deviceValues.put(McContract.Device.COLUMN_IS_LEARNING, device.getLearning());
        deviceValues.put(McContract.Device.COLUMN_LAST_CHECKIN_TIME, device.getLastCheckInTime());
        deviceValues.put(McContract.Device.COLUMN_LAST_AGENT_CONNECTION_TIME, device.getLastAgentConnectTime());
        deviceValues.put(McContract.Device.COLUMN_LAST_AGENT_DISCONNECTION_TIME, device.getLastAgentDisconnectTime());
        deviceValues.put(McContract.Device.COLUMN_LAST_LOGGED_ON_AT, device.getLastLoggedOnAt());
        deviceValues.put(McContract.Device.COLUMN_LAST_LOGGED_USER, device.getLastLoggedOnUser());
        deviceValues.put(McContract.Device.COLUMN_NETWORK_CONNECTION_TYPE, device.getNetworkConnectionType());
        deviceValues.put(McContract.Device.COLUMN_NETWORK_RSSI, device.getNetworkRSSI());
        deviceValues.put(McContract.Device.COLUMN_NETWORK_SSID, device.getNetworkSSID());
        deviceValues.put(McContract.Device.COLUMN_PASSCODE_ENABLED, device.getPasscodeEnabled());
        deviceValues.put(McContract.Device.COLUMN_PHONE_NUMBER, device.getPhoneNumber());
        deviceValues.put(McContract.Device.COLUMN_PROCESSOR, device.getProcessor());
        deviceValues.put(McContract.Device.COLUMN_SUBSCRIBER_NUMBER, device.getSubscriberNumber());


        return deviceValues;
    }

    public static ContentValues[] getListOfDeviceContentValues(Device[] devices) {
        ContentValues[] values = new ContentValues[devices.length];
        for (int i = 0; i < devices.length; i++) {
            values[i] = getDeviceContentValues(devices[i]);
        }
        return values;
    }

}
