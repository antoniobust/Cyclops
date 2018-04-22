package com.mdmobile.cyclops.dataModel.api.devices

import android.database.Cursor

class NotYetSupportedDevice : BasicDevice, IDevice<NotYetSupportedDevice> {

    constructor(Kind: String, DeviceId: String, DeviceName: String, EnrollmentTime: String,
                Family: String, HostName: String, MACAddress: String, Manufacturer: String,
                Mode: String, Model: String, OSVersion: String, Path: String,
                ComplianceStatus: Boolean, IsAgentOnline: Boolean,
                IsVirtual: Boolean, Platform: String, ExtraInfo: String) : super(
            Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode, Model, OSVersion,
            Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform, ExtraInfo)

    constructor(cursor: Cursor) : super(cursor)

    override fun getDevice(): NotYetSupportedDevice {
        return this
    }

}