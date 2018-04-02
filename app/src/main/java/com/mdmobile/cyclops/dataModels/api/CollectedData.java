package com.mdmobile.cyclops.dataModels.api;


public class CollectedData {

    private String deviceId, kind, timestamp;

    public CollectedData(String deviceId, String kind, String timestamp) {
        this.deviceId = deviceId;
        this.kind = kind;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getKind() {
        return kind;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
