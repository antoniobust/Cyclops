package com.mdmobile.pocketconsole.dataModels.api.devices

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Parcelable
import android.support.annotation.CallSuper
import com.google.gson.annotations.SerializedName
import com.mdmobile.pocketconsole.dataTypes.DeviceAttributes.BasicDevice.*
import com.mdmobile.pocketconsole.provider.McContract
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
open class BasicDevice(val Kind: String = "N/A", val DeviceId: String = "N/A", val DeviceName: String = "N/A", val EnrollmentTime: String = "N/A",
                       val Family: String = "N/A", val HostName: String = "N/A", val MACAddress: String = "N/A", val Manufacturer: String = "N/A",
                       val Mode: String = "N/A", val Model: String = "N/A", val OSVersion: String = "N/A", val Path: String = "N/A",
                       private val ComplianceStatus: Boolean = false, private val IsAgentOnline: Boolean = false,
                       private val IsVirtual: Boolean = false, val Platform: String = "N/A") : Parcelable {

    @SerializedName("Memory")
    var memory: Memory = Memory()
    @SerializedName("ComplianceItem")
    var complianceItemList: List<ComplianceItem> = ArrayList()
    @SerializedName("CustomAttributes")
    var customAttributesList: List<CustomAttributes> = ArrayList()

    private val complianceStatus: Int
        get() = if (ComplianceStatus) 1 else 0

    private val agentOnline: Int
        get() = if (IsAgentOnline) 1 else 0

    private val virtual: Int
        get() = if (IsVirtual) 1 else 0



    //Inner class for nested objects
    @Parcelize
    class CustomAttributes(val Name: String = "N/A", val Value: String = "N/A", val DataType: Boolean = false) : Parcelable {
        val dataType: Int
            get() = if (DataType) 1 else 0
    }

    //Inner class for nested objects
    @Parcelize
    class ComplianceItems(val ComplianceType: String = "N/A", val ComplianceValue: Boolean = false) : Parcelable {
        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0

    }

    //Inner class for nested objects
    inner class Memory(val AvailableExternalStorage: Long = 0, val AvailableMemory: Long = 0,
                       val AvailableSDCardStorage: Long = 0, val AvailableStorage: Long = 0,
                       val TotalExternalStorage: Long = 0, val TotalMemory: Long = 0, val TotalSDCardStorage: Long = 0,
                       val TotalStorage: Long = 0)


    @Parcelize
    class ComplianceItem(val ComplianceType: String = "N/A", private val ComplianceValue: Boolean = false) : Parcelable {
        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0
    }

    @CallSuper
    public open fun toContentValues(): ContentValues {
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
        values.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, this.Memory().AvailableMemory)
        values.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, this.Memory().TotalExternalStorage)
        values.put(McContract.Device.COLUMN_TOTAL_MEMORY, this.Memory().TotalMemory)
        values.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, this.Memory().TotalSDCardStorage)
        values.put(McContract.Device.COLUMN_TOTAL_STORAGE, this.Memory().TotalStorage)
        return values
    }
}
