package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName


data class Profile(
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

