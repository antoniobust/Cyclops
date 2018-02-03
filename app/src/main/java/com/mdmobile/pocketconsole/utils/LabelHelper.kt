package com.mdmobile.pocketconsole.utils

/**
 * Helper class to manage properties internal and UI labels
 */

class LabelHelper {

    fun getUiLabelFor(property: String): String {
        val filter: (Property) -> Boolean = {
            it.internalLabel == property
        }
        val s = BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.single(filter).uiLabel
        return if (s == "") {
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
        return if (s == "") {
            s
        } else {
            SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES.single(filter).internalLabel
        }
    }

    fun getStatisticPrperties(property: String): ArrayList<Property> {
        val filter: (Property) -> Boolean = {
            it.statisticable
        }
        return (BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.filter(filter) +
                SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES.filter(filter)) as ArrayList<Property>
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
            SPECIAL_DEVICE_PROPERTIES.add(Property("AgentVersion", "Agent version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("AndroidDeviceAdmin", "Device admin", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Antivirus"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BatteryStatus", "Battery status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BackupBatteryStatus", "Backup battery status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BiosVersion", "Bios version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BluetoothMACAddress", "Bluetooth MAC address"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BuildVersion", "Build version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CanResetPassword", "Can reset password", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularCarrier", "Cellular carrier", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularSignalStrength", "Cellular signal strength", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularTechnology", "Cellular technology", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CarrierSettingsVersion", "Cellular settings version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CompanyHubStatus", "Company hub status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CpuId", "CPU ID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CurrentMCC", "Current MCC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CurrentMNC", "Current MNC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CustomData", "Custom data"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DataRoamingEnabled", "Data roaming enabled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceTerms", "Device terms"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceUserInfo", "Device user info"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceUserRole", "Device user role"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DMRevision", "DM revision"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ElmStatus", "ELM status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ExchangeBlocked", "Exchange blocked", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ExchangeStatus", "Exchange status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("FirmwareVersion", "Firmware version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareEncryptionCaps", "Hardware encryption caps", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareSerialNumber", "Hardware serial number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareVersion", "Hardware version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ICCID", "ICCID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IMEI_MEID_ESN", "IMEI MEID ESN"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IMEI_MEID_ESN_SIM2", "IMEI MEID ESN (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("InRoaming", "Is Roaming", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("InRoamingSIM2", "Is Roaming (SIM 2)", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IntegratedApplications", "Integrated applications"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IntegrityServiceBaselineStatus", "Integrity service baseline status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Ipv6"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsAgentCompatible", "Agent compatible", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsAgentless", "Agentless", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsDeviceLocatorServiceEnabled", "Device location service", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsDoNotDisturbInEffect", "Do not disturb enabled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsEncrypted", "Encrypted", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsEnrolled", "Is enrolled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsITunesStoreAccountActive", "iTunes active", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsLearning", "Is learning", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsOSSecure", "OS secure", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsPersonalHotspotEnabled", "Personal hot spot enabled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IsSupervised ", "Supervised", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Knox", "Knox", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Language", "Language", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastCheckInTime", "Last check in time", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastLoggedOnAt", "Last logged on at", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastAgentConnectTime", "Last agent connection", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastAgentDisconnectTime", "Last agent disconnection", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastLoggedOnUser", "Last logged on user", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastStatusUpdate", "Last status update", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ManufacturerSerialNumber", "Manufacturer serial num", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Memory", "Memory", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ModelNumber", "Model number", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ModemFirmwareVersion", "Modem firmware version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkBSSID", "Network BSSID", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkConnectionType", "Network connection type", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkRSSI", "Network RSSI", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkSSID", "Network SSID", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("OEMVersion", "OEM version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PasscodeEnabled", "Passcode enabled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PasscodeStatus", "Passcode status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PersonalizedName", "Personalized name"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PhoneNumber", "Phone number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PhoneNumberSIM2", "Phone num (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PrinterAdminServer", "Printer admin server", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Processor", "Processor", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ProductIdentification", "Product Identification"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ProductName", "Product name"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Property"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("RadioVersion", "Radio version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ScreenResolution", "Screen resolution", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SIMCarrierNetwork", "SIM carrier", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberMCC", "Subscriber MCC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberNumber", "Subscriber number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberNumberSIM2", "Subscriber MCC (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SupportedApis", "Supported APIs", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("TimeZone", "Timezone", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("UserIdentities", "User identities"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("UserIdHash", "User ID hash"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("VoiceRoamingEnabled", "Voice roaming enabled", true))
        }
    }
}