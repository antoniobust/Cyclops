package com.mdmobile.pocketconsole.dataModels.api.devices

import com.google.gson.annotations.SerializedName

open class BasicDevice(val Kind: String = "", val DeviceId: String = "", val DeviceName: String = "", val EnrollmentTime: String = "",
                       val Family: String = "", val HostName: String = "", val MACAddress: String = "", val Manufacturer: String = "",
                       val Mode: String = "", val Model: String = "", val OSVersion: String = "", val Path: String = "",
                       private val ComplianceStatus: Boolean = false, private val IsAgentOnline: Boolean = false,
                       private val IsVirtual: Boolean = false, val Platform: String = "") {

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
    inner class CustomAttributes(name: String = "", value: String = "", internal var dataType: Boolean = false) {
        var name: String = ""
            internal set
        var value: String = ""
            internal set

        init {
            this.name = name
            this.value = value
        }

        fun getDataType(): Int {
            return if (dataType) 1 else 0
        }
    }

    //Inner class for nested objects
    inner class ComplianceItems(complianceType: String = "", var ComplianceValue: Boolean = false) {
        var complianceType: String = ""
            internal set

        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0

        init {
            this.complianceType = complianceType
        }
    }

    //Inner class for nested objects
    inner class Memory(val availableExternalStorage: Long = 0, val availableMemory: Long = 0, val availableSDCardStorage: Long = 0, val availableStorage: Long = 0,
                       val totalExternalStorage: Long = 0, val totalMemory: Long = 0, val totalSDCardStorage: Long = 0, val totalStorage: Long = 0)


    inner class ComplianceItem(val complianceType: String = "", private val ComplianceValue: Boolean = false) {

        val complianceValue: Int
            get() = if (ComplianceValue) 1 else 0
    }
}
