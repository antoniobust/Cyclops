package com.mdmobile.pocketconsole.dataModels.api.devices;


public class WindowsDesktop extends BasicDevice {

    public WindowsDesktop(String Kind, String deviceId, String deviceName, String enrollmentTime, String family, String hostName, String MACAddress, String manufacturer, String mode, String model, String OSVersion, String path, Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual, String Platform) {
        super(Kind, deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model, OSVersion, path, complianceStatus, isAgentOnline, isVirtual, Platform);
    }
}
