package com.mdmobile.pocketconsole.dataModels.api.devices

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.support.annotation.CallSuper
import com.google.gson.annotations.SerializedName
import com.mdmobile.pocketconsole.provider.McContract
import com.mdmobile.pocketconsole.utils.DbData

abstract class BasicDevice(val Kind: String = "N/A", val DeviceId: String = "N/A", val DeviceName: String = "N/A", val EnrollmentTime: String = "N/A",
                           val Family: String = "N/A", val HostName: String = "N/A", val MACAddress: String = "N/A", val Manufacturer: String = "N/A",
                           val Mode: String = "N/A", val Model: String = "N/A", val OSVersion: String = "N/A", val Path: String = "N/A",
                           val ComplianceStatus: Boolean = false, val IsAgentOnline: Boolean = false,
                           val IsVirtual: Boolean = false, val Platform: String = "N/A", val ExtraInfo: String = "N/A") {

    constructor(cursor: Cursor) : this(
            Kind = cursor.getString(1),
            ComplianceStatus = cursor.getInt(2) == 1,
            DeviceId = cursor.getString(3),
            DeviceName = cursor.getString(4),
            Family = cursor.getString(5),
            HostName = cursor.getString(6),
            IsAgentOnline = cursor.getInt(7) == 1,
            IsVirtual = cursor.getInt(8) == 1,
            MACAddress = cursor.getString(9),
            Manufacturer = cursor.getString(10),
            Mode = cursor.getString(11),
            Model = cursor.getString(12),
            OSVersion = cursor.getString(13),
            Path = cursor.getString(14),
            Platform = cursor.getString(15),
            EnrollmentTime = cursor.getString(23),
            ExtraInfo = cursor.getString(24)
    )

    @SerializedName("Memory")
    val memory: Memory = Memory()
    @SerializedName("ComplianceItem")
    val complianceItemList: List<ComplianceItem> = ArrayList()
    @SerializedName("CustomAttributes")
    val customAttributesList: List<CustomAttributes> = ArrayList()

    private val complianceStatus: Int
        get() = if (ComplianceStatus) 1 else 0

    private val agentOnline: Int
        get() = if (IsAgentOnline) 1 else 0

    private val virtual: Int
        get() = if (IsVirtual) 1 else 0

    private val extraInfo: Bundle
        get() = DbData.getDeviceExtraInfo(ExtraInfo)

    //Inner class for nested objects
    class CustomAttributes(val Name: String = "N/A", val Value: String = "N/A", val DataType: Boolean = false) {
        val dataType: Int
            get() = if (DataType) 1 else 0
    }

    //Inner class for nested objects
    class ComplianceItems(val ComplianceType: String = "N/A", val ComplianceValue: Boolean = false) {
        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0

    }

    //Inner class for nested objects
    inner class Memory(val AvailableExternalStorage: Long = 0, val AvailableMemory: Long = 0,
                       val AvailableSDCardStorage: Long = 0, val TotalExternalStorage: Long = 0,
                       val TotalMemory: Long = 0, val TotalSDCardStorage: Long = 0, val TotalStorage: Long = 0)


    class ComplianceItem(val ComplianceType: String = "N/A", private val ComplianceValue: Boolean = false) {
        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0
    }

    @CallSuper
    open fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(McContract.Device.COLUMN_KIND, this.Kind)
        values.put(McContract.Device.COLUMN_DEVICE_ID, this.DeviceId)
        values.put(McContract.Device.COLUMN_DEVICE_NAME, this.DeviceName)
        values.put(McContract.Device.COLUMN_ENROLLMENT_TIME, this.EnrollmentTime)
        values.put(McContract.Device.COLUMN_FAMILY, this.Family)
        values.put(McContract.Device.COLUMN_HOST_NAME, this.HostName)
        values.put(McContract.Device.COLUMN_MAC_ADDRESS, this.MACAddress)
        values.put(McContract.Device.COLUMN_MANUFACTURER, this.Manufacturer)
        values.put(McContract.Device.COLUMN_MODE, this.Mode)
        values.put(McContract.Device.COLUMN_MODEL, this.Model)
        values.put(McContract.Device.COLUMN_OS_VERSION, this.OSVersion)
        values.put(McContract.Device.COLUMN_PATH, this.Path)
        values.put(McContract.Device.COLUMN_COMPLIANCE_STATUS, this.complianceStatus)
        values.put(McContract.Device.COLUMN_AGENT_ONLINE, this.agentOnline)
        values.put(McContract.Device.COLUMN_VIRTUAL, this.virtual)
        values.put(McContract.Device.COLUMN_PLATFORM, this.Platform)
        values.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, this.Memory().AvailableSDCardStorage)
        values.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, this.memory.AvailableMemory)
        values.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, this.memory.TotalExternalStorage)
        values.put(McContract.Device.COLUMN_TOTAL_MEMORY, this.memory.TotalMemory)
        values.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, this.memory.TotalSDCardStorage)
        values.put(McContract.Device.COLUMN_TOTAL_STORAGE, this.memory.TotalStorage)
        values.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, this.memory.AvailableExternalStorage)
        return values
    }
}
