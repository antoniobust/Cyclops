package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName

data class ServerInfo(
        @field:SerializedName("DeploymentServers")
        val deploymentServer: List<DeploymentServer>,
        @field:SerializedName("ManagementServers")
        val managementServer: List<ManagementServer>,
        @field:SerializedName("ProductVersion")
        val ProductVersion: String?,
        @field:SerializedName("ProductVersionBuild")
        val ProductVersionBuild: String?)