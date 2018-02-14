package com.mdmobile.pocketconsole.dataTypes;

/**
 * Device attributes is a list of possible attributes divided by platform
 * The first element of the array is the label as we receive it from API or DB.
 * The second is the corresponding label that we can use in UI to display the value
 */
@SuppressWarnings("unused")
public class DeviceAttributes {

    public  static class BasicDevice {
        public static String ComplianceStatus = "ComplianceStatus";
        public static String ComplianceItems = "ComplianceItems";
        public static String DeviceId = "DeviceId";
        public static String DeviceName = "DeviceName";
        public static String EnrollmentTime = "EnrollmentTime";
        public static String Family = "Family";
        public static String HostName = "HostName";
        public static String IsAgentOnline = "IsAgentOnline";
        public static String IsVirtual = "IsVirtual";
        public static String CustomAttributes = "CustomAttributes";
        public static String MACAddress = "MACAddress";
        public static String Manufacturer = "Manufacturer";
        public static String Mode = "Mode";
        public static String Model = "Model";
        public static String OSVersion = "OSVersion";
        public static String Path = "Path";
    }

    public static class AndroidPlusDevice extends BasicDevice {
        public static String AgentVersion = "AgentVersion";
        public static String AndroidDeviceAdmin = "AndroidDeviceAdmin";
        public static String Antivirus = "Antivirus";
        public static String Memory = "Memory";
        public static String BatteryStatus = "BatteryStatus";
        public static String CanResetPassword = "CanResetPassword";
        public static String CellularCarrier = "CellularCarrier";
        public static String CellularSignalStrength = "CellularSignalStrength";
        public static String CustomData = "CustomData";
        public static String DeviceTerms = "DeviceTerms";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String ExchangeBlocked = "ExchangeBlocked";
        public static String ExchangeStatus = "ExchangeStatus";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String HardwareSerialNumber = "HardwareSerialNumber";
        public static String HardwareVersion = "HardwareVersion";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String InRoaming = "InRoaming";
        public static String IntegratedApplications = "IntegratedApplications";
        public static String Ipv6 = "Ipv6";
        public static String IsAgentCompatible = "IsAgentCompatible";
        public static String IsAgentless = "IsAgentless";
        public static String IsEncrypted = "IsEncrypted";
        public static String IsOSSecure = "IsOSSecure";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String LastLoggedOnUser = "LastLoggedOnUser";
        public static String NetworkBSSID = "NetworkBSSID";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String NetworkRSSI = "NetworkRSSI";
        public static String NetworkSSID = "NetworkSSID";
        public static String OEMVersion = "OEMVersion";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PasscodeStatus = "PasscodeStatus";
        public static String PhoneNumber = "PhoneNumber";
        public static String SubscriberNumber = "SubscriberNumber";
        public static String SupportedApis = "SupportedApis";
    }

    public static class AfwDevice extends AndroidPlusDevice {
        public static String BluetoothMACAddress = "BluetoothMACAddress";
        public static String BuildVersion = "BuildVersion";
        public static String CellularTechnology = "CellularTechnology";
        public static String UserIdentities = "UserIdentities";
    }

    public static class AndroidElmDevice extends AndroidPlusDevice {
        public static String ElmStatus = "ElmStatus";
    }

    public static class KnoxDevice extends AndroidPlusDevice {
        public static String IntegrityServiceBaselineStatus = "IntegrityServiceBaselineStatus";
        public static String Knox = "Knox";
    }

    public static class AndroidGenericDevice extends BasicDevice {
        public static String AgentVersion = "AgentVersion";
        public static String AndroidDeviceAdmin = "AndroidDeviceAdmin";
        public static String Antivirus = "Antivirus";
        public static String Memory = "Memory";
        public static String BatteryStatus = "BatteryStatus";
        public static String CanResetPassword = "CanResetPassword";
        public static String CellularCarrier = "CellularCarrier";
        public static String CellularSignalStrength = "CellularSignalStrength";
        public static String CustomData = "CustomData";
        public static String DeviceTerms = "DeviceTerms";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String ExchangeBlocked = "ExchangeBlocked";
        public static String ExchangeStatus = "ExchangeStatus";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String InRoaming = "InRoaming";
        public static String Ipv6 = "Ipv6";
        public static String IsAgentCompatible = "IsAgentCompatible";
        public static String IsAgentless = "IsAgentless";
        public static String IsEncrypted = "IsEncrypted";
        public static String IsOSSecure = "IsOSSecure";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String LastLoggedOnUser = "LastLoggedOnUser";
        public static String NetworkBSSID = "NetworkBSSID";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String NetworkRSSI = "NetworkRSSI";
        public static String NetworkSSID = "NetworkSSID";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PhoneNumber = "PhoneNumber";
        public static String SubscriberNumber = "SubscriberNumber";
    }

    public static class IosDevice extends BasicDevice {
        public static String AgentVersion = "AgentVersion";
        public static String Memory = "Memory";
        public static String BatteryStatus = "BatteryStatus";
        public static String BluetoothMACAddress = "BluetoothMACAddress";
        public static String BuildVersion = "BuildVersion";
        public static String CarrierSettingsVersion = "CarrierSettingsVersion";
        public static String CellularCarrier = "CellularCarrier";
        public static String CellularTechnology = "CellularTechnology";
        public static String CurrentMCC = "CurrentMCC";
        public static String CurrentMNC = "CurrentMNC";
        public static String DataRoamingEnabled = "DataRoamingEnabled";
        public static String DeviceTerms = "DeviceTerms";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String ExchangeBlocked = "ExchangeBlocked";
        public static String ExchangeStatus = "ExchangeStatus";
        public static String FirmwareVersion = "FirmwareVersion";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String ICCID = "ICCID";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String InRoaming = "InRoaming";
        public static String Ipv6 = "Ipv6";
        public static String IsAgentCompatible = "IsAgentCompatible";
        public static String IsAgentless = "IsAgentless";
        public static String IsDeviceLocatorServiceEnabled = "IsDeviceLocatorServiceEnabled";
        public static String IsDoNotDisturbInEffect = "IsDoNotDisturbInEffect";
        public static String IsEncrypted = "IsEncrypted";
        public static String IsEnrolled = "IsEnrolled";
        public static String IsITunesStoreAccountActive = "IsITunesStoreAccountActive";
        public static String IsOSSecure = "IsOSSecure";
        public static String IsPersonalHotspotEnabled = "IsPersonalHotspotEnabled";
        public static String IsSupervised = "IsSupervised";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String LastLoggedOnUser = "LastLoggedOnUser";
        public static String LastStatusUpdate = "LastStatusUpdate";
        public static String ManufacturerSerialNumber = "ManufacturerSerialNumber";
        public static String ModelNumber = "ModelNumber";
        public static String ModemFirmwareVersion = "ModemFirmwareVersion";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PasscodeStatus = "PasscodeStatus";
        public static String PersonalizedName = "PersonalizedName";
        public static String PhoneNumber = "PhoneNumber";
        public static String ProductName = "ProductName";
        public static String SIMCarrierNetwork = "SIMCarrierNetwork";
        public static String SubscriberMCC = "SubscriberMCC";
        public static String SubscriberMNC = "SubscriberMNC";
        public static String SubscriberNumber = "SubscriberNumber";
        public static String UserIdHash = "UserIdHash";
        public static String VoiceRoamingEnabled = "VoiceRoamingEnabled";
    }

    public static class MacDevice extends BasicDevice {
        public static String AgentVersion = "AgentVersion";
        public static String Memory = "Memory";
        public static String BatteryStatus = "BatteryStatus";
        public static String BluetoothMACAddress = "BluetoothMACAddress";
        public static String BuildVersion = "BuildVersion";
        public static String DeviceTerms = "DeviceTerms";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String Ipv6 = "Ipv6";
        public static String IsAgentCompatible = "IsAgentCompatible";
        public static String IsAgentless = "IsAgentless";
        public static String IsITunesStoreAccountActive = "IsITunesStoreAccountActive";
        public static String IsPersonalHotspotEnabled = "IsPersonalHotspotEnabled";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String ManufacturerSerialNumber = "ManufacturerSerialNumber";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PersonalizedName = "PersonalizedName";
        public static String ProductName = "ProductName";
    }

    public static class WindowsMobileDevice extends BasicDevice {
        public static String AgentVersion = "AgentVersion";
        public static String Memory = "Memory";
        public static String BackupBatteryStatus = "BackupBatteryStatus";
        public static String BatteryStatus = "BatteryStatus";
        public static String CellularCarrier = "CellularCarrier";
        public static String CellularSignalStrength = "CellularSignalStrength";
        public static String CustomData = "CustomData";
        public static String DeviceTerms = "DeviceTerms";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String ExchangeBlocked = "ExchangeBlocked";
        public static String ExchangeStatus = "ExchangeStatus";
        public static String HardwareSerialNumber = "HardwareSerialNumber";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String InRoaming = "InRoaming";
        public static String Ipv6 = "Ipv6";
        public static String IsAgentCompatible = "IsAgentCompatible";
        public static String IsAgentless = "IsAgentless";
        public static String IsLearning = "IsLearning";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String LastLoggedOnAt = "LastLoggedOnAt";
        public static String LastLoggedOnUser = "LastLoggedOnUser";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String NetworkRSSI = "NetworkRSSI";
        public static String NetworkSSID = "NetworkSSID";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PhoneNumber = "PhoneNumber";
        public static String Processor = "Processor";
        public static String SubscriberNumber = "SubscriberNumber";
    }

    public static class WindowsDesktopDevice extends BasicDevice {
        public static String BiosVersion = "BiosVersion";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String DMRevision = "DMRevision";
        public static String FirmwareVersion = "FirmwareVersion";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String HardwareVersion = "HardwareVersion";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String InRoaming = "InRoaming";
        public static String Language = "Language";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PasscodeStatus = "PasscodeStatus";
        public static String ScreenResolution = "ScreenResolution";
        public static String SubscriberNumber = "SubscriberNumber";
        public static String TimeZone = "TimeZone";
    }

    public static class WindowsDesktopLegacyDevice extends BasicDevice {
        public static String AgentVersion = "AgentVersion";
        public static String Memory = "Memory";
        public static String BatteryStatus = "BatteryStatus";
        public static String CustomData = "CustomData";
        public static String DeviceTerms = "DeviceTerms";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String Ipv6 = "Ipv6";
        public static String IsAgentCompatible = "IsAgentCompatible";
        public static String IsAgentless = "IsAgentless";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String LastLoggedOnAt = "LastLoggedOnAt";
        public static String LastLoggedOnUser = "LastLoggedOnUser";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String NetworkRSSI = "NetworkRSSI";
        public static String NetworkSSID = "NetworkSSID";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String Processor = "Processor";
    }

    public static class WindowsPhoneDevice extends BasicDevice {
        public static String BiosVersion = "BiosVersion";
        public static String CellularCarrier = "CellularCarrier";
        public static String CpuId = "CpuId";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String DMRevision = "DMRevision";
        public static String FirmwareVersion = "FirmwareVersion";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String HardwareVersion = "HardwareVersion";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String IMEI_MEID_ESN_SIM2 = "IMEI_MEID_ESN_SIM2";
        public static String InRoaming = "InRoaming";
        public static String InRoamingSIM2 = "InRoamingSIM2";
        public static String Language = "Language";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PasscodeStatus = "PasscodeStatus";
        public static String PhoneNumber = "PhoneNumber";
        public static String PhoneNumberSIM2 = "PhoneNumberSIM2";
        public static String RadioVersion = "RadioVersion";
        public static String ScreenResolution = "ScreenResolution";
        public static String SIMCarrierNetwork = "SIMCarrierNetwork";
        public static String SubscriberNumber = "SubscriberNumber";
        public static String SubscriberNumberSIM2 = "SubscriberNumberSIM2";
        public static String TimeZone = "TimeZone";
    }

    public static class WindowsRunTimeDevice extends BasicDevice {
        public static String BatteryStatus = "BatteryStatus";
        public static String CellularCarrier = "CellularCarrier";
        public static String CompanyHubStatus = "CompanyHubStatus";
        public static String DeviceUserInfo = "DeviceUserInfo";
        public static String DMRevision = "DMRevision";
        public static String FirmwareVersion = "FirmwareVersion";
        public static String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        public static String HardwareVersion = "HardwareVersion";
        public static String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        public static String InRoaming = "InRoaming";
        public static String IsEncrypted = "IsEncrypted";
        public static String Language = "Language";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String PasscodeEnabled = "PasscodeEnabled";
        public static String PasscodeStatus = "PasscodeStatus";
        public static String ScreenResolution = "ScreenResolution";
        public static String SubscriberNumber = "SubscriberNumber";
        public static String TimeZone = "TimeZone";
    }

    public static class ZebraPrinterDevice extends BasicDevice {
        public static String Memory = "Memory";
        public static String BatteryStatus = "BatteryStatus";
        public static String DeviceUserRole = "DeviceUserRole";
        public static String FirmwareVersion = "FirmwareVersion";
        public static String LastCheckInTime = "LastCheckInTime";
        public static String LastAgentConnectTime = "LastAgentConnectTime";
        public static String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        public static String NetworkConnectionType = "NetworkConnectionType";
        public static String NetworkSSID = "NetworkSSID";
        public static String PrinterAdminServer = "PrinterAdminServer";
        public static String ProductIdentification = "ProductIdentification";
    }

}
