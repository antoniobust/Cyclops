package com.mdmobile.pocketconsole.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Device {
    private int BackupBatteryStatus, BatteryStatus, CellularSignalStrength;
    private String Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, Platform, AgentVersion, CellularCarrier,
            DeviceTerms, DeviceUserInfo, ExchangeStatus, HardwareSerialNumber, IMEI_MEID_ESN, Ipv6,
            LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime, LastLoggedOnAt, LastLoggedOnUser, NetworkConnectionType,
            NetworkRSSI, NetworkSSID, PhoneNumber, Processor, SubscriberNumber;
    private Boolean ComplianceStatus, IsAgentOnline, PasscodeEnabled, IsVirtual, ExchangeBlocked, InRoaming, IsAgentCompatible, IsAgentless, IsLearning;
    @SerializedName("Memory")
    private Memory memory;
    @SerializedName("CustomData")
    private List<CustomData> customDataList;
    @SerializedName("ComplianceItem")
    private List<ComplianceItem> complianceItemList;

    public Device(int backupBatteryStatus, int batteryStatus, int cellularSignalStrength, String kind,
                  String deviceId, String deviceName, String enrollmentTime, String family, String hostName,
                  String MACAddress, String manufacturer, String mode, String model, String OSVersion,
                  String path, String platform, String agentVersion, String cellularCarrier,
                  String deviceTerms, String deviceUserInfo, String exchangeStatus,
                  String hardwareSerialNumber, String IMEI_MEID_ESN, String ipv6, String lastCheckInTime,
                  String lastAgentConnectTime, String lastAgentDisconnectTime, String lastLoggedOnAt,
                  String lastLoggedOnUser, String networkConnectionType, String networkRSSI,
                  String networkSSID, String phoneNumber, String processor, String subscriberNumber,
                  Boolean complianceStatus, Boolean isAgentOnline, Boolean passcodeEnabled,
                  Boolean isVirtual, Boolean exchangeBlocked, Boolean inRoaming,
                  Boolean isAgentCompatible, Boolean isAgentless, Boolean isLearning,
                  Memory memory, List<CustomData> customDataList, List<ComplianceItem> complianceItemList) {
        BackupBatteryStatus = backupBatteryStatus;
        BatteryStatus = batteryStatus;
        CellularSignalStrength = cellularSignalStrength;
        Kind = kind;
        DeviceId = deviceId;
        DeviceName = deviceName;
        EnrollmentTime = enrollmentTime;
        Family = family;
        HostName = hostName;
        this.MACAddress = MACAddress;
        Manufacturer = manufacturer;
        Mode = mode;
        Model = model;
        this.OSVersion = OSVersion;
        Path = path;
        Platform = platform;
        AgentVersion = agentVersion;
        CellularCarrier = cellularCarrier;
        DeviceTerms = deviceTerms;
        DeviceUserInfo = deviceUserInfo;
        ExchangeStatus = exchangeStatus;
        HardwareSerialNumber = hardwareSerialNumber;
        this.IMEI_MEID_ESN = IMEI_MEID_ESN;
        Ipv6 = ipv6;
        LastCheckInTime = lastCheckInTime;
        LastAgentConnectTime = lastAgentConnectTime;
        LastAgentDisconnectTime = lastAgentDisconnectTime;
        LastLoggedOnAt = lastLoggedOnAt;
        LastLoggedOnUser = lastLoggedOnUser;
        NetworkConnectionType = networkConnectionType;
        NetworkRSSI = networkRSSI;
        NetworkSSID = networkSSID;
        PhoneNumber = phoneNumber;
        Processor = processor;
        SubscriberNumber = subscriberNumber;
        ComplianceStatus = complianceStatus;
        IsAgentOnline = isAgentOnline;
        PasscodeEnabled = passcodeEnabled;
        IsVirtual = isVirtual;
        ExchangeBlocked = exchangeBlocked;
        InRoaming = inRoaming;
        IsAgentCompatible = isAgentCompatible;
        IsAgentless = isAgentless;
        IsLearning = isLearning;
        this.memory = memory;
        this.customDataList = customDataList;
        this.complianceItemList = complianceItemList;
    }

    public List<ComplianceItem> getComplianceItemList() {
        return complianceItemList;
    }

    public Memory getMemory() {
        return memory;
    }

    public List<CustomData> getCustomDataList() {
        return customDataList;
    }

    public String getKind() {
        return Kind;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public String getEnrollmentTime() {
        return EnrollmentTime;
    }

    public String getFamily() {
        return Family;
    }

    public String getHostName() {
        return HostName;
    }

    public String getMACAddress() {
        return MACAddress;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public String getMode() {
        return Mode;
    }

    public String getModel() {
        return Model;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public String getPath() {
        return Path;
    }

    public String getPlatform() {
        return Platform;
    }

    public String getAgentVersion() {
        return AgentVersion;
    }

    public String getCellularCarrier() {
        return CellularCarrier;
    }

    public String getDeviceTerms() {
        return DeviceTerms;
    }

    public String getDeviceUserInfo() {
        return DeviceUserInfo;
    }

    public String getExchangeStatus() {
        return ExchangeStatus;
    }

    public String getHardwareSerialNumber() {
        return HardwareSerialNumber;
    }

    public String getIMEI_MEID_ESN() {
        return IMEI_MEID_ESN;
    }

    public String getIpv6() {
        return Ipv6;
    }

    public String getLastCheckInTime() {
        return LastCheckInTime;
    }

    public String getLastAgentConnectTime() {
        return LastAgentConnectTime;
    }

    public String getLastAgentDisconnectTime() {
        return LastAgentDisconnectTime;
    }

    public String getLastLoggedOnAt() {
        return LastLoggedOnAt;
    }

    public String getLastLoggedOnUser() {
        return LastLoggedOnUser;
    }

    public String getNetworkConnectionType() {
        return NetworkConnectionType;
    }

    public String getNetworkRSSI() {
        return NetworkRSSI;
    }

    public String getNetworkSSID() {
        return NetworkSSID;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getProcessor() {
        return Processor;
    }

    public String getSubscriberNumber() {
        return SubscriberNumber;
    }

    public int getBackupBatteryStatus() {
        return BackupBatteryStatus;
    }

    public int getBatteryStatus() {
        return BatteryStatus;
    }

    public int getCellularSignalStrength() {
        return CellularSignalStrength;
    }

    public int getComplianceStatus() {
        return ComplianceStatus ? 1 : 0;
    }

    public int getAgentOnline() {
        return IsAgentOnline ? 1 : 0;
    }

    public int getPasscodeEnabled() {
        return PasscodeEnabled ? 1 : 0;
    }

    public int getVirtual() {
        return IsVirtual ? 1 : 0;
    }

    public int getExchangeBlocked() {
        return ExchangeBlocked ? 1 : 0;
    }

    public int getInRoaming() {
        return InRoaming ? 1 : 0;
    }

    public int getAgentCompatible() {
        return IsAgentCompatible ? 1 : 0;
    }

    public int getAgentless() {
        return IsAgentless ? 1 : 0;
    }

    public int getLearning() {
        return IsLearning ? 1 : 0;
    }

    //Inner class for nested objects
    public static class CustomAttributes {
        String name, value;
        Boolean dataType;

        public CustomAttributes(String name, String value, Boolean dataType) {
            this.name = name;
            this.value = value;
            this.dataType = dataType;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public int getDataType() {
            return dataType ? 1 : 0;
        }
    }

    //Inner class for nested objects
    private class ComplianceItems {
        String ComplianceType;
        Boolean ComplianceValue;

        public ComplianceItems(String complianceType, Boolean complianceValue) {
            ComplianceType = complianceType;
            ComplianceValue = complianceValue;
        }

        public String getComplianceType() {
            return ComplianceType;
        }

        public int getComplianceValue() {
            return ComplianceValue ? 1 : 0;
        }
    }

    //Inner class for nested objects
    public class Memory {
        int AvailableExternalStorage, AvailableMemory, AvailableSDCardStorage, AvailableStorage,
                TotalExternalStorage, TotalMemory, TotalSDCardStorage, TotalStorage;

        public Memory(int availableExternalStorage, int availableMemory, int availableSDCardStorage,
                      int availableStorage, int totalExternalStorage, int totalMemory,
                      int totalSDCardStorage, int totalStorage) {
            AvailableExternalStorage = availableExternalStorage;
            AvailableMemory = availableMemory;
            AvailableSDCardStorage = availableSDCardStorage;
            AvailableStorage = availableStorage;
            TotalExternalStorage = totalExternalStorage;
            TotalMemory = totalMemory;
            TotalSDCardStorage = totalSDCardStorage;
            TotalStorage = totalStorage;
        }

        public int getAvailableExternalStorage() {
            return AvailableExternalStorage;
        }

        public int getAvailableMemory() {
            return AvailableMemory;
        }

        public int getAvailableSDCardStorage() {
            return AvailableSDCardStorage;
        }

        public int getAvailableStorage() {
            return AvailableStorage;
        }

        public int getTotalExternalStorage() {
            return TotalExternalStorage;
        }

        public int getTotalMemory() {
            return TotalMemory;
        }

        public int getTotalSDCardStorage() {
            return TotalSDCardStorage;
        }

        public int getTotalStorage() {
            return TotalStorage;
        }
    }

    private class ComplianceItem {
        String ComplianceType;
        Boolean ComplianceValue;

        public ComplianceItem(String complianceType, Boolean complianceValue) {
            ComplianceType = complianceType;
            ComplianceValue = complianceValue;
        }

        public String getComplianceType() {
            return ComplianceType;
        }

        public int getComplianceValue() {
            return ComplianceValue ? 1 : 0;
        }
    }

    private class CustomData {
        //TODO:test device with custom data and populate this table
    }


}
