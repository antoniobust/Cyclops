package com.mdmobile.cyclops.dataModel.api.devices

import android.content.ContentValues
import android.database.Cursor
import com.mdmobile.cyclops.provider.McContract
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties


class WindowsPhone : BasicDevice, IDevice<WindowsPhone> {

    constructor(Kind: String = "N/A", DeviceId: String = "N/A", DeviceName: String = "N/A", EnrollmentTime: String = "N/A",
                Family: String = "N/A", HostName: String = "N/A", MACAddress: String = "N/A", Manufacturer: String = "N/A",
                Mode: String = "N/A", Model: String = "N/A", OSVersion: String = "N/A", Path: String = "N/A",
                ComplianceStatus: Boolean = false, IsAgentOnline: Boolean = false,
                IsVirtual: Boolean = false, Platform: String = "N/A")
            : super(Kind, DeviceId, DeviceName, EnrollmentTime, Family, HostName, MACAddress, Manufacturer, Mode,
            Model, OSVersion, Path, ComplianceStatus, IsAgentOnline, IsVirtual, Platform)

    constructor(c: Cursor) : super(c)

    override fun getDevice(): WindowsPhone {
        return this
    }

//    override fun toContentValues(): ContentValues {
//        val values = super.toContentValues()
//        val stringBuilder = StringBuilder()
//        this::class.declaredMemberProperties.forEach {
//            if (it.visibility == KVisibility.PUBLIC) {
//                stringBuilder.append(it.name).append("=")
//                        .append(it.getter.call(this).toString())
//                        .append(";")
//            }
//        }
//        values.put(McContract.Device.COLUMN_EXTRA_INFO, stringBuilder.toString())
//        return values
//    }
}

//TODO: support win phone extra fields