package com.mdmobile.pocketconsole.dataTypes;

/**
 * Device attributes is a list of possible attributes divided by platform
 * The first element of the array is the label as we receive it from API or DB.
 * The second is the corresponding label that we can use in UI to display the value
 */

public class DeviceAttributes {

    public final static int INTERNAL_LABEL = 0;
    public final static int UI_LABEL = 1;

    private interface BaseAttributes {
        String[] ComplianceStatus = {"ComplianceStatus", "Compliance status"};
        String[] ComplianceItems = {"ComplianceItems", "Compliance item"};
        String[] DeviceId = {"DeviceId", "Device ID"};
        String[] DeviceName = {"DeviceName", "Device name"};
        String[] EnrollmentTime = {"EnrollmentTime", "Enrollment Time"};
        String[] Family = {"Family", "Family"};
        String[] HostName = {"HostName", "Host name"};
        String[] IsAgentOnline = {"IsAgentOnline", "Online"};
        String[] IsVirtual = {"IsVirtual", "Virtual"};
        String[] CustomAttributes = {"CustomAttributes", "Custom attributes"};
        String[] MACAddress = {"MACAddress", "MAC Address"};
        String[] Manufacturer = {"Manufacturer", "Manufacturer"};
        String[] Mode = {"Mode", "Mode"};
        String[] Model = {"Model", "Model"};
        String[] OSVersion = {"OSVersion", "OS version"};
        String[] Path = {"Path", "Path"};
    }

    private interface AndroidPlusAttributes {
        String[] AgentVersion = {"AgentVersion", "Agent version"};
        String[] AndroidDeviceAdmin = {"AndroidDeviceAdmin", "Device admin"};
        String[] Antivirus = {"Antivirus", "Antivirus"};
        String[] Memory = {"Memory", "Memory"};
        String[] BatteryStatus = {"BatteryStatus", "Battery status"};
        String[] CanResetPassword = {"CanResetPassword", "Can reset password"};
        String[] CellularCarrier = {"CellularCarrier", "Cellular carrier"};
        String[] CellularSignalStrength = {"CellularSignalStrength", "Cellular signal strength"};
        String[] CustomData = {"CustomData", "Custom data"};
        String[] DeviceTerms = {"DeviceTerms", "Device terms"};
        String[] DeviceUserInfo = {"DeviceUserInfo", "Device user info"};
        String[] ExchangeBlocked = {"ExchangeBlocked", "Exchange blocked"};
        String[] ExchangeStatus = {"ExchangeStatus", "Exchange status"};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", "Hardware encryption caps"};
        String[] HardwareSerialNumber = {"HardwareSerialNumber", "Hardware serial number"};
        String[] HardwareVersion = {"HardwareVersion", "Hardware version"};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", "IMEI MEID ESN"};
        String[] InRoaming = {"InRoaming", "Roaming"};
        String[] IntegratedApplications = {"IntegratedApplications", "Integrated applications"};
        String[] Ipv6 = {"Ipv6", "Ipv6"};
        String[] IsAgentCompatible = {"IsAgentCompatible", "Agent compatible"};
        String[] IsAgentless = {"IsAgentless", "Agentless"};
        String[] IsEncrypted = {"IsEncrypted", "encrypted"};
        String[] IsOSSecure = {"IsOSSecure", "OS secure"};
        String[] LastCheckInTime = {"LastCheckInTime", "Last check in time"};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", "Last agent connection"};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", "Last agent disconnection"};
        String[] LastLoggedOnUser = {"LastLoggedOnUser", "Last logged on user"};
        String[] NetworkBSSID = {"NetworkBSSID", "Network BSSID"};
        String[] NetworkConnectionType = {"NetworkConnectionType", "Network connection type"};
        String[] NetworkRSSI = {"NetworkRSSI", "Network RSSI"};
        String[] NetworkSSID = {"NetworkSSID", "Network SSID"};
        String[] OEMVersion = {"OEMVersion", "OEM version"};
        String[] PasscodeEnabled = {"PasscodeEnabled", "Passcode enabled"};
        String[] PasscodeStatus = {"PasscodeStatus", "Passcode status"};
        String[] PhoneNumber = {"PhoneNumber", "Phone number"};
        String[] SubscriberNumber = {"SubscriberNumber", "Subscriber number"};
        String[] SupportedApis = {"SupportedApis", "Supported APIs"};
    }

    private interface AfwAttributes {
        String[] BluetoothMACAddress = {"BluetoothMACAddress", "Bluetooth MAC address"};
        String[] BuildVersion = {"BuildVersion", "Build version"};
        String[] CellularTechnology = {"CellularTechnology", "Cellular technology"};
        String[] UserIdentities = {"UserIdentities", "User identities"};
    }

    private interface AndroidElmAttributes {
        String[] ElmStatus = {"ElmStatus", "ELM status"};
    }

    private interface AndroidKnoxAttributes {
        String[] IntegrityServiceBaselineStatus = {"IntegrityServiceBaselineStatus", "Integrity service baseline status"};
        String[] Knox = {"Knox", "Knox"};
    }

    private interface AndroidGenericAttributes {
        String[] AgentVersion = {"AgentVersion", "Agent version"};
        String[] AndroidDeviceAdmin = {"AndroidDeviceAdmin", "Android device admin"};
        String[] Antivirus = {"Antivirus", "Antivirus"};
        String[] Memory = {"Memory", "Memory"};
        String[] BatteryStatus = {"BatteryStatus", "Battery status"};
        String[] CanResetPassword = {"CanResetPassword", "Can reset password"};
        String[] CellularCarrier = {"CellularCarrier", "Cellular carrier"};
        String[] CellularSignalStrength = {"CellularSignalStrength", "Signal strength"};
        String[] CustomData = {"CustomData", "Custom data"};
        String[] DeviceTerms = {"DeviceTerms", "Device terms"};
        String[] DeviceUserInfo = {"DeviceUserInfo", "Device user info"};
        String[] ExchangeBlocked = {"ExchangeBlocked", "Exchange blocked"};
        String[] ExchangeStatus = {"ExchangeStatus", "Exchange status"};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", "Hardware encryption caps"};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", "IMEI MEID ESN"};
        String[] InRoaming = {"InRoaming", "Roaming"};
        String[] Ipv6 = {"Ipv6", "Ipv6"};
        String[] IsAgentCompatible = {"IsAgentCompatible", "Agent compatible"};
        String[] IsAgentless = {"IsAgentless", "Agentless"};
        String[] IsEncrypted = {"IsEncrypted", "Encrypted"};
        String[] IsOSSecure = {"IsOSSecure", "OS secure"};
        String[] LastCheckInTime = {"LastCheckInTime", "Last check in time"};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", "Last agent Connection"};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", "Last agent disconnection"};
        String[] LastLoggedOnUser = {"LastLoggedOnUser", "Last logged on user"};
        String[] NetworkBSSID = {"NetworkBSSID", "Network BSSID"};
        String[] NetworkConnectionType = {"NetworkConnectionType", "Network Connection type"};
        String[] NetworkRSSI = {"NetworkRSSI", "Network RSSI"};
        String[] NetworkSSID = {"NetworkSSID", "Network SSID"};
        String[] PasscodeEnabled = {"PasscodeEnabled", "Passcode enabled"};
        String[] PhoneNumber = {"PhoneNumber", "Phone number"};
        String[] SubscriberNumber = {"SubscriberNumber", "Subscriber number"};
    }

    private interface iOSAttributes {
        String[] AgentVersion = {"AgentVersion", ""};
        String[] Memory = {"Memory", ""};
        String[] BatteryStatus = {"BatteryStatus", ""};
        String[] BluetoothMACAddress = {"BluetoothMACAddress", ""};
        String[] BuildVersion = {"BuildVersion", ""};
        String[] CarrierSettingsVersion = {"CarrierSettingsVersion", ""};
        String[] CellularCarrier = {"CellularCarrier", ""};
        String[] CellularTechnology = {"CellularTechnology", ""};
        String[] CurrentMCC = {"CurrentMCC", ""};
        String[] CurrentMNC = {"CurrentMNC", ""};
        String[] DataRoamingEnabled = {"DataRoamingEnabled", ""};
        String[] DeviceTerms = {"DeviceTerms", ""};
        String[] DeviceUserInfo = {"DeviceUserInfo", ""};
        String[] ExchangeBlocked = {"ExchangeBlocked", ""};
        String[] ExchangeStatus = {"ExchangeStatus", ""};
        String[] FirmwareVersion = {"FirmwareVersion", ""};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", ""};
        String[] ICCID = {"ICCID", ""};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", ""};
        String[] InRoaming = {"InRoaming", ""};
        String[] Ipv6 = {"Ipv6", ""};
        String[] IsAgentCompatible = {"IsAgentCompatible", ""};
        String[] IsAgentless = {"IsAgentless", ""};
        String[] IsDeviceLocatorServiceEnabled = {"IsDeviceLocatorServiceEnabled", ""};
        String[] IsDoNotDisturbInEffect = {"IsDoNotDisturbInEffect", ""};
        String[] IsEncrypted = {"IsEncrypted", ""};
        String[] IsEnrolled = {"IsEnrolled", ""};
        String[] IsITunesStoreAccountActive = {"IsITunesStoreAccountActive", ""};
        String[] IsOSSecure = {"IsOSSecure", ""};
        String[] IsPersonalHotspotEnabled = {"IsPersonalHotspotEnabled", ""};
        String[] IsSupervised = {"IsSupervised", ""};
        String[] LastCheckInTime = {"LastCheckInTime", ""};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", ""};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", ""};
        String[] LastLoggedOnUser = {"LastLoggedOnUser", ""};
        String[] LastStatusUpdate = {"LastStatusUpdate", ""};
        String[] ManufacturerSerialNumber = {"ManufacturerSerialNumber", ""};
        String[] ModelNumber = {"ModelNumber", ""};
        String[] ModemFirmwareVersion = {"ModemFirmwareVersion", ""};
        String[] NetworkConnectionType = {"NetworkConnectionType", ""};
        String[] PasscodeEnabled = {"PasscodeEnabled", ""};
        String[] PasscodeStatus = {"PasscodeStatus", ""};
        String[] PersonalizedName = {"PersonalizedName", ""};
        String[] PhoneNumber = {"PhoneNumber", ""};
        String[] ProductName = {"ProductName", ""};
        String[] SIMCarrierNetwork = {"SIMCarrierNetwork", ""};
        String[] SubscriberMCC = {"SubscriberMCC", ""};
        String[] SubscriberMNC = {"SubscriberMNC", ""};
        String[] SubscriberNumber = {"SubscriberNumber", ""};
        String[] UserIdHash = {"UserIdHash", ""};
        String[] VoiceRoamingEnabled = {"VoiceRoamingEnabled", ""};
    }

    private interface MacAttributes {
        String[] AgentVersion = {"AgentVersion", ""};
        String[] Memory = {"Memory", ""};
        String[] BatteryStatus = {"BatteryStatus", ""};
        String[] BluetoothMACAddress = {"BluetoothMACAddress", ""};
        String[] BuildVersion = {"BuildVersion", ""};
        String[] DeviceTerms = {"DeviceTerms", ""};
        String[] DeviceUserInfo = {"DeviceUserInfo", ""};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", ""};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", ""};
        String[] Ipv6 = {"Ipv6", ""};
        String[] IsAgentCompatible = {"IsAgentCompatible", ""};
        String[] IsAgentless = {"IsAgentless", ""};
        String[] IsITunesStoreAccountActive = {"IsITunesStoreAccountActive", ""};
        String[] IsPersonalHotspotEnabled = {"IsPersonalHotspotEnabled", ""};
        String[] LastCheckInTime = {"LastCheckInTime", ""};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", ""};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", ""};
        String[] ManufacturerSerialNumber = {"ManufacturerSerialNumber", ""};
        String[] NetworkConnectionType = {"NetworkConnectionType", ""};
        String[] PasscodeEnabled = {"PasscodeEnabled", ""};
        String[] PersonalizedName = {"PersonalizedName", ""};
        String[] ProductName = {"ProductName", ""};
    }

    private interface WindowsMobileCeAttributes {
        String[] AgentVersion = {"AgentVersion", "Agent version"};
        String[] Memory = {"Memory", "Memory"};
        String[] BackupBatteryStatus = {"BackupBatteryStatus", "Backup battery status"};
        String[] BatteryStatus = {"BatteryStatus", "Battery status"};
        String[] CellularCarrier = {"CellularCarrier", "Cellular carrier"};
        String[] CellularSignalStrength = {"CellularSignalStrength", "Cellular carrier strength"};
        String[] CustomData = {"CustomData", "Custom data"};
        String[] DeviceTerms = {"DeviceTerms", "Device terms"};
        String[] DeviceUserInfo = {"DeviceUserInfo", "Device user info"};
        String[] ExchangeBlocked = {"ExchangeBlocked", "Exchange blocked"};
        String[] ExchangeStatus = {"ExchangeStatus", "Exchange status"};
        String[] HardwareSerialNumber = {"HardwareSerialNumber", "Hardware serial number"};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", "IMEI(MEID ESN)"};
        String[] InRoaming = {"InRoaming", "Is roaming"};
        String[] Ipv6 = {"Ipv6", "IPv6"};
        String[] IsAgentCompatible = {"IsAgentCompatible", "Is agent compatible"};
        String[] IsAgentless = {"IsAgentless", "Is agentless"};
        String[] IsLearning = {"IsLearning", "Is learning"};
        String[] LastCheckInTime = {"LastCheckInTime", "Last checkin time"};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", "Last Agent connection"};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", "Last agent disconnection"};
        String[] LastLoggedOnAt = {"LastLoggedOnAt", "Last logon"};
        String[] LastLoggedOnUser = {"LastLoggedOnUser", "Last logged user"};
        String[] NetworkConnectionType = {"NetworkConnectionType", "Network connection type"};
        String[] NetworkRSSI = {"NetworkRSSI", "Network RSSI"};
        String[] NetworkSSID = {"NetworkSSID", "Network SSID"};
        String[] PasscodeEnabled = {"PasscodeEnabled", "Is passcode enabled"};
        String[] PhoneNumber = {"PhoneNumber", "Phone number"};
        String[] Processor = {"Processor", "Processor"};
        String[] SubscriberNumber = {"SubscriberNumber", "Subscriber number"};
    }

    private interface WindowsDesktopAttributes {
        String[] BiosVersion = {"BiosVersion", ""};
        String[] DeviceUserInfo = {"DeviceUserInfo", ""};
        String[] DMRevision = {"DMRevision", ""};
        String[] FirmwareVersion = {"FirmwareVersion", ""};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", ""};
        String[] HardwareVersion = {"HardwareVersion", ""};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", ""};
        String[] InRoaming = {"InRoaming", ""};
        String[] Language = {"Language", ""};
        String[] PasscodeEnabled = {"PasscodeEnabled", ""};
        String[] PasscodeStatus = {"PasscodeStatus", ""};
        String[] ScreenResolution = {"ScreenResolution", ""};
        String[] SubscriberNumber = {"SubscriberNumber", ""};
        String[] TimeZone = {"TimeZone", ""};
    }

    private interface WindowsDesktopLegacyAttributes {
        String[] AgentVersion = {"AgentVersion", ""};
        String[] Memory = {"Memory", ""};
        String[] BatteryStatus = {"BatteryStatus", ""};
        String[] CustomData = {"CustomData", ""};
        String[] DeviceTerms = {"DeviceTerms", ""};
        String[] DeviceUserInfo = {"DeviceUserInfo", ""};
        String[] Ipv6 = {"Ipv6", ""};
        String[] IsAgentCompatible = {"IsAgentCompatible", ""};
        String[] IsAgentless = {"IsAgentless", ""};
        String[] LastCheckInTime = {"LastCheckInTime", ""};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", ""};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", ""};
        String[] LastLoggedOnAt = {"LastLoggedOnAt", ""};
        String[] LastLoggedOnUser = {"LastLoggedOnUser", ""};
        String[] NetworkConnectionType = {"NetworkConnectionType", ""};
        String[] NetworkRSSI = {"NetworkRSSI", ""};
        String[] NetworkSSID = {"NetworkSSID", ""};
        String[] PasscodeEnabled = {"PasscodeEnabled", ""};
        String[] Processor = {"Processor", ""};
    }

    private interface WindowsPhoneAttributes {
        String[] BiosVersion = {"BiosVersion", "BIOS version"};
        String[] CellularCarrier = {"CellularCarrier", "Cellular carrier"};
        String[] CpuId = {"CpuId", "CPU id"};
        String[] DeviceUserInfo = {"DeviceUserInfo", "Device user info"};
        String[] DMRevision = {"DMRevision", "DM revision"};
        String[] FirmwareVersion = {"FirmwareVersion", "Firmware version"};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", "Hardware encryption caps"};
        String[] HardwareVersion = {"HardwareVersion", "Hardware version"};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", "IMEI (MEID ESN)"};
        String[] IMEI_MEID_ESN_SIM2 = {"IMEI_MEID_ESN_SIM2", "IMEI(MEID ESN SIM2)"};
        String[] InRoaming = {"InRoaming", "Roaming"};
        String[] InRoamingSIM2 = {"InRoamingSIM2", "SIM2 in roaming"};
        String[] Language = {"Language", "Language"};
        String[] PasscodeEnabled = {"PasscodeEnabled", "Passcode enabled"};
        String[] PasscodeStatus = {"PasscodeStatus", "Passcode status"};
        String[] PhoneNumber = {"PhoneNumber", "Phone number"};
        String[] PhoneNumberSIM2 = {"PhoneNumberSIM2", "Phone number SIM2"};
        String[] RadioVersion = {"RadioVersion", "Radio version"};
        String[] ScreenResolution = {"ScreenResolution", "Screen resolution"};
        String[] SIMCarrierNetwork = {"SIMCarrierNetwork", "SIM carrier"};
        String[] SubscriberNumber = {"SubscriberNumber", "Subscriber number"};
        String[] SubscriberNumberSIM2 = {"SubscriberNumberSIM2", "Subscriber num SIM2"};
        String[] TimeZone = {"TimeZone", "Time zone"};

    }

    private interface WindowsRunTimeAttributes {
        String[] BatteryStatus = {"BatteryStatus", ""};
        String[] CellularCarrier = {"CellularCarrier", ""};
        String[] CompanyHubStatus = {"CompanyHubStatus", ""};
        String[] DeviceUserInfo = {"DeviceUserInfo", ""};
        String[] DMRevision = {"DMRevision", ""};
        String[] FirmwareVersion = {"FirmwareVersion", ""};
        String[] HardwareEncryptionCaps = {"HardwareEncryptionCaps", "Hardware encryption caps"};
        String[] HardwareVersion = {"HardwareVersion", ""};
        String[] IMEI_MEID_ESN = {"IMEI_MEID_ESN", "IMEI MEID ESN"};
        String[] InRoaming = {"InRoaming", "Roaming"};
        String[] IsEncrypted = {"IsEncrypted", "Is encrypted"};
        String[] Language = {"Language", "Language"};
        String[] NetworkConnectionType = {"NetworkConnectionType", "Network connection type"};
        String[] PasscodeEnabled = {"PasscodeEnabled", "Passcode enabled"};
        String[] PasscodeStatus = {"PasscodeStatus", "Passcode status"};
        String[] ScreenResolution = {"ScreenResolution", "Screen resolution"};
        String[] SubscriberNumber = {"SubscriberNumber", "Subscriber number"};
        String[] TimeZone = {"TimeZone", ""};

    }

    private interface ZebraPrintersAttributes {
        String[] Memory = {"Memory", "Memory"};
        String[] BatteryStatus = {"BatteryStatus", "Battery status"};
        String[] DeviceUserRole = {"DeviceUserRole", "Device user role"};
        String[] FirmwareVersion = {"FirmwareVersion", "Firmware version"};
        String[] LastCheckInTime = {"LastCheckInTime", "Last check in"};
        String[] LastAgentConnectTime = {"LastAgentConnectTime", "Last agent connection"};
        String[] LastAgentDisconnectTime = {"LastAgentDisconnectTime", "Last disconnection time"};
        String[] NetworkConnectionType = {"NetworkConnectionType", "Network connection type"};
        String[] NetworkSSID = {"NetworkSSID", "Network SSID"};
        String[] PrinterAdminServer = {"PrinterAdminServer", "Printer admin server"};
        String[] ProductIdentification = {"ProductIdentification", "Product identification"};
    }

    public class BasicDevice implements BaseAttributes {
        public BasicDevice() {
        }
    }

    public class AndroidPlusDevice extends BasicDevice implements AndroidPlusAttributes {
        public AndroidPlusDevice() {
            super();
        }
    }

    public class AfwDevice extends AndroidPlusDevice implements AfwAttributes {
        public AfwDevice() {
            super();
        }
    }

    public class AndroidElmDevice extends AndroidPlusDevice implements AndroidElmAttributes {
        public AndroidElmDevice() {
            super();
        }
    }

    public class KnoxDevice extends AndroidPlusDevice implements AndroidKnoxAttributes {
        public KnoxDevice() {
            super();
        }
    }

    public class AndroidGenericDevice extends BasicDevice implements AndroidGenericAttributes {
        public AndroidGenericDevice() {
            super();
        }
    }

    public class IosDevice extends BasicDevice implements iOSAttributes {
        public IosDevice() {
            super();
        }
    }

    public class MacDevice extends BasicDevice implements MacAttributes {
        public MacDevice() {
            super();
        }
    }

    public class WindowsMobileDevice extends BasicDevice implements WindowsMobileCeAttributes {
        public WindowsMobileDevice() {
            super();
        }
    }

    public class WindowsDesktopDevice extends BasicDevice implements WindowsDesktopAttributes {
        public WindowsDesktopDevice() {
            super();
        }
    }

    public class WindowsDesktopLegacyDevice extends BasicDevice implements WindowsDesktopLegacyAttributes {
        public WindowsDesktopLegacyDevice() {
            super();
        }
    }

    public class WindowsPhoneDevice extends BasicDevice implements WindowsPhoneAttributes {
        public WindowsPhoneDevice() {
            super();
        }
    }

    public class WindowsRunTimeDevice extends BasicDevice implements WindowsRunTimeAttributes {
        public WindowsRunTimeDevice() {
            super();
        }
    }

    public class ZebraPrinterDevice extends BasicDevice implements ZebraPrintersAttributes {
        public ZebraPrinterDevice() {
            super();
        }
    }

}
