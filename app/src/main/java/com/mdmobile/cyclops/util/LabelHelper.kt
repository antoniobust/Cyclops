package com.mdmobile.cyclops.util

import android.util.Log

/**
 * Helper class to manage properties internal and UI labels
 */

class LabelHelper {

    companion object {
        private val LOG_TAG = LabelHelper::class.java.simpleName

        fun getUiLabelFor(property: String): String {
            val filter: (Property) -> Boolean = {
                it.internalLabel == property
            }
            return try {
                (BasicDeviceProperties.BASIC_DEVICE_PROPERTIES + SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES)
                        .single(filter).uiLabel
            } catch (e: NoSuchElementException) {
                Logger.log(LOG_TAG, "No label found:$property", Log.ERROR)
                ""
            }
        }

        fun getInternalLabelFor(property: String): String {
            val filter: (Property) -> Boolean = {
                it.uiLabel == property
            }
            return try {
                (BasicDeviceProperties.BASIC_DEVICE_PROPERTIES + SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES)
                .single(filter).internalLabel
            } catch (e: NoSuchElementException) {
                ""
            }
        }

        fun getStatisticProperties(): ArrayList<Property> {
            val filter: (Property) -> Boolean = {
                it.statisticable
            }
            return (BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.filter(filter) +
                    SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES.filter(filter)) as ArrayList<Property>
        }

        fun getBasicDeviceStatisticProperties(): ArrayList<Property> {
            val filter: (Property) -> Boolean = {
                it.statisticable
            }
            return (BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.filter(filter)) as ArrayList<Property>
        }

        fun getAllUILabels(): Array<String> {
            return BasicDeviceProperties.BASIC_DEVICE_PROPERTIES.map {
                it.uiLabel
            }.toTypedArray()
        }

        fun getAllExtraInfo(): ArrayList<Property> {
            return SpecialDeviceProperties.SPECIAL_DEVICE_PROPERTIES
        }

        fun getAllBasicInfo(): ArrayList<Property>{
            return BasicDeviceProperties.BASIC_DEVICE_PROPERTIES
        }

        fun isBasicProperty(internalLabel: String):Boolean{
            val uiLabel = getUiLabelFor(internalLabel)
            val isStatisticable = getStatisticProperties().contains(Property(internalLabel,uiLabel,true))
            return getAllBasicInfo().contains(Property(internalLabel,uiLabel,isStatisticable))
        }
    }

    object BasicDeviceProperties {
        var BASIC_DEVICE_PROPERTIES = ArrayList<Property>()

        init {
            BASIC_DEVICE_PROPERTIES.add(Property("ComplianceStatus", "Compliance Status", true))
            BASIC_DEVICE_PROPERTIES.add(Property("ComplianceItems", "Compliance item"))
            BASIC_DEVICE_PROPERTIES.add(Property("DeviceId", "Device ID"))
            BASIC_DEVICE_PROPERTIES.add(Property("DeviceName", "Device Name"))
            BASIC_DEVICE_PROPERTIES.add(Property("Kind", statisticable = true))
            BASIC_DEVICE_PROPERTIES.add(Property("EnrollmentTime", "Enrollment Time", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Family", statisticable = true))
            BASIC_DEVICE_PROPERTIES.add(Property("HostName", "Host Name"))
            BASIC_DEVICE_PROPERTIES.add(Property("IsAgentOnline", "Is online", true))
            BASIC_DEVICE_PROPERTIES.add(Property("IsVirtual", "Is virtual"))
            BASIC_DEVICE_PROPERTIES.add(Property("CustomAttributes", "Custom attributes"))
            BASIC_DEVICE_PROPERTIES.add(Property("MACAddress", "MAC Address"))
            BASIC_DEVICE_PROPERTIES.add(Property("Manufacturer", "Manufacturer", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Mode", statisticable = true))
            BASIC_DEVICE_PROPERTIES.add(Property("Model", statisticable = true))
            BASIC_DEVICE_PROPERTIES.add(Property("OSVersion", "OS Version", true))
            BASIC_DEVICE_PROPERTIES.add(Property("Path"))
        }
    }

    object SpecialDeviceProperties {
        var SPECIAL_DEVICE_PROPERTIES = arrayListOf<Property>()

        init {
            SPECIAL_DEVICE_PROPERTIES.add(Property("AgentVersion", "Agent Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("AndroidDeviceAdmin", "Device admin", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Antivirus", statisticable = true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BatteryStatus", "Battery Status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BackupBatteryStatus", "Backup battery Status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BiosVersion", "Bios Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BluetoothMACAddress", "Bluetooth MAC address"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("BuildVersion", "Build Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CanResetPassword", "Can reset password", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularCarrier", "Cellular carrier", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularSignalStrength", "Cellular signal strength"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CellularTechnology", "Cellular technology", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CarrierSettingsVersion", "Cellular settings Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CompanyHubStatus", "Company hub Status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CpuId", "CPU ID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CurrentMCC", "Current MCC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CurrentMNC", "Current MNC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("CustomData", "Custom data"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DataRoamingEnabled", "Data roaming enabled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceTerms", "Device terms"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceUserInfo", "Device user info"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DeviceUserRole", "Device user role"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("DMRevision", "DM revision"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ElmStatus", "ELM Status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ExchangeBlocked", "Exchange blocked", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ExchangeStatus", "Exchange Status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("FirmwareVersion", "Firmware Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareEncryptionCaps", "Hardware encryption caps", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareSerialNumber", "Hardware serial number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("HardwareVersion", "Hardware Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ICCID", "ICCID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IMEI_MEID_ESN", "IMEI MEID ESN"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IMEI_MEID_ESN_SIM2", "IMEI MEID ESN (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("InRoaming", "Is Roaming", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("InRoamingSIM2", "Is Roaming (SIM 2)", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IntegratedApplications", "Integrated applications"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("IntegrityServiceBaselineStatus", "Integrity service baseline Status"))
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
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastLoggedOnAt", "Last logged on at"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastAgentConnectTime", "Last agent connection"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastAgentDisconnectTime", "Last agent disconnection"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastLoggedOnUser", "Last logged on user"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("LastStatusUpdate", "Last Status update"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ManufacturerSerialNumber", "Manufacturer serial num"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Memory", "Memory"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ModelNumber", "Model number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ModemFirmwareVersion", "Modem firmware Version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkBSSID", "Network BSSID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkConnectionType", "Network connection type", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkRSSI", "Network RSSI"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("NetworkSSID", "Network SSID"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("OEMVersion", "OEM Version", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PasscodeEnabled", "Passcode enabled", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PasscodeStatus", "Passcode Status", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PersonalizedName", "Personalized Name"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PhoneNumber", "Phone number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PhoneNumberSIM2", "Phone num (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("PrinterAdminServer", "Printer admin server"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Processor", "Processor", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ProductIdentification", "Product Identification"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ProductName", "Product Name"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("Property"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("RadioVersion", "Radio Version"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("ScreenResolution", "Screen resolution"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SIMCarrierNetwork", "SIM carrier"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberMCC", "Subscriber MCC"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberNumber", "Subscriber number"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SubscriberNumberSIM2", "Subscriber MCC (SIM 2)"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("SupportedApis", "Supported APIs", true))
            SPECIAL_DEVICE_PROPERTIES.add(Property("TimeZone", "Timezone"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("UserIdentities", "User identities"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("UserIdHash", "User ID hash"))
            SPECIAL_DEVICE_PROPERTIES.add(Property("VoiceRoamingEnabled", "Voice roaming enabled", true))
        }
    }
}