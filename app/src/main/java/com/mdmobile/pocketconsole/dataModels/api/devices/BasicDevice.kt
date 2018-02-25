package com.mdmobile.pocketconsole.dataModels.api.devices

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
open class BasicDevice(val Kind: String = "", val DeviceId: String = "", val DeviceName: String = "", val EnrollmentTime: String = "",
                       val Family: String = "", val HostName: String = "", val MACAddress: String = "", val Manufacturer: String = "",
                       val Mode: String = "", val Model: String = "", val OSVersion: String = "", val Path: String = "",
                       private val ComplianceStatus: Boolean = false, private val IsAgentOnline: Boolean = false,
                       private val IsVirtual: Boolean = false, val Platform: String = "") : Parcelable {

    @SerializedName("Memory")
    var memory: Memory = Memory()
    @SerializedName("ComplianceItem")
    var complianceItemList: List<ComplianceItem> = ArrayList()
    @SerializedName("CustomAttributes")
    var customAttributesList: List<CustomAttributes> = ArrayList()

    val complianceStatus: Int
        get() = if (ComplianceStatus) 1 else 0

    val agentOnline: Int
        get() = if (IsAgentOnline) 1 else 0

    val virtual: Int
        get() = if (IsVirtual) 1 else 0


    //Inner class for nested objects
    @Parcelize
    class CustomAttributes(val Name: String = "", val Value: String = "", val DataType: Boolean = false) : Parcelable {
        val dataType: Int
            get() = if (DataType) 1 else 0
    }

    //Inner class for nested objects
    @Parcelize
    class ComplianceItems(val ComplianceType: String = "", val ComplianceValue: Boolean = false) : Parcelable {
        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0

    }

    //Inner class for nested objects
    inner class Memory(val AvailableExternalStorage: Long = 0, val AvailableMemory: Long = 0,
                 val AvailableSDCardStorage: Long = 0, val AvailableStorage: Long = 0,
                 val TotalExternalStorage: Long = 0, val TotalMemory: Long = 0, val TotalSDCardStorage: Long = 0,
                 val TotalStorage: Long = 0)


    @Parcelize
    class ComplianceItem(val ComplianceType: String = "", private val ComplianceValue: Boolean = false) : Parcelable {
        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0
    }
}
