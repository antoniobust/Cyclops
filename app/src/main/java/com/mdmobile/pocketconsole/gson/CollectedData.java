package com.mdmobile.pocketconsole.gson;


public class CollectedData {

    private String DeviceId, Kind, Timestamp;

    public CollectedData(String DeviceId, String Kind, String Timestamp) {
        this.DeviceId = DeviceId;
        this.Kind = Kind;
        this.Timestamp = Timestamp;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public String getKind() {
        return Kind;
    }

    public String getTimestamp() {
        return Timestamp;
    }
}
