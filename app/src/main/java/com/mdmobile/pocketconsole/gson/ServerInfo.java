package com.mdmobile.pocketconsole.gson;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerInfo {

    @SerializedName("DeploymentServers")
    List<DeploymentServer> deploymentServers;
    @SerializedName("ManagementServers")
    List<ManagementServer> managementServers;

    public List<DeploymentServer> getDeploymentServers() {
        return deploymentServers;
    }

    public List<ManagementServer> getManagementServers() {
        return managementServers;
    }

    public final class DeploymentServer {
        private String PrimaryManagementAddress, SecondaryManagementAddress, PrimaryAgentAddress, SecondaryAgentAddress,
                DeviceManagementAddress, Name, Status;
        private Boolean IsConnected;
        private int PulseTimeout, RuleReload, ScheduleInterval, MinThreads, MaxThread, MaxBurstThreads, PulseWaitInterval,
                ConnectedDeviceCount, ConnectedManagerCount, MsgQueueLength, CurrentThreadCount;

        public DeploymentServer(String PrimaryManagementAddress, String SecondaryManagementAddress,
                                String PrimaryAgentAddress, String SecondaryAgentAddress, String DeviceManagementAddress,
                                String Name, String Status, Boolean IsConnected, int PulseTimeout,
                                int RuleReload, int ScheduleInterval, int MinThreads, int MaxThread,
                                int MaxBurstThreads, int PulseWaitInterval, int ConnectedDeviceCount, int ConnectedManagerCount,
                                int MsgQueueLength, int CurrentThreadCount) {
            this.PrimaryManagementAddress = PrimaryManagementAddress;
            this.SecondaryManagementAddress = SecondaryManagementAddress;
            this.PrimaryAgentAddress = PrimaryAgentAddress;
            this.SecondaryAgentAddress = SecondaryAgentAddress;
            this.DeviceManagementAddress = DeviceManagementAddress;
            this.Name = Name;
            this.Status = Status;
            this.IsConnected = IsConnected;
            this.PulseTimeout = PulseTimeout;
            this.RuleReload = RuleReload;
            this.ScheduleInterval = ScheduleInterval;
            this.MinThreads = MinThreads;
            this.MaxThread = MaxThread;
            this.MaxBurstThreads = MaxBurstThreads;
            this.PulseWaitInterval = PulseWaitInterval;
            this.ConnectedDeviceCount = ConnectedDeviceCount;
            this.ConnectedManagerCount = ConnectedManagerCount;
            this.MsgQueueLength = MsgQueueLength;
            this.CurrentThreadCount = CurrentThreadCount;
        }

        public String getPrimaryManagementAddress() {
            return PrimaryManagementAddress;
        }

        public String getSecondaryManagementAddress() {
            return SecondaryManagementAddress;
        }

        public String getPrimaryAgentAddress() {
            return PrimaryAgentAddress;
        }

        public String getSecondaryAgentAddress() {
            return SecondaryAgentAddress;
        }

        public String getDeviceManagementAddress() {
            return DeviceManagementAddress;
        }

        public String getName() {
            return Name;
        }

        public String getStatus() {
            return Status;
        }

        public Boolean getConnected() {
            return IsConnected;
        }

        public int getPulseTimeout() {
            return PulseTimeout;
        }

        public int getRuleReload() {
            return RuleReload;
        }

        public int getScheduleInterval() {
            return ScheduleInterval;
        }

        public int getMinThreads() {
            return MinThreads;
        }

        public int getMaxThread() {
            return MaxThread;
        }

        public int getMaxBurstThreads() {
            return MaxBurstThreads;
        }

        public int getPulseWaitInterval() {
            return PulseWaitInterval;
        }

        public int getConnectedDeviceCount() {
            return ConnectedDeviceCount;
        }

        public int getConnectedManagerCount() {
            return ConnectedManagerCount;
        }

        public int getMsgQueueLength() {
            return MsgQueueLength;
        }

        public int getCurrentThreadCount() {
            return CurrentThreadCount;
        }
    }


    public final class ManagementServer {
        private String Fqdn, Description, StatusTime, MacAddress, Name, Status;
        private int PortNumber, TotalConsoleUsers;

        public ManagementServer(String fqdn, String description, String statusTime,
                                String macAddress, String name, String status, int portNumber, int totalConsoleUsers) {
            this.Fqdn = fqdn;
            this.Description = description;
            this.StatusTime = statusTime;
            this.MacAddress = macAddress;
            this.Name = name;
            this.Status = status;
            this.PortNumber = portNumber;
            this.TotalConsoleUsers = totalConsoleUsers;
        }

        public String getFqdn() {
            return Fqdn;
        }

        public String getDescription() {
            return Description;
        }

        public String getStatusTime() {
            return StatusTime;
        }

        public String getMacAddress() {
            return MacAddress;
        }

        public String getName() {
            return Name;
        }

        public String getStatus() {
            return Status;
        }

        public int getPortNumber() {
            return PortNumber;
        }

        public int getTotalConsoleUsers() {
            return TotalConsoleUsers;
        }
    }
}
