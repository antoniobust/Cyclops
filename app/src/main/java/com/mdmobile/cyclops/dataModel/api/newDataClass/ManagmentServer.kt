package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.MANAGEMENT_SERVER_TABLE_NAME,foreignKeys = [ForeignKey(
        entity = ServerInfo::class, parentColumns = arrayOf("id"), childColumns = arrayOf("instanceId")
)])
data class ManagmentServer(
        @PrimaryKey(autoGenerate = true)
        val id:Int,
        @field:SerializedName("Fqdn")
        val fqdn: String,
        @field:SerializedName("Description")
        val description: String,
        @field:SerializedName("StatusTime")
        val statusTime: String,
        @field:SerializedName("MacAddress")
        val macAddress: String,
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("Status")
        val status: String,
        @field:SerializedName("PortNumber")
        val portNumber: Int,
        @field:SerializedName("TotalConsoleUsers")
        val totalConsoleUsers: Int,
        val instanceId:Int)