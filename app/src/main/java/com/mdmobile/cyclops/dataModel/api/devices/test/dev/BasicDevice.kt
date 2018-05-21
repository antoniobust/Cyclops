package com.mdmobile.cyclops.dataModel.api.devices.test.dev

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.dataModel.api.devices.test.Memory
import com.mdmobile.cyclops.provider.McContract
import kotlinx.android.parcel.Parcelize
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

@Parcelize
open class BasicDevice(
        @SerializedName("Kind") val kind: String = "",
        @SerializedName("ComplianceStatus") val complianceStatus: Boolean = false,
        @SerializedName("ComplianceItems") val complianceItems: List<ComplianceItem> = listOf(),
        @SerializedName("DeviceId") val deviceId: String = "",
        @SerializedName("DeviceName") val deviceName: String = "",
        @SerializedName("EnrollmentTime") val enrollmentTime: String = "",
        @SerializedName("Family") val family: String = "",
        @SerializedName("HostName") val hostName: String = "",
        @SerializedName("IsAgentOnline") val isAgentOnline: Boolean = false,
        @SerializedName("IsVirtual") val isVirtual: Boolean = false,
        @SerializedName("CustomAttributes") val customAttributes: List<CustomAttribute> = listOf(),
        @SerializedName("MACAddress") val MACAddress: String = "",
        @SerializedName("Manufacturer") val manufacturer: String = "",
        @SerializedName("Mode") val mode: String = "",
        @SerializedName("Model") val model: String = "",
        @SerializedName("OSVersion") val OSVersion: String = "",
        @SerializedName("Path") val path: String = "",
        @SerializedName("Platform") val platform: String = "",
        @SerializedName("Memory") val memory: Memory = Memory(),
        val extraInfo: String = ""
) : Parcelable {

    constructor(cursor: Cursor) : this(
            kind = nullSafe(cursor.getString(1)),
            complianceStatus = cursor.getInt(2) == 1,
            deviceId = nullSafe(cursor.getString(3)),
            deviceName = nullSafe(cursor.getString(4)),
            family = nullSafe(cursor.getString(5)),
            hostName = nullSafe(cursor.getString(6)),
            isAgentOnline = cursor.getInt(7) == 1,
            isVirtual = cursor.getInt(8) == 1,
            MACAddress = nullSafe(cursor.getString(9)),
            manufacturer = nullSafe(cursor.getString(10)),
            mode = nullSafe(cursor.getString(11)),
            model = nullSafe(cursor.getString(12)),
            OSVersion = nullSafe(cursor.getString(13)),
            path = nullSafe(cursor.getString(14)),
            platform = nullSafe(cursor.getString(15)),
            enrollmentTime = nullSafe(cursor.getString(23)),
            memory = Memory(0, 0, 0, 0, 0, 0, 0),
            extraInfo = nullSafe(cursor.getString(24))
    )


    companion object {
        fun nullSafe(obj: Any?): String {
            return if (obj == null) "N/A" else obj as String
        }
    }

    @Parcelize
    class ComplianceItem(
            @SerializedName("ComplianceType") val complianceType: String = "",
            @SerializedName("ComplianceValue") val complianceValue: Boolean = false
    ) : Parcelable

    @Parcelize
    class CustomAttribute(
            @SerializedName("Name") val name: String = "",
            @SerializedName("Value") val value: String = "",
            @SerializedName("DataType") val dataType: String = ""
    ) : Parcelable

    open fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(McContract.Device.COLUMN_KIND, this.kind)
        values.put(McContract.Device.COLUMN_DEVICE_ID, this.deviceId)
        values.put(McContract.Device.COLUMN_DEVICE_NAME, this.deviceName)
        values.put(McContract.Device.COLUMN_ENROLLMENT_TIME, this.enrollmentTime)
        values.put(McContract.Device.COLUMN_FAMILY, this.family)
        values.put(McContract.Device.COLUMN_HOST_NAME, this.hostName)
        values.put(McContract.Device.COLUMN_MAC_ADDRESS, this.MACAddress)
        values.put(McContract.Device.COLUMN_MANUFACTURER, this.manufacturer)
        values.put(McContract.Device.COLUMN_MODE, this.mode)
        values.put(McContract.Device.COLUMN_MODEL, this.model)
        values.put(McContract.Device.COLUMN_OS_VERSION, this.OSVersion)
        values.put(McContract.Device.COLUMN_PATH, this.path)
        values.put(McContract.Device.COLUMN_COMPLIANCE_STATUS, this.complianceStatus)
        values.put(McContract.Device.COLUMN_AGENT_ONLINE, this.isAgentOnline)
        values.put(McContract.Device.COLUMN_VIRTUAL, this.isVirtual)
        values.put(McContract.Device.COLUMN_PLATFORM, this.platform)
        values.put(McContract.Device.COLUMN_AVAILABLE_SD_CARD_STORAGE, this.memory.availableSDCardStorage)
        values.put(McContract.Device.COLUMN_AVAILABLE_MEMORY, this.memory.availableMemory)
        values.put(McContract.Device.COLUMN_TOTAL_EXTERNAL_STORAGE, this.memory.totalExternalStorage)
        values.put(McContract.Device.COLUMN_TOTAL_MEMORY, this.memory.totalMemory)
        values.put(McContract.Device.COLUMN_TOTAL_SD_CARD_STORAGE, this.memory.totalSDCardStorage)
        values.put(McContract.Device.COLUMN_TOTAL_STORAGE, this.memory.totalStorage)
        values.put(McContract.Device.COLUMN_AVAILABLE_EXTERNAL_STORAGE, this.memory.availableExternalStorage)
        values.put(McContract.Device.COLUMN_EXTRA_INFO, formatExtraInfo())
        return values
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