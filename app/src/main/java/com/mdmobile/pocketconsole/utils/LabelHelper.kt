package com.mdmobile.pocketconsole.utils

/**
 * Helper class to manage properties internal and UI labels
 */

class LabelHelper {

    companion object {

        fun getUiLabelFor(property: String): String {
            val filter: (Property) -> Boolean = {
                it.internalLabel == property
            }
            val s = BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.single(filter).uiLabel
            return if (s != "") {
                s
            } else {
                SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES.single(filter).uiLabel
            }
        }

        fun getInternalLabelFor(property: String): String {
            val filter: (Property) -> Boolean = {
                it.uiLabel == property
            }
            val s = BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.single(filter).internalLabel
            return if (s != "") {
                s
            } else {
                SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES.single(filter).internalLabel
            }
        }

        fun getStatisticProperties(): ArrayList<Property> {
            val filter: (Property) -> Boolean = {
                it.statisticable
            }
            return (BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.filter(filter) +
                    SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES.filter(filter)) as ArrayList<Property>
        }
    }

    object BasicDeviceProperties {
        var BASIC_DEVICE_PROPERTIES = ArrayList<Property>()

        init {
            BASIC_DEVICE_PROPERTIES.add(Property("ComplianceStatus", "Compliance status", true))
            BASIC_DEVICE_PROPERTIES.add(Property("ComplianceItems", "Compliance item"))
            BASIC_DEVICE_PROPERTIES.add(Property("DeviceId", "Device ID"))
            BASIC_DEVICE_PROPERTIES.add(Property("DeviceName", "Device name"))
            BASIC_DEVICE_PROPERTIES.add(Property("EnrollmentTime", "Enrollment Time", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Family", "Family", true))
            BASIC_DEVICE_PROPERTIES.add(Property("HostName", "Host name"))
            BASIC_DEVICE_PROPERTIES.add(Property("IsAgentOnline", "Online", true))
            BASIC_DEVICE_PROPERTIES.add(Property("IsVirtual", "Is virtual"))
            BASIC_DEVICE_PROPERTIES.add(Property("CustomAttributes", "Custom attributes"))
            BASIC_DEVICE_PROPERTIES.add(Property("MACAddress", "MAC Address"))
            BASIC_DEVICE_PROPERTIES.add(Property("Manufacturer", "Manufacturer", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Mode", "Mode", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Model", "Model", true))
            BASIC_DEVICE_PROPERTIES.add(Property("OSVersion", "OS version", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Path", "Path"))
        }
    }

    object SpecialDeviceProperties {
        var SPECIAL_DEVICE_PROPERTIES = arrayListOf<Property>()

        init {
            SPECIAL_DEVICE_PROPERTIES.add(Property("AgentVersion", "Agent version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("AndroidDeviceAdmin", "Device admin"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Antivirus"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BatteryStatus", "Battery status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BackupBatteryStatus", "Backup battery status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BiosVersion", "Bios version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BluetoothMACAddress", "Bluetooth MAC address"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BuildVersion", "Build version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CanResetPassword", "Can reset password"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularCarrier", "Cellular carrier"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularSignalStrength", "Cellular signal strength"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularTechnology", "Cellular technology"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CarrierSettingsVersion", "Cellular settings version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CompanyHubStatus", "Company hub status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CpuId", "CPU ID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CurrentMCC", "Current MCC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CurrentMNC", "Current MNC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CustomData", "Custom data"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DataRoamingEnabled", "Data roaming enabled"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceTerms", "Device terms"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceUserInfo", "Device user info"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceUserRole", "Device user role"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DMRevision", "DM revision"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ElmStatus", "ELM status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ExchangeBlocked", "Exchange blocked"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ExchangeStatus", "Exchange status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("FirmwareVersion", "Firmware version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareEncryptionCaps", "Hardware encryption caps"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareSerialNumber", "Hardware serial number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareVersion", "Hardware version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ICCID", "ICCID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IMEI_MEID_ESN", "IMEI MEID ESN"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IMEI_MEID_ESN_SIM2", "IMEI MEID ESN (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("InRoaming", "Is Roaming"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("InRoamingSIM2", "Is Roaming (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IntegratedApplications", "Integrated applications"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IntegrityServiceBaselineStatus", "Integrity service baseline status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Ipv6"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsAgentCompatible", "Agent compatible"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsAgentless", "Agentless"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsDeviceLocatorServiceEnabled", "Device location service"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsDoNotDisturbInEffect", "Do not disturb enabled"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsEncrypted", "Encrypted"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsEnrolled", "Is enrolled"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsITunesStoreAccountActive", "iTunes active"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsLearning", "Is learning"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsOSSecure", "OS secure"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsPersonalHotspotEnabled", "Personal hot spot enabled"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsSupervised ", "Supervised"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Knox", "Knox"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Language", "Language"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastCheckInTime", "Last check in time"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastLoggedOnAt", "Last logged on at"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastAgentConnectTime", "Last agent connection"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastAgentDisconnectTime", "Last agent disconnection"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastLoggedOnUser", "Last logged on user"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastStatusUpdate", "Last status update"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ManufacturerSerialNumber", "Manufacturer serial num"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Memory", "Memory"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ModelNumber", "Model number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ModemFirmwareVersion", "Modem firmware version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkBSSID", "Network BSSID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkConnectionType", "Network connection type"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkRSSI", "Network RSSI"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkSSID", "Network SSID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("OEMVersion", "OEM version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PasscodeEnabled", "Passcode enabled"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PasscodeStatus", "Passcode status"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PersonalizedName", "Personalized name"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PhoneNumber", "Phone number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PhoneNumberSIM2", "Phone num (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PrinterAdminServer", "Printer admin server"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Processor", "Processor"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ProductIdentification", "Product Identification"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ProductName", "Product name"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Property"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("RadioVersion", "Radio version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ScreenResolution", "Screen resolution"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SIMCarrierNetwork", "SIM carrier"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberMCC", "Subscriber MCC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberNumber", "Subscriber number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberNumberSIM2", "Subscriber MCC (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SupportedApis", "Supported APIs"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("TimeZone", "Timezone"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("UserIdentities", "User identities"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("UserIdHash", "User ID hash"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("VoiceRoamingEnabled", "Voice roaming enabled"))
        }
    }
}