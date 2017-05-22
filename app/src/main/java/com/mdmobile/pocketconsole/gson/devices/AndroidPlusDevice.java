package com.mdmobile.pocketconsole.gson.devices;

/**
 * Represent Gson class for android Plus Device
 */

public class AndroidPlusDevice extends BasicDevice {
    private String AgentVersion, HardwareSerialNumber, HardwareVersion, IMEI_MEID_ESN, CellularCarrier,
            LastLoggedOnUser,
            NetworkBSSID, Ipv6, NetworkSSID, OEMVersion, PhoneNumber, SubscriberNumber, PasscodeStatus,
            ExchangeStatus, LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime;
    private String[] SupportedApis;
    private boolean InRoaming, AndroidDeviceAdmin, CanResetPassword, ExchangeBlocked, IsAgentCompatible, IsAgentless,
            IsEncrypted, IsOSSecure, PasscodeEnabled;
    private short BatteryStatus;
    private int CellularSignalStrength, NetworkConnectionType, NetworkRSSI, HardwareEncryptionCaps;

    //TODO: Support following info
//    Array	Antivirus
//    Array	CustomData
//    DeviceTerms	DeviceTerms
//    DeviceUser	DeviceUserInfo
//    DeviceIntegratedApplication[]	IntegratedApplications


    public AndroidPlusDevice(String Kind,String deviceId, String deviceName, String enrollmentTime, String family, String hostName,
                             String MACAddress, String manufacturer, String mode, String model, String OSVersion, String path,
                             Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual, String agentVersion,
                             String hardwareSerialNumber, String hardwareVersion, String IMEI_MEID_ESN, String cellularCarrier,
                             String lastLoggedOnUser, String networkBSSID, String ipv6, String networkSSID, String OEMVersion,
                             String phoneNumber, String subscriberNumber, String passcodeStatus, String[] supportedApis,
                             String exchangeStatus, String lastCheckInTime, String lastAgentConnectTime, String lastAgentDisconnectTime,
                             boolean inRoaming, boolean androidDeviceAdmin, boolean canResetPassword, boolean exchangeBlocked,
                             boolean isAgentCompatible, boolean isAgentless, boolean isEncrypted, boolean isOSSecure,
                             boolean passcodeEnabled, short batteryStatus, int cellularSignalStrength, int networkConnectionType,
                             int networkRSSI, int hardwareEncryptionCaps,String Platform) {

        super(Kind,deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
                OSVersion, path, complianceStatus, isAgentOnline, isVirtual,Platform);

        AgentVersion = agentVersion;
        HardwareSerialNumber = hardwareSerialNumber;
        HardwareVersion = hardwareVersion;
        this.IMEI_MEID_ESN = IMEI_MEID_ESN;
        CellularCarrier = cellularCarrier;
        LastLoggedOnUser = lastLoggedOnUser;
        NetworkBSSID = networkBSSID;
        Ipv6 = ipv6;
        NetworkSSID = networkSSID;
        this.OEMVersion = OEMVersion;
        PhoneNumber = phoneNumber;
        SubscriberNumber = subscriberNumber;
        PasscodeStatus = passcodeStatus;
        SupportedApis = supportedApis;
        ExchangeStatus = exchangeStatus;
        LastCheckInTime = lastCheckInTime;
        LastAgentConnectTime = lastAgentConnectTime;
        LastAgentDisconnectTime = lastAgentDisconnectTime;
        InRoaming = inRoaming;
        AndroidDeviceAdmin = androidDeviceAdmin;
        CanResetPassword = canResetPassword;
        ExchangeBlocked = exchangeBlocked;
        IsAgentCompatible = isAgentCompatible;
        IsAgentless = isAgentless;
        IsEncrypted = isEncrypted;
        IsOSSecure = isOSSecure;
        PasscodeEnabled = passcodeEnabled;
        BatteryStatus = batteryStatus;
        CellularSignalStrength = cellularSignalStrength;
        NetworkConnectionType = networkConnectionType;
        NetworkRSSI = networkRSSI;
        HardwareEncryptionCaps = hardwareEncryptionCaps;
    }

    public String getAgentVersion() {
        return AgentVersion;
    }

    public String getHardwareSerialNumber() {
        return HardwareSerialNumber;
    }

    public String getHardwareVersion() {
        return HardwareVersion;
    }

    public String getIMEI_MEID_ESN() {
        return IMEI_MEID_ESN;
    }

    public String getCellularCarrier() {
        return CellularCarrier;
    }

    public String getLastLoggedOnUser() {
        return LastLoggedOnUser;
    }

    public String getNetworkBSSID() {
        return NetworkBSSID;
    }

    public String getIpv6() {
        return Ipv6;
    }

    public String getNetworkSSID() {
        return NetworkSSID;
    }

    public String getOEMVersion() {
        return OEMVersion;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getSubscriberNumber() {
        return SubscriberNumber;
    }

    public String getPasscodeStatus() {
        return PasscodeStatus;
    }

    public String[] getSupportedApis() {
        return SupportedApis;
    }

    public String getExchangeStatus() {
        return ExchangeStatus;
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

    public int isInRoaming() {
        return InRoaming ? 1 : 0;
    }

    public int isAndroidDeviceAdmin() {
        return AndroidDeviceAdmin ? 1 : 0;
    }

    public int isCanResetPassword() {
        return CanResetPassword ? 1 : 0;
    }

    public int isExchangeBlocked() {
        return ExchangeBlocked ? 1 : 0;
    }

    public int isAgentCompatible() {
        return IsAgentCompatible ? 1 : 0;
    }

    public int isAgentless() {
        return IsAgentless ? 1 : 0;
    }

    public int isEncrypted() {
        return IsEncrypted ? 1 : 0;
    }

    public int isOSSecure() {
        return IsOSSecure ? 1 : 0;
    }

    public int isPasscodeEnabled() {
        return PasscodeEnabled ? 1 : 0;
    }

    public short getBatteryStatus() {
        return BatteryStatus;
    }

    public int getCellularSignalStrength() {
        return CellularSignalStrength;
    }

    public int getNetworkConnectionType() {
        return NetworkConnectionType;
    }

    public int getNetworkRSSI() {
        return NetworkRSSI;
    }

    public int getHardwareEncryptionCaps() {
        return HardwareEncryptionCaps;
    }
}
