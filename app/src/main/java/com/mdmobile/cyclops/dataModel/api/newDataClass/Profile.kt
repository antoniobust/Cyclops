package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.PROFILE_TABLE_NAME)
data class Profile(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @PrimaryKey(autoGenerate = false)
        @field:SerializedName("ReferenceId")
        val referenceId: String = "N/A",
        @field:SerializedName("Name")
        val name: String = "N/A",
        @field:SerializedName("Status")
        val status: String = "N/A",
        @field:SerializedName("AssignmentDate")
        val assignmentDate: String = "N/A",
        @field:SerializedName("VersionNumber")
        val versionNumber: Int = -1,
        @field:SerializedName("Mandatory")
        val mandatory: Boolean = false)

