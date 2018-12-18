package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

//TODO:excluding some data as MC return null which is parsed and saved in DB -> when read from kotlin null causes issues
//    val Version: String = "N/A", /**val ShortVersion: String = "N/A"*,*/ val SizeInBytes: String = "N/A",
//    val DataSizeInBytes: String = "N/A",

@Entity(tableName = McContract.INSTALLED_APPLICATION_TABLE_NAME,
        foreignKeys = [ForeignKey(entity = Device::class, parentColumns = arrayOf("deviceId"), childColumns = arrayOf("deviceId"))])
class InstalledApps(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @field:SerializedName("DeviceId")
        val deviceId: String,
        @field:SerializedName("ApplicationId")
        val packageId: String,
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("Status")
        val status: String) {

    override fun equals(other: Any?): Boolean {
        return if (other is InstalledApps) {
            (other.id == this.id && other.deviceId == this.deviceId && other.packageId == this.packageId
                    && other.name == this.name && other.status == this.status)
        } else {
            super.equals(other)
        }
    }
}