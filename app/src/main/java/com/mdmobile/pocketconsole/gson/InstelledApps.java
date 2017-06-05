package com.mdmobile.pocketconsole.gson;

/**
 * Installed app gson class
 */

public class InstelledApps {

    private String DeviceId, ApplicationId, Name, Version,
            ShortVersion, SizeInBytes, DataSizeInBytes, Status;

    public InstelledApps(String deviceId, String applicationId, String name, String version, String shortVersion,
                         String sizeInBytes, String dataSizeInBytes, String status) {
        DeviceId = deviceId;
        ApplicationId = applicationId;
        Name = name;
        Version = version;
        ShortVersion = shortVersion;
        SizeInBytes = sizeInBytes;
        DataSizeInBytes = dataSizeInBytes;
        Status = status;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public void setApplicationId(String applicationId) {
        ApplicationId = applicationId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public void setShortVersion(String shortVersion) {
        ShortVersion = shortVersion;
    }

    public void setSizeInBytes(String sizeInBytes) {
        SizeInBytes = sizeInBytes;
    }

    public void setDataSizeInBytes(String dataSizeInBytes) {
        DataSizeInBytes = dataSizeInBytes;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
