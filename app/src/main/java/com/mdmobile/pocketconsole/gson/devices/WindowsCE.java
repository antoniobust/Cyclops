package com.mdmobile.pocketconsole.gson.devices;

/**
 * Created by Antonio on 21/05/2017.
 */

public class WindowsCE extends BasicDevice {
    public WindowsCE(String Kind,String deviceId, String deviceName, String enrollmentTime, String family, String
            hostName, String MACAddress, String manufacturer, String mode, String model, String OSVersion, String path, Boolean complianceStatus, Boolean isAgentOnline, Boolean isVirtual) {
        super(Kind,deviceId, deviceName, enrollmentTime, family, hostName, MACAddress, manufacturer, mode, model,
                OSVersion, path, complianceStatus, isAgentOnline, isVirtual);
    }
}
