package com.mdmobile.pocketconsole.dataTypes;


public class ComplexDataType {

    //Represent the possible option for DataCollection
    public enum BuiltInDataType {
        ForegroundApp,
        MissedCalls,
        SuccessCalls,
        DroppedCalls,
        AvailableExternalStorage,
        AvailableInternalStorage,
        OperatingSystemVersion,
        VoiceUsage,
        TrafficCellularTethered,
        TrafficCellularRoaming,
        TrafficCellular,
        TrafficWifi,
        TrafficUsb,
        BSSID,
        CallLog,
        CellularSignalStrength,
        CellularCarrier,
        IPAddress,
        RSSI,
        SSID,
        Location,
        AvailableStorage,
        AvailableMemory,
        BatteryStatus
    }

    public enum DirectoryResourceType {
        Both, User, Group
    }
}
