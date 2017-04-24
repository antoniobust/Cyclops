package com.mdmobile.pocketconsole.gson;


public class InstalledApp {

    private String deviceId, applicationId, name, version, shortVersion, sizeInBytes, dataSizeInBytes,
            status;

    public InstalledApp(String deviceId, String applicationId, String name, String version,
                        String shortVersion, String sizeInBytes, String dataSizeInBytes, String status) {
        this.deviceId = deviceId;
        this.applicationId = applicationId;
        this.name = name;
        this.version = version;
        this.shortVersion = shortVersion;
        this.sizeInBytes = sizeInBytes;
        this.dataSizeInBytes = dataSizeInBytes;
        this.status = status;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getShortVersion() {
        return shortVersion;
    }

    public String getSizeInBytes() {
        return sizeInBytes;
    }

    public String getDataSizeInBytes() {
        return dataSizeInBytes;
    }

    public String getStatus() {
        return status;
    }
}
