package com.mdmobile.pocketconsole.gson;

public class Device {
    int BackupBatteryStatus, BatteryStatus, CellularSignalStrength;
    private String Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, Platform, AgentVersion, CellularCarrier,
            DeviceTerms, DeviceUserInfo, ExchangeStatus, HardwareSerialNumber, IMEI_MEID_ESN, Ipv6,
            LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime, LastLoggedOnAt, LastLoggedOnUser, NetworkConnectionType,
            NetworkRSSI, NetworkSSID, PhoneNumber, Processor, SubscriberNumber;
    private Boolean ComplianceStatus, IsAgentOnline, PasscodeEnabled, IsVirtual, ExchangeBlocked, InRoaming, IsAgentCompatible, IsAgentless, IsLearning;

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
    public class Memory {
        int AvailableExternalStorage, AvailableMemory, AvailableSDCardStorage, AvailableStorage,
                TotalExternalStorage, TotalMemory, TotalSDCardStorage, TotalStorage;

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

    //Inner class for nested objects
    public class ComplianceItems {
        private String ComplianceType;
        private Boolean ComplianceValue;

        public String getComplianceType() {
            return ComplianceType;
        }

        public int getComplianceValue() {
            return ComplianceValue ? 1 : 0;
        }
    }

    //Inner class for nested objects
    public class CustomAttributes {
        private String name, value;
        private Boolean dataType;

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


}
