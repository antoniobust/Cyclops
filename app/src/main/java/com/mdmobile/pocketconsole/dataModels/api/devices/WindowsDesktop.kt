package com.mdmobile.pocketconsole.dataModels.api.devices

import android.database.Cursor

class WindowsDesktop : BasicDevice, IDevice<WindowsDesktop> {

    constructor(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                IsVirtual: Boolean = false, Platform: String = "N/A") : super(
            Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode,
            Model, OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform)

    constructor(c: Cursor) : super(c)

    override fun getDevice(): WindowsDesktop {
        return this
    }
}

//TODO: support Windows Desktop extras