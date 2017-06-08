package com.mdmobile.pocketconsole.gson.devices;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//TODO: support missing platform, only android ios added so far
public class BasicDevice {

    @SerializedName("Memory")
    public Memory memory;
    @SerializedName("ComplianceItem")
    public List<ComplianceItem> complianceItemList;
    @SerializedName("CustomAttributes")
    public List<CustomAttributes> customAttributesList;


    private String Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model,
            OSVersion, Path, Platform;
    private Boolean ComplianceStatus, IsAgentOnline, IsVirtual;

    public BasicDevice(String kind, String deviceId, String deviceName, String enrollmentTime, String family, String
            hostName, String MACAddress, String manufacturer, String mode, String model, String OSVersion,
                       String path, Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual, String Platform) {
        this.Kind = kind;
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
        ComplianceStatus = complianceStatus;
        IsAgentOnline = isAgentOnline;
        IsVirtual = isVirtual;
        this.Platform = Platform;
    }

    public String getPlatform() {
        return Platform;
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

    public int getComplianceStatus() {
        return ComplianceStatus ? 1 : 0;
    }

    public int getAgentOnline() {
        return IsAgentOnline ? 1 : 0;
    }

    public int getVirtual() {
        return IsVirtual ? 1 : 0;
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
        private long AvailableExternalStorage, AvailableMemory, AvailableSDCardStorage, AvailableStorage,
                TotalExternalStorage, TotalMemory, TotalSDCardStorage, TotalStorage;

        public Memory(long availableExternalStorage, long availableMemory, long availableSDCardStorage, long availableStorage,
                      long totalExternalStorage, long totalMemory, long totalSDCardStorage, long totalStorage) {
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
            return AvailableExternalStorage;
        }

        public long getAvailableMemory() {
            return AvailableMemory;
        }

        public long getAvailableSDCardStorage() {
            return AvailableSDCardStorage;
        }

        public long getAvailableStorage() {
            return AvailableStorage;
        }

        public long getTotalExternalStorage() {
            return TotalExternalStorage;
        }

        public long getTotalMemory() {
            return TotalMemory;
        }

        public long getTotalSDCardStorage() {
            return TotalSDCardStorage;
        }

        public long getTotalStorage() {
            return TotalStorage;
        }
    }


    public class ComplianceItem {
        private String ComplianceType;
        private Boolean ComplianceValue;

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
}
