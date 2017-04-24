package com.mdmobile.pocketconsole.gson;


public class Device {

    private String kind, deviceId, deviceName, enrollmentTime, family, hostName, macAddress, manufacturer, mode, model,
            osVersion, path, platform;
    private Boolean complianceStatus, isAgentOnline, isVirtual;

    public Device(String kind, String deviceId, String deviceName, String enrollmentTime, String family,
                  String hostName, String macAddress, String manufacturer, String mode, String model,
                  String osVersion, String path, String platform, Boolean complianceStatus,
                  Boolean isAgentOnline, Boolean isVirtual) {
        this.kind = kind;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.enrollmentTime = enrollmentTime;
        this.family = family;
        this.hostName = hostName;
        this.macAddress = macAddress;
        this.manufacturer = manufacturer;
        this.mode = mode;
        this.model = model;
        this.osVersion = osVersion;
        this.path = path;
        this.platform = platform;
        this.complianceStatus = complianceStatus;
        this.isAgentOnline = isAgentOnline;
        this.isVirtual = isVirtual;
    }

    public String getKind() {
        return kind;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getEnrollmentTime() {
        return enrollmentTime;
    }

    public String getFamily() {
        return family;
    }

    public String getHostName() {
        return hostName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMode() {
        return mode;
    }

    public String getModel() {
        return model;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getPath() {
        return path;
    }

    public String getPlatform() {
        return platform;
    }

    public Boolean getComplianceStatus() {
        return complianceStatus;
    }

    public Boolean getAgentOnline() {
        return isAgentOnline;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }


    //Inner class for nested objects
    public final class ComplianceItems {
        private String complianceType;
        private Boolean complianceValue;

        public ComplianceItems(String complianceType, Boolean complianceValue) {
            this.complianceType = complianceType;
            this.complianceValue = complianceValue;
        }

        public String getComplianceType() {
            return complianceType;
        }

        public Boolean getComplianceValue() {
            return complianceValue;
        }
    }

    //Inner class for nested objects
    public final class CustomAttributes {
        private String name, value;
        private Boolean dataType;

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

        public Boolean getDataType() {
            return dataType;
        }
    }


}
