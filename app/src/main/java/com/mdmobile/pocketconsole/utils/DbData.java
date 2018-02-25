package com.mdmobile.pocketconsole.utils;

import android.content.ContentValues;
import android.os.Bundle;

import com.mdmobile.pocketconsole.dataModels.api.InstalledApp;
import com.mdmobile.pocketconsole.dataModels.api.Profile;
import com.mdmobile.pocketconsole.dataModels.api.ServerInfo;
import com.mdmobile.pocketconsole.dataModels.api.User;
import com.mdmobile.pocketconsole.dataModels.api.devices.BasicDevice;
import com.mdmobile.pocketconsole.provider.McContract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Helper methods to get data ready for DB
 */

public class DbData {

    public static ContentValues[] prepareDeviceValues(ArrayList<BasicDevice> devices) {
        //TODO: not sure why gson sometimes skips some indexes so need to check here if the object exists at this index
        // Workaround: collect devices in an array list of content values and then convert it to content values array
        // to pass it to content provider

        ArrayList<ContentValues> values1 = new ArrayList<>();
        for (BasicDevice device : devices) {
            if (device != null) {
                values1.add(prepareDeviceValues(device));
            }
        }
        ContentValues[] values = new ContentValues[values1.size()];
        values1.toArray(values);
        return values;
    }

    public static ContentValues prepareDeviceValues(BasicDevice device) {

        ContentValues deviceBasicValues = getDeviceBasicValues(device);

        try {
            Class<?> c = Class.forName(device.getClass().getName());
            Method[] methods = c.getMethods();
            String append, propertyName;
            StringBuilder extraStringBuilder = new StringBuilder();

            Object o;

            for (Method method : methods) {
                //Getter method form gson classes don't have any parameter
                if (method.getGenericParameterTypes().length > 0) {
                    continue;
                }

                //Skip BasicDevice methods and Objects methods
                if (method.getDeclaringClass() == BasicDevice.class || method.getDeclaringClass() == Object.class) {
                    continue;
                }

                o = method.invoke(device);
                if (o == null) {
                    append = "null";
                } else {
                    append = o.toString();
                }
                propertyName = method.getName();
                if (propertyName.startsWith("get")) {
                    propertyName = propertyName.substring(2);
                } else if (propertyName.startsWith("is")) {
                    propertyName = propertyName.substring(1);
                }
                extraStringBuilder.append(propertyName).append("=").append(append).append(";");
            }

            deviceBasicValues.put(McContract.Device.COLUMN_EXTRA_INFO, extraStringBuilder.toString());

        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return deviceBasicValues;
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
        if (device.getMemory() != null) {
            deviceValues.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, device.getMemory().getAvailableExternalStorage());
            deviceValues.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, device.getMemory().getAvailableMemory());
            deviceValues.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, device.getMemory().getAvailableSDCardStorage());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, device.getMemory().getTotalExternalStorage());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_MEMORY, device.getMemory().getTotalMemory());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, device.getMemory().getTotalSDCardStorage());
            deviceValues.put(McContract.Device.COLUMN_TOTAL_STORAGE, device.getMemory().getTotalStorage());
        }


        return deviceValues;
    }


    private static ContentValues getInstalledAppContentValues(InstalledApp app) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(McContract.InstalledApplications.DEVICE_ID, app.getDeviceId());
        contentValues.put(McContract.InstalledApplications.APPLICATION_ID, app.getApplicationId());
        contentValues.put(McContract.InstalledApplications.APPLICATION_NAME, app.getName());
        contentValues.put(McContract.InstalledApplications.APPLICATION_VERSION, app.getShortVersion());
        contentValues.put(McContract.InstalledApplications.APPLICATION_BUILD_NUMBER, app.getVersion());
        contentValues.put(McContract.InstalledApplications.APPLICATION_SIZE, app.getSizeInBytes());
        contentValues.put(McContract.InstalledApplications.APPLICATION_DATA_USED, app.getDataSizeInBytes());
        contentValues.put(McContract.InstalledApplications.APPLICATION_STATUS, app.getStatus());

        return contentValues;
    }

    public static ContentValues[] prepareInstalledAppValues(ArrayList<InstalledApp> installedApps) {
        ArrayList<ContentValues> values = new ArrayList<>(installedApps.size());
        for (int i = 0; i < installedApps.size(); i++) {
            values.add(getInstalledAppContentValues(installedApps.get(i)));
        }
        ContentValues[] values1 = new ContentValues[values.size()];
        values.toArray(values1);
        return values1;
    }

    public static ContentValues prepareInstalledAppValues(InstalledApp installedApp) {
        return getInstalledAppContentValues(installedApp);
    }

    public static ContentValues[] prepareProfilesValue(ArrayList<Profile> profiles) {
        ArrayList<ContentValues> values = new ArrayList<>(profiles.size());
        for (int i = 0; i < profiles.size(); i++) {
            values.add(prepareProfilesValue(profiles.get(i)));
        }
        ContentValues[] values1 = new ContentValues[values.size()];
        values.toArray(values1);
        return values1;
    }

    public static ContentValues prepareProfilesValue(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(McContract.Profile.REFERENCE_ID, profile.getReferenceId());
        values.put(McContract.Profile.NAME, profile.getName());
        values.put(McContract.Profile.STATUS, profile.getStatus());
        values.put(McContract.Profile.VERSION_NUMBER, profile.getVersionNumber());
        values.put(McContract.Profile.ASSIGNMENT_DATE, profile.getAssignmentDate());
        values.put(McContract.Profile.IS_MANDATORY, profile.getMandatory());
        return values;
    }

    public static ContentValues prepareScriptValues(String name, String description, String script) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(McContract.Script.TITLE, name);
        contentValues.put(McContract.Script.DESCRIPTION, description);
        contentValues.put(McContract.Script.SCRIPT, script);
        return contentValues;
    }


    private static ContentValues getMsContentValues(ServerInfo.ManagementServer server) {
        ContentValues msValues = new ContentValues();
        msValues.put(McContract.MsInfo.NAME, server.getName());
        msValues.put(McContract.MsInfo.FULLY_QUALIFIED_NAME, server.getFqdn());
        msValues.put(McContract.MsInfo.DESCRIPTION, server.getDescription());
        msValues.put(McContract.MsInfo.MAC_ADDRESS, server.getMacAddress());
        msValues.put(McContract.MsInfo.PORT_NUMBER, server.getPortNumber());
        msValues.put(McContract.MsInfo.STATUS_TIME, server.getStatusTime());
        msValues.put(McContract.MsInfo.STATUS, server.getStatus());
        msValues.put(McContract.MsInfo.TOTAL_USER_COUNT, server.getTotalConsoleUsers());
        return msValues;
    }

    public static ContentValues prepareMsValues(ServerInfo.ManagementServer server) {
        return getMsContentValues(server);
    }

    public static ContentValues[] prepareMsValues(ArrayList<ServerInfo.ManagementServer> servers) {
        ContentValues[] msValuesArray = new ContentValues[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            msValuesArray[i] = getMsContentValues(servers.get(i));
        }
        return msValuesArray;
    }


    private static ContentValues getDsContentValues(ServerInfo.DeploymentServer server) {
        ContentValues values = new ContentValues();
        values.put(McContract.DsInfo.NAME, server.getName());
        values.put(McContract.DsInfo.STATUS, server.getStatus());
        values.put(McContract.DsInfo.CONNECTED, server.getConnected() ? 1 : 0);
        values.put(McContract.DsInfo.PRIMARY_AGENT_ADDRESS, server.getPrimaryAgentAddress());
        values.put(McContract.DsInfo.SECONDARY_AGENT_ADDRESS, server.getSecondaryAgentAddress());
        values.put(McContract.DsInfo.DEVICE_MANAGEMENT_ADDRESS, server.getDeviceManagementAddress());
        values.put(McContract.DsInfo.PRIMARY_MANAGEMENT_ADDRESS, server.getPrimaryManagementAddress());
        values.put(McContract.DsInfo.SECONDARY_MANAGEMENT_ADDRESS, server.getSecondaryManagementAddress());
        values.put(McContract.DsInfo.RULE_RELOAD, server.getRuleReload());
        values.put(McContract.DsInfo.SCHEDULE_INTERVAL, server.getScheduleInterval());
        values.put(McContract.DsInfo.MIN_THREADS, server.getMinThreads());
        values.put(McContract.DsInfo.MAX_THREADS, server.getMaxThread());
        values.put(McContract.DsInfo.MAX_BURST_THREADS, server.getMaxBurstThreads());
        values.put(McContract.DsInfo.DEVICES_CONNECTED, server.getConnectedDeviceCount());
        values.put(McContract.DsInfo.MANAGERS_CONNECTED, server.getConnectedManagerCount());
        values.put(McContract.DsInfo.QUEUE_LENGTH, server.getMsgQueueLength());
        values.put(McContract.DsInfo.CURRENT_THREAD_COUNT, server.getMsgQueueLength());
        values.put(McContract.DsInfo.PULSE_WAIT_INTERVAL, server.getPulseWaitInterval());

        return values;
    }

    public static ContentValues prepareDsValues(ServerInfo.DeploymentServer server) {
        return getDsContentValues(server);
    }

    public static ContentValues[] prepareDsValues(ArrayList<ServerInfo.DeploymentServer> servers) {
        ContentValues[] dsValuesArray = new ContentValues[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            dsValuesArray[i] = getDsContentValues(servers.get(i));
        }
        return dsValuesArray;
    }

    private static ContentValues getUserContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(McContract.UserInfo.NAME, user.getName());
        values.put(McContract.UserInfo.DISPLAYED_NAME, user.getDisplayName());
        values.put(McContract.UserInfo.KIND, user.getKind());
        values.put(McContract.UserInfo.IS_EULA_ACCEPTED, user.getEulaAccepted());
        values.put(McContract.UserInfo.EULA_ACCEPTANCE_DATE, user.getEulaAcceptanceDate());
        values.put(McContract.UserInfo.IS_LOCKED, user.getAccountLocked());
        values.put(McContract.UserInfo.NUMBER_OF_FAILED_LOGIN, user.getNumberOfFailedLogins());

        return values;
    }

    public static ContentValues prepareUserValues(User user) {
        return getUserContentValues(user);
    }

    public static ContentValues[] prepareUserValues(ArrayList<User> users) {
        ContentValues[] userValues = new ContentValues[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userValues[i] = getUserContentValues(users.get(i));
        }
        return userValues;
    }

    public static Bundle getDeviceExtraInfo(String extraInfo) {
        String[] extras = extraInfo.split(";");
        String[] temp;
        Bundle extraBundle = new Bundle();
        for (String extra : extras) {
            temp = extra.split("=");
            if (temp.length == 1) {
                extraBundle.putString(temp[0], "N/A");
                continue;
            }
            temp[0] = temp[0].substring(1, temp[0].length());
            extraBundle.putString(temp[0], temp[1]);
        }
        return extraBundle;
    }

    public static ContentValues prepareServerInfoValues(ServerInfo serverInfo) {
        ContentValues serverValues = new ContentValues();
        serverValues.put(McContract.ServerInfo.PRODUCT_VERSION, serverInfo.getProductVersion());
        serverValues.put(McContract.ServerInfo.PRODUCT_BUILD_NUMBER, serverInfo.getProductVersionBuild());
        return serverValues;
    }
}
