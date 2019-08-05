package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName


data class DeploymentServer(@field:SerializedName("PrimaryManagementAddress")
                            val primaryManagementAddress: String = "N/A",
                            @field:SerializedName("SecondaryManagementAddress")
                            val secondaryManagementAddress: String = "N/A",
                            @field:SerializedName("PrimaryAgentAddress")
                            val primaryAgentAddress: String = "N/A",
                            @field:SerializedName("SecondaryAgentAddress")
                            val secondaryAgentAddress: String = "N/A",
                            @field:SerializedName("DeviceManagementAddress")
                            val deviceManagementAddress: String = "N/A",
                            @field:SerializedName("Name")
                            val name: String = "N/A",
                            @field:SerializedName("Status")
                            val status: String = "N/A",
                            @field:SerializedName("IsConnected")
                            val isConnected: Boolean = false,
                            @field:SerializedName("PulseTimeout")
                            val pulseTimeout: Int = -1,
                            @field:SerializedName("RuleReload")
                            val ruleReload: Int = -1,
                            @field:SerializedName("ScheduleInterval")
                            val scheduleInterval: Int = -1,
                            @field:SerializedName("MinThreads")
                            val minThreads: Int = -1,
                            @field:SerializedName("MaxThread")
                            val maxThread: Int = -1,
                            @field:SerializedName("MaxBurstThreads")
                            val maxBurstThreads: Int = -1,
                            @field:SerializedName("PulseWaitInterval")
                            val pulseWaitInterval: Int = -1,
                            @field:SerializedName("ConnectedDeviceCount")
                            val connectedDeviceCount: Int = -1,
                            @field:SerializedName("ConnectedManagerCount")
                            val connectedManagerCount: Int = -1,
                            @field:SerializedName("MsgQueueLength")
                            val msgQueueLength: Int = -1,
                            @field:SerializedName("CurrentThreadCount")
                            val currentThreadCount: Int = -1,
                            val instanceId: Int = -1)