package com.mdmobile.pocketconsole.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Device {

    @SerializedName("Memory")
    public Memory memory;
    @SerializedName("CustomData")
    public List<CustomData> customDataList;
    @SerializedName("ComplianceItem")
    public List<ComplianceItem> complianceItemList;
    @SerializedName("DeviceUserInfo")
    public DeviceUserInfo deviceUserInfo;

    private String Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, Platform, AgentVersion, CellularCarrier,
            DeviceTerms, ExchangeStatus, HardwareSerialNumber, IMEI_MEID_ESN, Ipv6,
            LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime, LastLoggedOnAt, LastLoggedOnUser, NetworkConnectionType,
            NetworkRSSI, NetworkSSID, PhoneNumber, Processor, SubscriberNumber, BackupBatteryStatus, BatteryStatus, CellularSignalStrength;
    private Boolean ComplianceStatus, IsAgentOnline, PasscodeEnabled, IsVirtual, ExchangeBlocked, InRoaming, IsAgentCompatible, IsAgentless, IsLearning;


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
        if (BackupBatteryStatus == null) {
            return -1;
        } else
            return Integer.valueOf(BackupBatteryStatus);
    }

    public int getBatteryStatus() {
        if (BatteryStatus == null) {
            return -1;
        } else {
            return Integer.valueOf(BatteryStatus);
        }
    }

    public int getCellularSignalStrength() {
        if (CellularSignalStrength == null) {
            return -1;
        } else {
            return Integer.valueOf(CellularSignalStrength);
        }
    }

    public int getComplianceStatus() {
        if (ComplianceStatus == null) {
            return -1;
        } else {
            return ComplianceStatus ? 1 : 0;
        }
    }

    public int getAgentOnline() {
        if (IsAgentOnline == null) {
            return -1;
        } else {
        }
        return IsAgentOnline ? 1 : 0;
    }

    public int getPasscodeEnabled() {
        if (PasscodeEnabled == null) {
            return -1;
        } else {
        }
        return PasscodeEnabled ? 1 : 0;
    }

    public int getVirtual() {
        if (IsVirtual == null) {
            return -1;
        } else {

            return IsVirtual ? 1 : 0;
        }
    }

    public int getExchangeBlocked() {
        if (ExchangeBlocked == null) {
            return -1;
        } else {

            return ExchangeBlocked ? 1 : 0;
        }
    }

    public int getInRoaming() {
        if (InRoaming == null) {
            return -1;
        } else {
            return InRoaming ? 1 : 0;
        }
    }

    public int getAgentCompatible() {
        if (IsAgentCompatible == null) {
            return -1;
        } else {

            return IsAgentCompatible ? 1 : 0;
        }
    }

    public int getAgentless() {
        if (IsAgentless == null) {
            return -1;
        } else {
            return IsAgentless ? 1 : 0;
        }
    }

    public int getLearning() {
        if (IsLearning == null) {
            return -1;
        } else {
            return IsLearning ? 1 : 0;
        }
    }

    //Inner class for nested objects
    public class CustomAttributes {
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
    public class ComplianceItems {
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
        String AvailableExternalStorage, AvailableMemory, AvailableSDCardStorage, AvailableStorage,
                TotalExternalStorage, TotalMemory, TotalSDCardStorage, TotalStorage;

        public Memory(String availableExternalStorage, String availableMemory, String availableSDCardStorage,
                      String availableStorage, String totalExternalStorage, String totalMemory,
                      String totalSDCardStorage, String totalStorage) {
            AvailableExternalStorage = availableExternalStorage;
            AvailableMemory = availableMemory;
            AvailableSDCardStorage = availableSDCardStorage;
            AvailableStorage = availableStorage;
            TotalExternalStorage = totalExternalStorage;
            TotalMemory = totalMemory;
            TotalSDCardStorage = totalSDCardStorage;
            TotalStorage = totalStorage;
        }

        public long getAvailableExternalStorage() {
            if (AvailableExternalStorage == null) {
                return -1;
            } else {

                return Long.valueOf(AvailableExternalStorage);
            }
        }

        public long getAvailableMemory() {
            if (AvailableMemory == null) {
                return -1;
            } else {
                return Long.valueOf(AvailableMemory);
            }
        }

        public long getAvailableSDCardStorage() {
            if (AvailableSDCardStorage == null) {
                return -1;
            } else {
                return Long.valueOf(AvailableSDCardStorage);
            }
        }

        public long getAvailableStorage() {
            if (AvailableStorage == null) {
                return -1;
            } else {
                return Long.valueOf(AvailableStorage);
            }
        }

        public long getTotalExternalStorage() {
            if (TotalExternalStorage == null) {
                return -1;
            } else {
                return Long.valueOf(TotalExternalStorage);
            }
        }

        public long getTotalMemory() {
            if (TotalMemory == null) {
                return -1;
            } else {
                return Long.valueOf(TotalMemory);
            }
        }

        public long getTotalSDCardStorage() {
            if (TotalSDCardStorage == null) {
                return -1;
            } else {
                return Long.valueOf(TotalSDCardStorage);
            }
        }

        public long getTotalStorage() {
            if (TotalStorage == null) {
                return -1;
            } else {
                return Long.valueOf(TotalStorage);
            }
        }
    }

    public class DeviceUserInfo {
        private String UserName, FirstName, MiddleName, LastName, DomainName, UPN, PhoneNumber,
                Email, CustomProperty1, CustomProperty2, CustomProperty3, Identifier;

        public String getUserName() {
            return UserName;
        }

        public String getFirstName() {
            return FirstName;
        }

        public String getMiddleName() {
            return MiddleName;
        }

        public String getLastName() {
            return LastName;
        }

        public String getDomainName() {
            return DomainName;
        }

        public String getUPN() {
            return UPN;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public String getEmail() {
            return Email;
        }

        public String getCustomProperty1() {
            return CustomProperty1;
        }

        public String getCustomProperty2() {
            return CustomProperty2;
        }

        public String getCustomProperty3() {
            return CustomProperty3;
        }

        public String getIdentifier() {
            return Identifier;
        }
    }

    public class ComplianceItem {
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

    public class CustomData {
        //TODO:test device with custom data and populate this table
    }


}
