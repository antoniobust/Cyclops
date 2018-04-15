package com.mdmobile.cyclops.dataTypes

/**
 * Device attributes is a list of possible attributes divided by platform
 */
@Suppress("unused")
class DeviceAttributes {

    open class BasicDevice {
        companion object {
            var ComplianceStatus = "ComplianceStatus"
            var ComplianceItems = "ComplianceItems"
            var DeviceId = "DeviceId"
            var DeviceName = "DeviceName"
            var EnrollmentTime = "EnrollmentTime"
            var Family = "Family"
            var HostName = "HostName"
            var IsAgentOnline = "IsAgentOnline"
            var IsVirtual = "IsVirtual"
            var CustomAttributes = "CustomAttributes"
            var MACAddress = "MACAddress"
            var Manufacturer = "Manufacturer"
            var Mode = "Mode"
            var Model = "Model"
            var OSVersion = "OSVersion"
            var Path = "Path"
        }
    }

    open class AndroidPlusDevice : BasicDevice() {
        companion object {
            var AgentVersion = "AgentVersion"
            var AndroidDeviceAdmin = "AndroidDeviceAdmin"
            var Antivirus = "Antivirus"
            var Memory = "Memory"
            var BatteryStatus = "BatteryStatus"
            var CanResetPassword = "CanResetPassword"
            var CellularCarrier = "CellularCarrier"
            var CellularSignalStrength = "CellularSignalStrength"
            var CustomData = "CustomData"
            var DeviceTerms = "DeviceTerms"
            var DeviceUserInfo = "DeviceUserInfo"
            var ExchangeBlocked = "ExchangeBlocked"
            var ExchangeStatus = "ExchangeStatus"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var HardwareSerialNumber = "HardwareSerialNumber"
            var HardwareVersion = "HardwareVersion"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var InRoaming = "InRoaming"
            var IntegratedApplications = "IntegratedApplications"
            var Ipv6 = "Ipv6"
            var IsAgentCompatible = "IsAgentCompatible"
            var IsAgentless = "IsAgentless"
            var IsEncrypted = "IsEncrypted"
            var IsOSSecure = "IsOSSecure"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var LastLoggedOnUser = "LastLoggedOnUser"
            var NetworkBSSID = "NetworkBSSID"
            var NetworkConnectionType = "NetworkConnectionType"
            var NetworkRSSI = "NetworkRSSI"
            var NetworkSSID = "NetworkSSID"
            var OEMVersion = "OEMVersion"
            var PasscodeEnabled = "PasscodeEnabled"
            var PasscodeStatus = "PasscodeStatus"
            var PhoneNumber = "PhoneNumber"
            var SubscriberNumber = "SubscriberNumber"
            var SupportedApis = "SupportedApis"
        }
    }

    class AfwDevice : AndroidPlusDevice() {
        companion object {
            var BluetoothMACAddress = "BluetoothMACAddress"
            var BuildVersion = "BuildVersion"
            var CellularTechnology = "CellularTechnology"
            var UserIdentities = "UserIdentities"
        }
    }

    class AndroidElmDevice : AndroidPlusDevice() {
        companion object {
            var ElmStatus = "ElmStatus"
        }
    }

    class KnoxDevice : AndroidPlusDevice() {
        companion object {
            var IntegrityServiceBaselineStatus = "IntegrityServiceBaselineStatus"
            var Knox = "Knox"
        }
    }

    class AndroidGenericDevice : BasicDevice() {
        companion object {
            var AgentVersion = "AgentVersion"
            var AndroidDeviceAdmin = "AndroidDeviceAdmin"
            var Antivirus = "Antivirus"
            var Memory = "Memory"
            var BatteryStatus = "BatteryStatus"
            var CanResetPassword = "CanResetPassword"
            var CellularCarrier = "CellularCarrier"
            var CellularSignalStrength = "CellularSignalStrength"
            var CustomData = "CustomData"
            var DeviceTerms = "DeviceTerms"
            var DeviceUserInfo = "DeviceUserInfo"
            var ExchangeBlocked = "ExchangeBlocked"
            var ExchangeStatus = "ExchangeStatus"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var InRoaming = "InRoaming"
            var Ipv6 = "Ipv6"
            var IsAgentCompatible = "IsAgentCompatible"
            var IsAgentless = "IsAgentless"
            var IsEncrypted = "IsEncrypted"
            var IsOSSecure = "IsOSSecure"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var LastLoggedOnUser = "LastLoggedOnUser"
            var NetworkBSSID = "NetworkBSSID"
            var NetworkConnectionType = "NetworkConnectionType"
            var NetworkRSSI = "NetworkRSSI"
            var NetworkSSID = "NetworkSSID"
            var PasscodeEnabled = "PasscodeEnabled"
            var PhoneNumber = "PhoneNumber"
            var SubscriberNumber = "SubscriberNumber"
        }
    }

    class IosDevice : BasicDevice() {
        companion object {
            var AgentVersion = "AgentVersion"
            var Memory = "Memory"
            var BatteryStatus = "BatteryStatus"
            var BluetoothMACAddress = "BluetoothMACAddress"
            var BuildVersion = "BuildVersion"
            var CarrierSettingsVersion = "CarrierSettingsVersion"
            var CellularCarrier = "CellularCarrier"
            var CellularTechnology = "CellularTechnology"
            var CurrentMCC = "CurrentMCC"
            var CurrentMNC = "CurrentMNC"
            var DataRoamingEnabled = "DataRoamingEnabled"
            var DeviceTerms = "DeviceTerms"
            var DeviceUserInfo = "DeviceUserInfo"
            var ExchangeBlocked = "ExchangeBlocked"
            var ExchangeStatus = "ExchangeStatus"
            var FirmwareVersion = "FirmwareVersion"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var ICCID = "ICCID"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var InRoaming = "InRoaming"
            var Ipv6 = "Ipv6"
            var IsAgentCompatible = "IsAgentCompatible"
            var IsAgentless = "IsAgentless"
            var IsDeviceLocatorServiceEnabled = "IsDeviceLocatorServiceEnabled"
            var IsDoNotDisturbInEffect = "IsDoNotDisturbInEffect"
            var IsEncrypted = "IsEncrypted"
            var IsEnrolled = "IsEnrolled"
            var IsITunesStoreAccountActive = "IsITunesStoreAccountActive"
            var IsOSSecure = "IsOSSecure"
            var IsPersonalHotspotEnabled = "IsPersonalHotspotEnabled"
            var IsSupervised = "IsSupervised"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var LastLoggedOnUser = "LastLoggedOnUser"
            var LastStatusUpdate = "LastStatusUpdate"
            var ManufacturerSerialNumber = "ManufacturerSerialNumber"
            var ModelNumber = "ModelNumber"
            var ModemFirmwareVersion = "ModemFirmwareVersion"
            var NetworkConnectionType = "NetworkConnectionType"
            var PasscodeEnabled = "PasscodeEnabled"
            var PasscodeStatus = "PasscodeStatus"
            var PersonalizedName = "PersonalizedName"
            var PhoneNumber = "PhoneNumber"
            var ProductName = "ProductName"
            var SIMCarrierNetwork = "SIMCarrierNetwork"
            var SubscriberMCC = "SubscriberMCC"
            var SubscriberMNC = "SubscriberMNC"
            var SubscriberNumber = "SubscriberNumber"
            var UserIdHash = "UserIdHash"
            var VoiceRoamingEnabled = "VoiceRoamingEnabled"
        }
    }

    class MacDevice : BasicDevice() {
        companion object {
            var AgentVersion = "AgentVersion"
            var Memory = "Memory"
            var BatteryStatus = "BatteryStatus"
            var BluetoothMACAddress = "BluetoothMACAddress"
            var BuildVersion = "BuildVersion"
            var DeviceTerms = "DeviceTerms"
            var DeviceUserInfo = "DeviceUserInfo"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var Ipv6 = "Ipv6"
            var IsAgentCompatible = "IsAgentCompatible"
            var IsAgentless = "IsAgentless"
            var IsITunesStoreAccountActive = "IsITunesStoreAccountActive"
            var IsPersonalHotspotEnabled = "IsPersonalHotspotEnabled"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var ManufacturerSerialNumber = "ManufacturerSerialNumber"
            var NetworkConnectionType = "NetworkConnectionType"
            var PasscodeEnabled = "PasscodeEnabled"
            var PersonalizedName = "PersonalizedName"
            var ProductName = "ProductName"
        }
    }

    class WindowsMobileDevice : BasicDevice() {
        companion object {
            var AgentVersion = "AgentVersion"
            var Memory = "Memory"
            var BackupBatteryStatus = "BackupBatteryStatus"
            var BatteryStatus = "BatteryStatus"
            var CellularCarrier = "CellularCarrier"
            var CellularSignalStrength = "CellularSignalStrength"
            var CustomData = "CustomData"
            var DeviceTerms = "DeviceTerms"
            var DeviceUserInfo = "DeviceUserInfo"
            var ExchangeBlocked = "ExchangeBlocked"
            var ExchangeStatus = "ExchangeStatus"
            var HardwareSerialNumber = "HardwareSerialNumber"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var InRoaming = "InRoaming"
            var Ipv6 = "Ipv6"
            var IsAgentCompatible = "IsAgentCompatible"
            var IsAgentless = "IsAgentless"
            var IsLearning = "IsLearning"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var LastLoggedOnAt = "LastLoggedOnAt"
            var LastLoggedOnUser = "LastLoggedOnUser"
            var NetworkConnectionType = "NetworkConnectionType"
            var NetworkRSSI = "NetworkRSSI"
            var NetworkSSID = "NetworkSSID"
            var PasscodeEnabled = "PasscodeEnabled"
            var PhoneNumber = "PhoneNumber"
            var Processor = "Processor"
            var SubscriberNumber = "SubscriberNumber"
        }
    }

    class WindowsDesktopDevice : BasicDevice() {
        companion object {
            var BiosVersion = "BiosVersion"
            var DeviceUserInfo = "DeviceUserInfo"
            var DMRevision = "DMRevision"
            var FirmwareVersion = "FirmwareVersion"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var HardwareVersion = "HardwareVersion"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var InRoaming = "InRoaming"
            var Language = "Language"
            var PasscodeEnabled = "PasscodeEnabled"
            var PasscodeStatus = "PasscodeStatus"
            var ScreenResolution = "ScreenResolution"
            var SubscriberNumber = "SubscriberNumber"
            var TimeZone = "TimeZone"
        }
    }

    class WindowsDesktopLegacyDevice : BasicDevice() {
        companion object {
            var AgentVersion = "AgentVersion"
            var Memory = "Memory"
            var BatteryStatus = "BatteryStatus"
            var CustomData = "CustomData"
            var DeviceTerms = "DeviceTerms"
            var DeviceUserInfo = "DeviceUserInfo"
            var Ipv6 = "Ipv6"
            var IsAgentCompatible = "IsAgentCompatible"
            var IsAgentless = "IsAgentless"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var LastLoggedOnAt = "LastLoggedOnAt"
            var LastLoggedOnUser = "LastLoggedOnUser"
            var NetworkConnectionType = "NetworkConnectionType"
            var NetworkRSSI = "NetworkRSSI"
            var NetworkSSID = "NetworkSSID"
            var PasscodeEnabled = "PasscodeEnabled"
            var Processor = "Processor"
        }
    }

    class WindowsPhoneDevice : BasicDevice() {
        companion object {
            var BiosVersion = "BiosVersion"
            var CellularCarrier = "CellularCarrier"
            var CpuId = "CpuId"
            var DeviceUserInfo = "DeviceUserInfo"
            var DMRevision = "DMRevision"
            var FirmwareVersion = "FirmwareVersion"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var HardwareVersion = "HardwareVersion"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var IMEI_MEID_ESN_SIM2 = "IMEI_MEID_ESN_SIM2"
            var InRoaming = "InRoaming"
            var InRoamingSIM2 = "InRoamingSIM2"
            var Language = "Language"
            var PasscodeEnabled = "PasscodeEnabled"
            var PasscodeStatus = "PasscodeStatus"
            var PhoneNumber = "PhoneNumber"
            var PhoneNumberSIM2 = "PhoneNumberSIM2"
            var RadioVersion = "RadioVersion"
            var ScreenResolution = "ScreenResolution"
            var SIMCarrierNetwork = "SIMCarrierNetwork"
            var SubscriberNumber = "SubscriberNumber"
            var SubscriberNumberSIM2 = "SubscriberNumberSIM2"
            var TimeZone = "TimeZone"
        }
    }

    class WindowsRunTimeDevice : BasicDevice() {
        companion object {
            var BatteryStatus = "BatteryStatus"
            var CellularCarrier = "CellularCarrier"
            var CompanyHubStatus = "CompanyHubStatus"
            var DeviceUserInfo = "DeviceUserInfo"
            var DMRevision = "DMRevision"
            var FirmwareVersion = "FirmwareVersion"
            var HardwareEncryptionCaps = "HardwareEncryptionCaps"
            var HardwareVersion = "HardwareVersion"
            var IMEI_MEID_ESN = "IMEI_MEID_ESN"
            var InRoaming = "InRoaming"
            var IsEncrypted = "IsEncrypted"
            var Language = "Language"
            var NetworkConnectionType = "NetworkConnectionType"
            var PasscodeEnabled = "PasscodeEnabled"
            var PasscodeStatus = "PasscodeStatus"
            var ScreenResolution = "ScreenResolution"
            var SubscriberNumber = "SubscriberNumber"
            var TimeZone = "TimeZone"
        }
    }

    class ZebraPrinterDevice : BasicDevice() {
        companion object {
            var Memory = "Memory"
            var BatteryStatus = "BatteryStatus"
            var DeviceUserRole = "DeviceUserRole"
            var FirmwareVersion = "FirmwareVersion"
            var LastCheckInTime = "LastCheckInTime"
            var LastAgentConnectTime = "LastAgentConnectTime"
            var LastAgentDisconnectTime = "LastAgentDisconnectTime"
            var NetworkConnectionType = "NetworkConnectionType"
            var NetworkSSID = "NetworkSSID"
            var PrinterAdminServer = "PrinterAdminServer"
            var ProductIdentification = "ProductIdentification"
        }
    }

}
