package com.mdmobile.pocketconsole.gson.devices;

/**
 * Represent a specific class for Samsung devices using Knox
 */

//TODO: support knox devices

public class SamsungKnoxDevice extends SamsungElmDevice {
    private String	IntegrityServiceBaselineStatus;
    private Knox	Knox;

    public SamsungKnoxDevice(String Kind,String deviceId, String deviceName, String enrollmentTime, String family,
                             String hostName,
                             String MACAddress, String manufacturer, String mode, String model, String OSVersion, String path,
                             Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual, String agentVersion,
                             String hardwareSerialNumber, String hardwareVersion, String IMEI_MEID_ESN, String cellularCarrier,
                             String lastLoggedOnUser, String networkBSSID, String ipv6, String networkSSID, String OEMVersion,
                             String phoneNumber, String subscriberNumber, String passcodeStatus, String supportedApis,
                             String exchangeStatus, String lastCheckInTime, String lastAgentConnectTime,
                             String lastAgentDisconnectTime, boolean inRoaming, boolean androidDeviceAdmin,
                             boolean canResetPassword, boolean exchangeBlocked, boolean isAgentCompatible, boolean isAgentless,
                             boolean isEncrypted, boolean isOSSecure, boolean passcodeEnabled, short batteryStatus,
                             int cellularSignalStrength, int networkConnectionType, int networkRSSI, int hardwareEncryptionCaps,
                             String elmStatus, String integrityServiceBaselineStatus, SamsungKnoxDevice.Knox knox) {

        super(Kind,deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
                OSVersion,
                path, complianceStatus, isAgentOnline, isVirtual, agentVersion, hardwareSerialNumber, hardwareVersion,
                IMEI_MEID_ESN, cellularCarrier, lastLoggedOnUser, networkBSSID, ipv6, networkSSID, OEMVersion, phoneNumber,
                subscriberNumber, passcodeStatus, supportedApis, exchangeStatus, lastCheckInTime, lastAgentConnectTime,
                lastAgentDisconnectTime, inRoaming, androidDeviceAdmin, canResetPassword, exchangeBlocked, isAgentCompatible,
                isAgentless, isEncrypted, isOSSecure, passcodeEnabled, batteryStatus, cellularSignalStrength, networkConnectionType,
                networkRSSI, hardwareEncryptionCaps, elmStatus);

        IntegrityServiceBaselineStatus = integrityServiceBaselineStatus;
        Knox = knox;
    }

    public String getIntegrityServiceBaselineStatus() {
        return IntegrityServiceBaselineStatus;
    }

    public SamsungKnoxDevice.Knox getKnox() {
        return Knox;
    }

    private class Knox {
        String KnoxCapability,
        IntegrityServiceBaselineStatus,
        KnoxAttestationCapability,
        KnoxAttestationStatus;
        KnoxLicenseInfo	KnoxLicense;
    }

    private class KnoxLicenseInfo {
        private String LicenseGuid, LicenseKey, LicenseType	, LicenseStatus, OrderNumber, StartDate,EndDate,UpdateDate;
        private int TotalSeats, AvailableSeats;
        private boolean enabled;
    }
}
