package com.mdmobile.cyclops.dataModel.api.devices

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.CallSuper
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.utils.LabelHelper
import com.mdmobile.cyclops.utils.Property
import kotlinx.android.parcel.Parcelize
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

@Parcelize
open class BasicDevice(val Kind: String = "N/A", val DeviceId: String = "N/A", val DeviceName: String = "N/A", val EnrollmentTime: String = "N/A",
                       val Family: String = "N/A", val HostName: String = "N/A", val MACAddress: String = "N/A", val Manufacturer: String = "N/A",
                       val Mode: String = "N/A", val Model: String = "N/A", val OSVersion: String = "N/A", val Path: String = "N/A",
                       val ComplianceStatus: Boolean = false, val IsAgentOnline: Boolean = false,
                       val IsVirtual: Boolean = false, val Platform: String = "N/A", val ExtraInfo: String = "N/A") : Parcelable {

    constructor(cursor: Cursor) : this(
            Kind = nullSafe(cursor.getString(1)),
            ComplianceStatus = cursor.getInt(2) == 1,
            DeviceId = nullSafe(cursor.getString(3)),
            DeviceName = nullSafe(cursor.getString(4)),
            Family = nullSafe(cursor.getString(5)),
            HostName = nullSafe(cursor.getString(6)),
            IsAgentOnline = cursor.getInt(7) == 1,
            IsVirtual = cursor.getInt(8) == 1,
            MACAddress = nullSafe(cursor.getString(9)),
            Manufacturer = nullSafe(cursor.getString(10)),
            Mode = nullSafe(cursor.getString(11)),
            Model = nullSafe(cursor.getString(12)),
            OSVersion = nullSafe(cursor.getString(13)),
            Path = nullSafe(cursor.getString(14)),
            Platform = nullSafe(cursor.getString(15)),
            EnrollmentTime = nullSafe(cursor.getString(23)),
            ExtraInfo = nullSafe(cursor.getString(24))
    )

    companion object {
         fun nullSafe(obj: Any?): String {
            return if (obj == null) "N/A" else obj as String
        }
    }

    @SerializedName("Memory")
    val memory: Memory = Memory()
    @SerializedName("ComplianceItem")
    val complianceItemList: List<ComplianceItem> = ArrayList()
    @SerializedName("CustomAttributes")
    val customAttributesList: List<CustomAttributes> = ArrayList()

    private val complianceStatus: String
        get() = if (ComplianceStatus) "Compliant" else "Non-Compliant"

    private val agentOnline: String
        get() = if (IsAgentOnline) "Online" else "Offline"

    private val virtual: Int
        get() = if (IsVirtual) 1 else 0

    private val extraInfo: Bundle
        get() = extraInfoStringToBundle(ExtraInfo)

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
        values.put(McContract.Device.COLUMN_COMPLIANCE_STATUS, this.ComplianceStatus)
        values.put(McContract.Device.COLUMN_AGENT_ONLINE, this.IsAgentOnline)
        values.put(McContract.Device.COLUMN_VIRTUAL, this.virtual)
        values.put(McContract.Device.COLUMN_PLATFORM, this.Platform)
        if (this.memory != null) {
            values.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, this.memory.AvailableSDCardStorage)
            values.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, this.memory.AvailableMemory)
            values.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, this.memory.TotalExternalStorage)
            values.put(McContract.Device.COLUMN_TOTAL_MEMORY, this.Memory().TotalMemory)
            values.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, this.memory.TotalSDCardStorage)
            values.put(McContract.Device.COLUMN_TOTAL_STORAGE, this.memory.TotalStorage)
            values.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, this.memory.AvailableExternalStorage)
        }
        values.put(McContract.Device.COLUMN_EXTRA_INFO, formatExtraInfo())
        return values
    }

    fun toContentValues(serverId: Int): ContentValues {
        val values = toContentValues()
        values.put(McContract.Device.COLUMN_SERVER_ID, serverId)
        return values
    }

    fun getExtraAttributesList(): ArrayList<Property> {
        val list = ArrayList<Property>()
        this::class.declaredMemberProperties.forEach {
            if (it.visibility == KVisibility.PUBLIC) {
                list.add(Property(it.name, LabelHelper.getUiLabelFor(it.name), true))
            }
        }
        return list
    }


    fun extraInfoStringToBundle(extraInfo: String): Bundle {
        val extras = extraInfo.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var temp: Array<String>
        val extraBundle = Bundle()
        for (extra in extras) {
            temp = extra.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (temp.size == 1) {
                extraBundle.putString(temp[0], "N/A")
                continue
            }
            temp[0] = temp[0].substring(1, temp[0].length)
            extraBundle.putString(temp[0], temp[1])
        }
        return extraBundle
    }

    private fun formatExtraInfo(): String {
        val stringBuilder = StringBuilder()
        this::class.declaredMemberProperties.forEach {
            if (it.visibility == KVisibility.PUBLIC) {
                stringBuilder.append(it.name).append("=")
                        .append(it.getter.call(this).toString())
                        .append(";")
            }
        }
        return stringBuilder.toString()
    }
}
