package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.USER_TABLE_NAME, foreignKeys = [ForeignKey(
        entity = InstanceInfo::class, parentColumns = arrayOf("id"), childColumns = arrayOf("instanceId")
)])
data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("DisplayName")
        val displayName: String,
        @field:SerializedName("Kind")
        val kind: String,
        @field:SerializedName("EulaAcceptanceDate")
        val eulaAcceptanceDate: String,
        @field:SerializedName("IsEulaAccepted")
        val isEulaAccepted: Boolean,
        @field:SerializedName("IsAccountLocked")
        val isAccountLocked: Boolean,
        @field:SerializedName("NumberOfFailedLogins")
        val numberOfFailedLogins: Int,
        val instanceId: Int
)
