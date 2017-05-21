package com.mdmobile.pocketconsole.gson.devices;

/**
 * Represent Gson class for android generic device
 */

public class AndroidGenericDevice extends BasicDevice {

    private String AgentVersion, LastCheckInTime, LastAgentConnectTime, LastAgentDisconnectTime, LastLoggedOnUser,
            NetworkBSSID, ExchangeStatus, IMEI_MEID_ESN, Ipv6, CellularCarrier, NetworkSSID, PhoneNumber, SubscriberNumber;
    private boolean AndroidDeviceAdmin, IsAgentCompatible, IsAgentless, IsEncrypted, IsOSSecure, CanResetPassword,
            ExchangeBlocked, InRoaming, PasscodeEnabled;
    private short BatteryStatus;
    private int CellularSignalStrength, HardwareEncryptionCaps, NetworkConnectionType, NetworkRSSI;

    //TODO: support following data
//    Array	Antivirus;
//    Array	CustomData;
//    DeviceTerms	DeviceTerms;
//    DeviceUser	DeviceUserInfo;


    public AndroidGenericDevice(String Kind,String deviceId, String deviceName, String enrollmentTime, String family,
                                String hostName,
                                String MACAddress, String manufacturer, String mode, String model, String OSVersion,
                                String path, Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual,
                                String agentVersion, String lastCheckInTime, String lastAgentConnectTime,
                                String lastAgentDisconnectTime, String lastLoggedOnUser, String networkBSSID,
                                String exchangeStatus, String IMEI_MEID_ESN, String ipv6, String cellularCarrier,
                                String networkSSID, String phoneNumber, String subscriberNumber, boolean androidDeviceAdmin,
                                boolean isAgentCompatible, boolean isAgentless, boolean isEncrypted, boolean isOSSecure,
                                boolean canResetPassword, boolean exchangeBlocked, boolean inRoaming, boolean passcodeEnabled,
                                short batteryStatus, int cellularSignalStrength, int hardwareEncryptionCaps,
                                int networkConnectionType, int networkRSSI, String Platform) {

        super(Kind, deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
                OSVersion, path,
                complianceStatus, isAgentOnline, isVirtual,Platform);

        AgentVersion = agentVersion;
        LastCheckInTime = lastCheckInTime;
        LastAgentConnectTime = lastAgentConnectTime;
        LastAgentDisconnectTime = lastAgentDisconnectTime;
        LastLoggedOnUser = lastLoggedOnUser;
        NetworkBSSID = networkBSSID;
        ExchangeStatus = exchangeStatus;
        this.IMEI_MEID_ESN = IMEI_MEID_ESN;
        Ipv6 = ipv6;
        CellularCarrier = cellularCarrier;
        NetworkSSID = networkSSID;
        PhoneNumber = phoneNumber;
        SubscriberNumber = subscriberNumber;
        AndroidDeviceAdmin = androidDeviceAdmin;
        IsAgentCompatible = isAgentCompatible;
        IsAgentless = isAgentless;
        IsEncrypted = isEncrypted;
        IsOSSecure = isOSSecure;
        CanResetPassword = canResetPassword;
        ExchangeBlocked = exchangeBlocked;
        InRoaming = inRoaming;
        PasscodeEnabled = passcodeEnabled;
        BatteryStatus = batteryStatus;
        CellularSignalStrength = cellularSignalStrength;
        HardwareEncryptionCaps = hardwareEncryptionCaps;
        NetworkConnectionType = networkConnectionType;
        NetworkRSSI = networkRSSI;
    }

    public String getAgentVersion() {
        return AgentVersion;
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

    public String getNetworkBSSID() {
        return NetworkBSSID;
    }

    public String getExchangeStatus() {
        return ExchangeStatus;
    }

    public String getIMEI_MEID_ESN() {
        return IMEI_MEID_ESN;
    }

    public String getIpv6() {
        return Ipv6;
    }

    public String getCellularCarrier() {
        return CellularCarrier;
    }

    public String getNetworkSSID() {
        return NetworkSSID;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getSubscriberNumber() {
        return SubscriberNumber;
    }

    public int isAndroidDeviceAdmin() {
        return AndroidDeviceAdmin ? 1 :0;
    }

    public int isAgentCompatible() {
        return IsAgentCompatible? 1 :0;
    }

    public int isAgentless() {
        return IsAgentless? 1 :0;
    }

    public int isEncrypted() {
        return IsEncrypted? 1 :0;
    }

    public int isOSSecure() {
        return IsOSSecure? 1 :0;
    }

    public int isCanResetPassword() {
        return CanResetPassword? 1 :0;
    }

    public int isExchangeBlocked() {
        return ExchangeBlocked? 1 :0;
    }

    public int isInRoaming() {
        return InRoaming? 1 :0;
    }

    public int isPasscodeEnabled() {
        return PasscodeEnabled? 1 :0;
    }

    public short getBatteryStatus() {
        return BatteryStatus;
    }

    public int getCellularSignalStrength() {
        return CellularSignalStrength;
    }

    public int getHardwareEncryptionCaps() {
        return HardwareEncryptionCaps;
    }

    public int getNetworkConnectionType() {
        return NetworkConnectionType;
    }

    public int getNetworkRSSI() {
        return NetworkRSSI;
    }
}
