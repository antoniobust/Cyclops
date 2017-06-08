package com.mdmobile.pocketconsole.gson;


public class InstalledApp {

    private String DeviceId, ApplicationId, Name, Version, ShortVersion, SizeInBytes, DataSizeInBytes,
            Status;

    public InstalledApp(String deviceId, String applicationId, String name, String version,
                        String shortVersion, String sizeInBytes, String dataSizeInBytes, String status) {
        this.DeviceId = deviceId;
        this.ApplicationId = applicationId;
        this.Name = name;
        this.Version = version;
        this.ShortVersion = shortVersion;
        this.SizeInBytes = sizeInBytes;
        this.DataSizeInBytes = dataSizeInBytes;
        this.Status = status;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public String getApplicationId() {
        return ApplicationId;
    }

    public String getName() {
        return Name;
    }

    public String getVersion() {
        return Version;
    }

    public String getShortVersion() {
        return ShortVersion;
    }

    public String getSizeInBytes() {
        return SizeInBytes;
    }

    public String getDataSizeInBytes() {
        return DataSizeInBytes;
    }

    public String getStatus() {
        return Status;
    }
}
