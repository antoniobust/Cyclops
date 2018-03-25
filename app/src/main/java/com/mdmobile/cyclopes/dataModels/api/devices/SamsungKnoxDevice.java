//package com.mdmobile.pocketconsole.dataModels.api.devices;
//
///**
// * Represent a specific class for Samsung devices using Knox
// */
//
////TODO: support knox devices
//
//public class SamsungKnoxDevice extends SamsungElm {
//    private String IntegrityServiceBaselineStatus;
//    private Knox Knox;
//
//    public SamsungKnoxDevice(String Kind, String deviceId, String deviceName, String enrollmentTime, String family,
//                             String hostName,
//                             String MACAddress, String manufacturer, String mode, String model, String OSVersion, String path,
//                             Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual, String AgentVersion,
//                             String hardwareSerialNumber, String hardwareVersion, String IMEI_MEID_ESN, String CellularCarrier,
//                             String LastLoggedOnUser, String NetworkBSSID, String Ipv6, String NetworkSSID, String OEMVersion,
//                             String PhoneNumber, String SubscriberNumber, String passcodeStatus, String[] supportedApis,
//                             String ExchangeStatus, String LastCheckInTime, String LastAgentConnectTime,
//                             String LastAgentDisconnectTime, boolean inRoaming, boolean androidDeviceAdmin,
//                             boolean canResetPassword, boolean exchangeBlocked, boolean isAgentCompatible, boolean isAgentless,
//                             boolean isEncrypted, boolean isOSSecure, boolean passcodeEnabled, short BatteryStatus,
//                             int CellularSignalStrength, int NetworkConnectionType, int NetworkRSSI, int HardwareEncryptionCaps,
//                             String elmStatus, String integrityServiceBaselineStatus, SamsungKnoxDevice.Knox knox,
//                             String Platform) {
//
//        super(Kind, deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
//                OSVersion,
//                path, complianceStatus, isAgentOnline, isVirtual, AgentVersion, hardwareSerialNumber, hardwareVersion,
//                IMEI_MEID_ESN, CellularCarrier, LastLoggedOnUser, NetworkBSSID, Ipv6, NetworkSSID, OEMVersion, PhoneNumber,
//                SubscriberNumber, passcodeStatus, supportedApis, ExchangeStatus, LastCheckInTime, LastAgentConnectTime,
//                LastAgentDisconnectTime, inRoaming, androidDeviceAdmin, canResetPassword, exchangeBlocked, isAgentCompatible,
//                isAgentless, isEncrypted, isOSSecure, passcodeEnabled, BatteryStatus, CellularSignalStrength, NetworkConnectionType,
//                NetworkRSSI, HardwareEncryptionCaps, elmStatus);
//
//        IntegrityServiceBaselineStatus = integrityServiceBaselineStatus;
//        Knox = knox;
//    }
//
//    public String getIntegrityServiceBaselineStatus() {
//        return IntegrityServiceBaselineStatus;
//    }
//
//    public SamsungKnoxDevice.Knox getKnox() {
//        return Knox;
//    }
//
//    private class Knox {
//        String KnoxCapability,
//                IntegrityServiceBaselineStatus,
//                KnoxAttestationCapability,
//                KnoxAttestationStatus;
//        KnoxLicenseInfo KnoxLicense;
//    }
//
//    private class KnoxLicenseInfo {
//        private String LicenseGuid, LicenseKey, LicenseType, LicenseStatus, OrderNumber, StartDate, EndDate, UpdateDate;
//        private int TotalSeats, AvailableSeats;
//        private boolean enabled;
//    }
//}
