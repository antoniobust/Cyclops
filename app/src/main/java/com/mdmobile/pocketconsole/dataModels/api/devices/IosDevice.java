package com.mdmobile.pocketconsole.dataModels.api.devices;

/**
 * Represent gson class for iOS device
 */

public class IosDevice extends BasicDevice {
    private String AgentVersion, BluetoothMACAddress, BuildVersion, CarrierSettingsVersion, CellularCarrier,
            CurrentMCC, CurrentMNC, FirmwareVersion, CellularTechnology, ExchangeStatus, ICCID, IMEI_MEID_ESN,
            Ipv6, LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime, LastLoggedOnUser, LastStatusUpdate,
            ManufacturerSerialNumber, ModelNumber, ModemFirmwareVersion, PersonalizedName, PhoneNumber, ProductName,
            SIMCarrierNetwork, SubscriberMCC, SubscriberMNC, SubscriberNumber, UserIdHash, PasscodeStatus;

    private int HardwareEncryptionCaps, NetworkConnectionType;
    private short BatteryStatus;

    private boolean InRoaming, DataRoamingEnabled, IsAgentCompatible, IsAgentless, IsDeviceLocatorServiceEnabled,
            IsDoNotDisturbInEffect, IsEncrypted, IsEnrolled, IsITunesStoreAccountActive, IsOSSecure,
            IsPersonalHotspotEnabled, IsSupervised, PasscodeEnabled, VoiceRoamingEnabled, ExchangeBlocked;


//    TODO:Support following info
//    DeviceTerms	DeviceTerms
//    DeviceUser	DeviceUserInfo


    public IosDevice(String Kind, String deviceId, String deviceName, String enrollmentTime, String family, String
            hostName, String MACAddress, String manufacturer, String mode, String model,
                     String OSVersion, String path, Boolean complianceStatus,
                     Boolean isAgentOnline, Boolean isVirtual, String agentVersion, String bluetoothMACAddress,
                     String buildVersion, String carrierSettingsVersion, String cellularCarrier, String currentMCC,
                     String currentMNC, String firmwareVersion, String cellularTechnology, String exchangeStatus, String ICCID,
                     String IMEI_MEID_ESN, String ipv6, String lastCheckInTime, String lastAgentConnectTime,
                     String lastAgentDisconnectTime, String lastLoggedOnUser, String lastStatusUpdate, String manufacturerSerialNumber,
                     String modelNumber, String modemFirmwareVersion, String personalizedName, String phoneNumber,
                     String productName, String SIMCarrierNetwork, String subscriberMCC, String subscriberMNC,
                     String subscriberNumber, String userIdHash, String passcodeStatus, int hardwareEncryptionCaps,
                     int networkConnectionType, short batteryStatus, boolean inRoaming, boolean dataRoamingEnabled,
                     boolean isAgentCompatible, boolean isAgentless, boolean isDeviceLocatorServiceEnabled,
                     boolean isDoNotDisturbInEffect, boolean isEncrypted, boolean isEnrolled, boolean isITunesStoreAccountActive,
                     boolean isOSSecure, boolean isPersonalHotspotEnabled, boolean isSupervised, boolean passcodeEnabled,
                     boolean voiceRoamingEnabled, boolean exchangeBlocked, String Platform) {

        super(Kind, deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
                OSVersion,
                path, complianceStatus, isAgentOnline, isVirtual, Platform);

        AgentVersion = agentVersion;
        BluetoothMACAddress = bluetoothMACAddress;
        BuildVersion = buildVersion;
        CarrierSettingsVersion = carrierSettingsVersion;
        CellularCarrier = cellularCarrier;
        CurrentMCC = currentMCC;
        CurrentMNC = currentMNC;
        FirmwareVersion = firmwareVersion;
        CellularTechnology = cellularTechnology;
        ExchangeStatus = exchangeStatus;
        this.ICCID = ICCID;
        this.IMEI_MEID_ESN = IMEI_MEID_ESN;
        Ipv6 = ipv6;
        LastCheckInTime = lastCheckInTime;
        LastAgentConnectTime = lastAgentConnectTime;
        LastAgentDisconnectTime = lastAgentDisconnectTime;
        LastLoggedOnUser = lastLoggedOnUser;
        LastStatusUpdate = lastStatusUpdate;
        ManufacturerSerialNumber = manufacturerSerialNumber;
        ModelNumber = modelNumber;
        ModemFirmwareVersion = modemFirmwareVersion;
        PersonalizedName = personalizedName;
        PhoneNumber = phoneNumber;
        ProductName = productName;
        this.SIMCarrierNetwork = SIMCarrierNetwork;
        SubscriberMCC = subscriberMCC;
        SubscriberMNC = subscriberMNC;
        SubscriberNumber = subscriberNumber;
        UserIdHash = userIdHash;
        PasscodeStatus = passcodeStatus;
        HardwareEncryptionCaps = hardwareEncryptionCaps;
        NetworkConnectionType = networkConnectionType;
        BatteryStatus = batteryStatus;
        InRoaming = inRoaming;
        DataRoamingEnabled = dataRoamingEnabled;
        IsAgentCompatible = isAgentCompatible;
        IsAgentless = isAgentless;
        IsDeviceLocatorServiceEnabled = isDeviceLocatorServiceEnabled;
        IsDoNotDisturbInEffect = isDoNotDisturbInEffect;
        IsEncrypted = isEncrypted;
        IsEnrolled = isEnrolled;
        IsITunesStoreAccountActive = isITunesStoreAccountActive;
        IsOSSecure = isOSSecure;
        IsPersonalHotspotEnabled = isPersonalHotspotEnabled;
        IsSupervised = isSupervised;
        PasscodeEnabled = passcodeEnabled;
        VoiceRoamingEnabled = voiceRoamingEnabled;
        ExchangeBlocked = exchangeBlocked;
    }

    public String getAgentVersion() {
        return AgentVersion;
    }

    public String getBluetoothMACAddress() {
        return BluetoothMACAddress;
    }

    public String getBuildVersion() {
        return BuildVersion;
    }

    public String getCarrierSettingsVersion() {
        return CarrierSettingsVersion;
    }

    public String getCellularCarrier() {
        return CellularCarrier;
    }

    public String getCurrentMCC() {
        return CurrentMCC;
    }

    public String getCurrentMNC() {
        return CurrentMNC;
    }

    public String getFirmwareVersion() {
        return FirmwareVersion;
    }

    public String getCellularTechnology() {
        return CellularTechnology;
    }

    public String getExchangeStatus() {
        return ExchangeStatus;
    }

    public String getICCID() {
        return ICCID;
    }

    public String getIMEI_MEID_ESN() {
        return IMEI_MEID_ESN;
    }

    public String getIpv6() {
        return Ipv6;
    }

    public String getLastCheckInTime() {
        return LastCheckInTime;
    }

    public String getLastAgentConnectTime() {
        return LastAgentConnectTime;
    }

    public String getLastAgentDisconnectTime() {
        return LastAgentDisconnectTime;
    }

    public String getLastLoggedOnUser() {
        return LastLoggedOnUser;
    }

    public String getLastStatusUpdate() {
        return LastStatusUpdate;
    }

    public String getManufacturerSerialNumber() {
        return ManufacturerSerialNumber;
    }

    public String getModelNumber() {
        return ModelNumber;
    }

    public String getModemFirmwareVersion() {
        return ModemFirmwareVersion;
    }

    public String getPersonalizedName() {
        return PersonalizedName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getSIMCarrierNetwork() {
        return SIMCarrierNetwork;
    }

    public String getSubscriberMCC() {
        return SubscriberMCC;
    }

    public String getSubscriberMNC() {
        return SubscriberMNC;
    }

    public String getSubscriberNumber() {
        return SubscriberNumber;
    }

    public String getUserIdHash() {
        return UserIdHash;
    }

    public String getPasscodeStatus() {
        return PasscodeStatus;
    }

    public int getHardwareEncryptionCaps() {
        return HardwareEncryptionCaps;
    }

    public int getNetworkConnectionType() {
        return NetworkConnectionType;
    }

    public short getBatteryStatus() {
        return BatteryStatus;
    }

    public int isInRoaming() {
        return InRoaming ? 1 : 0;
    }

    public int isDataRoamingEnabled() {
        return DataRoamingEnabled ? 1 : 0;
    }

    public int isAgentCompatible() {
        return IsAgentCompatible ? 1 : 0;
    }

    public int isAgentless() {
        return IsAgentless ? 1 : 0;
    }

    public int isDeviceLocatorServiceEnabled() {
        return IsDeviceLocatorServiceEnabled ? 1 : 0;
    }

    public int isDoNotDisturbInEffect() {
        return IsDoNotDisturbInEffect ? 1 : 0;
    }

    public int isEncrypted() {
        return IsEncrypted ? 1 : 0;
    }

    public int isEnrolled() {
        return IsEnrolled ? 1 : 0;
    }

    public int isITunesStoreAccountActive() {
        return IsITunesStoreAccountActive ? 1 : 0;
    }

    public int isOSSecure() {
        return IsOSSecure ? 1 : 0;
    }

    public int isPersonalHotspotEnabled() {
        return IsPersonalHotspotEnabled ? 1 : 0;
    }

    public int isSupervised() {
        return IsSupervised ? 1 : 0;
    }

    public int isPasscodeEnabled() {
        return PasscodeEnabled ? 1 : 0;
    }

    public int isVoiceRoamingEnabled() {
        return VoiceRoamingEnabled ? 1 : 0;
    }

    public int isExchangeBlocked() {
        return ExchangeBlocked ? 1 : 0;
    }
}
