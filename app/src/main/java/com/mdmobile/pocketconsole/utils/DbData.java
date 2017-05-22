package com.mdmobile.pocketconsole.utils;

import android.content.ContentValues;

import com.mdmobile.pocketconsole.gson.devices.BasicDevice;
import com.mdmobile.pocketconsole.gson.devices.IosDevice;
import com.mdmobile.pocketconsole.provider.McContract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Helper methods to get data ready for DB
 */

public class DbData {


    public static ContentValues getDeviceContentValues(BasicDevice device, String extraInfo) {
        ContentValues deviceValues = new ContentValues();

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
//        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, device.memory.getAvailableExternalStorage());
//        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, device.memory.getAvailableMemory());
//        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, device.memory.getAvailableSDCardStorage());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, device.memory.getTotalExternalStorage());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_MEMORY, device.memory.getTotalMemory());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, device.memory.getTotalSDCardStorage());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_STORAGE, device.memory.getTotalStorage());
        deviceValues.put(McContract.Device.COLUMN_PLATFORM, device.getPlatform());
        deviceValues.put(McContract.Device.COLUMN_EXTRA_INFO, extraInfo);

        return deviceValues;
    }

    public static ContentValues[] getListOfDeviceContentValues(BasicDevice[] devices, String extraInfo) {
        ContentValues[] values = new ContentValues[devices.length];
        for (int i = 0; i < devices.length; i++) {
            values[i] = getDeviceContentValues(devices[i], extraInfo);
        }
        return values;
    }

    public static void formatDeviceData(BasicDevice device) {

        ContentValues deviceBasicValues = getDeviceBasicValues(device);

        StringBuilder extraStringBuilder = new StringBuilder();

        try {
            Class<?> c = Class.forName(device.getClass().getName());
            Method[] methods = c.getDeclaredMethods();
            String append;
            Object o;

            for (int i = 0; i < methods.length; i++) {
                //TODO:need to find a nicer way of doing this
                if (!methods[i].getName().startsWith("get") && !methods[i].getName().startsWith("is")) {
                    continue;
                }

                o = methods[i].invoke(device);
                if (o == null) {
                    append = "null";
                } else {
                    append = o.toString();
                }
                extraStringBuilder.append(methods[i].getName()).append("=");
                extraStringBuilder.append(append).append(",");
            }
            extraStringBuilder.toString();

        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static ContentValues getDeviceBasicValues(BasicDevice device) {
        ContentValues deviceValues = new ContentValues();

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
//        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, device.memory.getAvailableExternalStorage());
//        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, device.memory.getAvailableMemory());
//        deviceValues.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, device.memory.getAvailableSDCardStorage());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, device.memory.getTotalExternalStorage());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_MEMORY, device.memory.getTotalMemory());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, device.memory.getTotalSDCardStorage());
//        deviceValues.put(McContract.Device.COLUMN_TOTAL_STORAGE, device.memory.getTotalStorage());
        deviceValues.put(McContract.Device.COLUMN_PLATFORM, device.getPlatform());

        return deviceValues;
    }

}
