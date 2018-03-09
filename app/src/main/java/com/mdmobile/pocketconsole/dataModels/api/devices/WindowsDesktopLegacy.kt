package com.mdmobile.pocketconsole.dataModels.api.devices


class WindowsDesktopLegacy(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                           Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                           Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                           ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                           IsVirtual: Boolean = false, Platform: String = "N/A")
    : BasicDevice(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode,
        Model, OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform), IDevice<WindowsDesktopLegacy>{
    override fun getDevice(): WindowsDesktopLegacy {
        return this
    }
}

//TODO support win desktop legacy extras