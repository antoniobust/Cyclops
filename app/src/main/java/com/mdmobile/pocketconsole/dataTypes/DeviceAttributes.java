package com.mdmobile.pocketconsole.dataTypes;

/**
 * Device attributes is a list of possible attributes divided by platform
 * The first element of the array is the label as we receive it from API or DB.
 * The second is the corresponding label that we can use in UI to display the value
 */
@SuppressWarnings("unused")
public class DeviceAttributes {

    public static class BasicDevice {
        String ComplianceStatus = "ComplianceStatus";
        String ComplianceItems = "ComplianceItems";
        String DeviceId = "DeviceId";
        String DeviceName = "DeviceName";
        String EnrollmentTime = "EnrollmentTime";
        String Family = "Family";
        String HostName = "HostName";
        String IsAgentOnline = "IsAgentOnline";
        String IsVirtual = "IsVirtual";
        String CustomAttributes = "CustomAttributes";
        String MACAddress = "MACAddress";
        String Manufacturer = "Manufacturer";
        String Mode = "Mode";
        String Model = "Model";
        String OSVersion = "OSVersion";
        String Path = "Path";
    }

    public class AndroidPlusDevice extends BasicDevice {
        String AgentVersion = "AgentVersion";
        String AndroidDeviceAdmin = "AndroidDeviceAdmin";
        String Antivirus = "Antivirus";
        String Memory = "Memory";
        String BatteryStatus = "BatteryStatus";
        String CanResetPassword = "CanResetPassword";
        String CellularCarrier = "CellularCarrier";
        String CellularSignalStrength = "CellularSignalStrength";
        String CustomData = "CustomData";
        String DeviceTerms = "DeviceTerms";
        String DeviceUserInfo = "DeviceUserInfo";
        String ExchangeBlocked = "ExchangeBlocked";
        String ExchangeStatus = "ExchangeStatus";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String HardwareSerialNumber = "HardwareSerialNumber";
        String HardwareVersion = "HardwareVersion";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String InRoaming = "InRoaming";
        String IntegratedApplications = "IntegratedApplications";
        String Ipv6 = "Ipv6";
        String IsAgentCompatible = "IsAgentCompatible";
        String IsAgentless = "IsAgentless";
        String IsEncrypted = "IsEncrypted";
        String IsOSSecure = "IsOSSecure";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String LastLoggedOnUser = "LastLoggedOnUser";
        String NetworkBSSID = "NetworkBSSID";
        String NetworkConnectionType = "NetworkConnectionType";
        String NetworkRSSI = "NetworkRSSI";
        String NetworkSSID = "NetworkSSID";
        String OEMVersion = "OEMVersion";
        String PasscodeEnabled = "PasscodeEnabled";
        String PasscodeStatus = "PasscodeStatus";
        String PhoneNumber = "PhoneNumber";
        String SubscriberNumber = "SubscriberNumber";
        String SupportedApis = "SupportedApis";
    }

    public class AfwDevice extends AndroidPlusDevice {
        String BluetoothMACAddress = "BluetoothMACAddress";
        String BuildVersion = "BuildVersion";
        String CellularTechnology = "CellularTechnology";
        String UserIdentities = "UserIdentities";
    }

    public class AndroidElmDevice extends AndroidPlusDevice {
        String ElmStatus = "ElmStatus";
    }

    public class KnoxDevice extends AndroidPlusDevice {
        String IntegrityServiceBaselineStatus = "IntegrityServiceBaselineStatus";
        String Knox = "Knox";
    }

    public class AndroidGenericDevice extends BasicDevice {
        String AgentVersion = "AgentVersion";
        String AndroidDeviceAdmin = "AndroidDeviceAdmin";
        String Antivirus = "Antivirus";
        String Memory = "Memory";
        String BatteryStatus = "BatteryStatus";
        String CanResetPassword = "CanResetPassword";
        String CellularCarrier = "CellularCarrier";
        String CellularSignalStrength = "CellularSignalStrength";
        String CustomData = "CustomData";
        String DeviceTerms = "DeviceTerms";
        String DeviceUserInfo = "DeviceUserInfo";
        String ExchangeBlocked = "ExchangeBlocked";
        String ExchangeStatus = "ExchangeStatus";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String InRoaming = "InRoaming";
        String Ipv6 = "Ipv6";
        String IsAgentCompatible = "IsAgentCompatible";
        String IsAgentless = "IsAgentless";
        String IsEncrypted = "IsEncrypted";
        String IsOSSecure = "IsOSSecure";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String LastLoggedOnUser = "LastLoggedOnUser";
        String NetworkBSSID = "NetworkBSSID";
        String NetworkConnectionType = "NetworkConnectionType";
        String NetworkRSSI = "NetworkRSSI";
        String NetworkSSID = "NetworkSSID";
        String PasscodeEnabled = "PasscodeEnabled";
        String PhoneNumber = "PhoneNumber";
        String SubscriberNumber = "SubscriberNumber";
    }

    public class IosDevice extends BasicDevice {
        String AgentVersion = "AgentVersion";
        String Memory = "Memory";
        String BatteryStatus = "BatteryStatus";
        String BluetoothMACAddress = "BluetoothMACAddress";
        String BuildVersion = "BuildVersion";
        String CarrierSettingsVersion = "CarrierSettingsVersion";
        String CellularCarrier = "CellularCarrier";
        String CellularTechnology = "CellularTechnology";
        String CurrentMCC = "CurrentMCC";
        String CurrentMNC = "CurrentMNC";
        String DataRoamingEnabled = "DataRoamingEnabled";
        String DeviceTerms = "DeviceTerms";
        String DeviceUserInfo = "DeviceUserInfo";
        String ExchangeBlocked = "ExchangeBlocked";
        String ExchangeStatus = "ExchangeStatus";
        String FirmwareVersion = "FirmwareVersion";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String ICCID = "ICCID";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String InRoaming = "InRoaming";
        String Ipv6 = "Ipv6";
        String IsAgentCompatible = "IsAgentCompatible";
        String IsAgentless = "IsAgentless";
        String IsDeviceLocatorServiceEnabled = "IsDeviceLocatorServiceEnabled";
        String IsDoNotDisturbInEffect = "IsDoNotDisturbInEffect";
        String IsEncrypted = "IsEncrypted";
        String IsEnrolled = "IsEnrolled";
        String IsITunesStoreAccountActive = "IsITunesStoreAccountActive";
        String IsOSSecure = "IsOSSecure";
        String IsPersonalHotspotEnabled = "IsPersonalHotspotEnabled";
        String IsSupervised = "IsSupervised";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String LastLoggedOnUser = "LastLoggedOnUser";
        String LastStatusUpdate = "LastStatusUpdate";
        String ManufacturerSerialNumber = "ManufacturerSerialNumber";
        String ModelNumber = "ModelNumber";
        String ModemFirmwareVersion = "ModemFirmwareVersion";
        String NetworkConnectionType = "NetworkConnectionType";
        String PasscodeEnabled = "PasscodeEnabled";
        String PasscodeStatus = "PasscodeStatus";
        String PersonalizedName = "PersonalizedName";
        String PhoneNumber = "PhoneNumber";
        String ProductName = "ProductName";
        String SIMCarrierNetwork = "SIMCarrierNetwork";
        String SubscriberMCC = "SubscriberMCC";
        String SubscriberMNC = "SubscriberMNC";
        String SubscriberNumber = "SubscriberNumber";
        String UserIdHash = "UserIdHash";
        String VoiceRoamingEnabled = "VoiceRoamingEnabled";
    }

    public class MacDevice extends BasicDevice {
        String AgentVersion = "AgentVersion";
        String Memory = "Memory";
        String BatteryStatus = "BatteryStatus";
        String BluetoothMACAddress = "BluetoothMACAddress";
        String BuildVersion = "BuildVersion";
        String DeviceTerms = "DeviceTerms";
        String DeviceUserInfo = "DeviceUserInfo";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String Ipv6 = "Ipv6";
        String IsAgentCompatible = "IsAgentCompatible";
        String IsAgentless = "IsAgentless";
        String IsITunesStoreAccountActive = "IsITunesStoreAccountActive";
        String IsPersonalHotspotEnabled = "IsPersonalHotspotEnabled";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String ManufacturerSerialNumber = "ManufacturerSerialNumber";
        String NetworkConnectionType = "NetworkConnectionType";
        String PasscodeEnabled = "PasscodeEnabled";
        String PersonalizedName = "PersonalizedName";
        String ProductName = "ProductName";
    }

    public class WindowsMobileDevice extends BasicDevice {
        String AgentVersion = "AgentVersion";
        String Memory = "Memory";
        String BackupBatteryStatus = "BackupBatteryStatus";
        String BatteryStatus = "BatteryStatus";
        String CellularCarrier = "CellularCarrier";
        String CellularSignalStrength = "CellularSignalStrength";
        String CustomData = "CustomData";
        String DeviceTerms = "DeviceTerms";
        String DeviceUserInfo = "DeviceUserInfo";
        String ExchangeBlocked = "ExchangeBlocked";
        String ExchangeStatus = "ExchangeStatus";
        String HardwareSerialNumber = "HardwareSerialNumber";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String InRoaming = "InRoaming";
        String Ipv6 = "Ipv6";
        String IsAgentCompatible = "IsAgentCompatible";
        String IsAgentless = "IsAgentless";
        String IsLearning = "IsLearning";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String LastLoggedOnAt = "LastLoggedOnAt";
        String LastLoggedOnUser = "LastLoggedOnUser";
        String NetworkConnectionType = "NetworkConnectionType";
        String NetworkRSSI = "NetworkRSSI";
        String NetworkSSID = "NetworkSSID";
        String PasscodeEnabled = "PasscodeEnabled";
        String PhoneNumber = "PhoneNumber";
        String Processor = "Processor";
        String SubscriberNumber = "SubscriberNumber";
    }

    public class WindowsDesktopDevice extends BasicDevice {
        String BiosVersion = "BiosVersion";
        String DeviceUserInfo = "DeviceUserInfo";
        String DMRevision = "DMRevision";
        String FirmwareVersion = "FirmwareVersion";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String HardwareVersion = "HardwareVersion";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String InRoaming = "InRoaming";
        String Language = "Language";
        String PasscodeEnabled = "PasscodeEnabled";
        String PasscodeStatus = "PasscodeStatus";
        String ScreenResolution = "ScreenResolution";
        String SubscriberNumber = "SubscriberNumber";
        String TimeZone = "TimeZone";
    }

    public class WindowsDesktopLegacyDevice extends BasicDevice {
        String AgentVersion = "AgentVersion";
        String Memory = "Memory";
        String BatteryStatus = "BatteryStatus";
        String CustomData = "CustomData";
        String DeviceTerms = "DeviceTerms";
        String DeviceUserInfo = "DeviceUserInfo";
        String Ipv6 = "Ipv6";
        String IsAgentCompatible = "IsAgentCompatible";
        String IsAgentless = "IsAgentless";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String LastLoggedOnAt = "LastLoggedOnAt";
        String LastLoggedOnUser = "LastLoggedOnUser";
        String NetworkConnectionType = "NetworkConnectionType";
        String NetworkRSSI = "NetworkRSSI";
        String NetworkSSID = "NetworkSSID";
        String PasscodeEnabled = "PasscodeEnabled";
        String Processor = "Processor";
    }

    public class WindowsPhoneDevice extends BasicDevice {
        String BiosVersion = "BiosVersion";
        String CellularCarrier = "CellularCarrier";
        String CpuId = "CpuId";
        String DeviceUserInfo = "DeviceUserInfo";
        String DMRevision = "DMRevision";
        String FirmwareVersion = "FirmwareVersion";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String HardwareVersion = "HardwareVersion";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String IMEI_MEID_ESN_SIM2 = "IMEI_MEID_ESN_SIM2";
        String InRoaming = "InRoaming";
        String InRoamingSIM2 = "InRoamingSIM2";
        String Language = "Language";
        String PasscodeEnabled = "PasscodeEnabled";
        String PasscodeStatus = "PasscodeStatus";
        String PhoneNumber = "PhoneNumber";
        String PhoneNumberSIM2 = "PhoneNumberSIM2";
        String RadioVersion = "RadioVersion";
        String ScreenResolution = "ScreenResolution";
        String SIMCarrierNetwork = "SIMCarrierNetwork";
        String SubscriberNumber = "SubscriberNumber";
        String SubscriberNumberSIM2 = "SubscriberNumberSIM2";
        String TimeZone = "TimeZone";
    }

    public class WindowsRunTimeDevice extends BasicDevice {
        String BatteryStatus = "BatteryStatus";
        String CellularCarrier = "CellularCarrier";
        String CompanyHubStatus = "CompanyHubStatus";
        String DeviceUserInfo = "DeviceUserInfo";
        String DMRevision = "DMRevision";
        String FirmwareVersion = "FirmwareVersion";
        String HardwareEncryptionCaps = "HardwareEncryptionCaps";
        String HardwareVersion = "HardwareVersion";
        String IMEI_MEID_ESN = "IMEI_MEID_ESN";
        String InRoaming = "InRoaming";
        String IsEncrypted = "IsEncrypted";
        String Language = "Language";
        String NetworkConnectionType = "NetworkConnectionType";
        String PasscodeEnabled = "PasscodeEnabled";
        String PasscodeStatus = "PasscodeStatus";
        String ScreenResolution = "ScreenResolution";
        String SubscriberNumber = "SubscriberNumber";
        String TimeZone = "TimeZone";
    }

    public class ZebraPrinterDevice extends BasicDevice {
        String Memory = "Memory";
        String BatteryStatus = "BatteryStatus";
        String DeviceUserRole = "DeviceUserRole";
        String FirmwareVersion = "FirmwareVersion";
        String LastCheckInTime = "LastCheckInTime";
        String LastAgentConnectTime = "LastAgentConnectTime";
        String LastAgentDisconnectTime = "LastAgentDisconnectTime";
        String NetworkConnectionType = "NetworkConnectionType";
        String NetworkSSID = "NetworkSSID";
        String PrinterAdminServer = "PrinterAdminServer";
        String ProductIdentification = "ProductIdentification";
    }

}
