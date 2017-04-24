package com.mdmobile.pocketconsole.gson;


public class Server {

    public final class DeploymentServer {
        private String primaryManagementAddress, secondaryManagementAddress, primaryAgentAddress, secondaryAgentAddress,
                deviceManagementAddress, name, status;
        private Boolean isConnected;
        private int pulseTimeout, ruleReload, scheduleInterval, minThreads, maxThread, maxBurstThreads, pulseWaitInterval,
                connectedDeviceCount, connectedManagerCount;

        public DeploymentServer(String primaryManagementAddress, String secondaryManagementAddress,
                                String primaryAgentAddress, String secondaryAgentAddress, String deviceManagementAddress,
                                String name, String status, Boolean isConnected, int pulseTimeout,
                                int ruleReload, int scheduleInterval, int minThreads, int maxThread,
                                int maxBurstThreads, int pulseWaitInterval, int connectedDeviceCount, int connectedManagerCount) {
            this.primaryManagementAddress = primaryManagementAddress;
            this.secondaryManagementAddress = secondaryManagementAddress;
            this.primaryAgentAddress = primaryAgentAddress;
            this.secondaryAgentAddress = secondaryAgentAddress;
            this.deviceManagementAddress = deviceManagementAddress;
            this.name = name;
            this.status = status;
            this.isConnected = isConnected;
            this.pulseTimeout = pulseTimeout;
            this.ruleReload = ruleReload;
            this.scheduleInterval = scheduleInterval;
            this.minThreads = minThreads;
            this.maxThread = maxThread;
            this.maxBurstThreads = maxBurstThreads;
            this.pulseWaitInterval = pulseWaitInterval;
            this.connectedDeviceCount = connectedDeviceCount;
            this.connectedManagerCount = connectedManagerCount;
        }

        public String getPrimaryManagementAddress() {
            return primaryManagementAddress;
        }

        public String getSecondaryManagementAddress() {
            return secondaryManagementAddress;
        }

        public String getPrimaryAgentAddress() {
            return primaryAgentAddress;
        }

        public String getSecondaryAgentAddress() {
            return secondaryAgentAddress;
        }

        public String getDeviceManagementAddress() {
            return deviceManagementAddress;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public Boolean getConnected() {
            return isConnected;
        }

        public int getPulseTimeout() {
            return pulseTimeout;
        }

        public int getRuleReload() {
            return ruleReload;
        }

        public int getScheduleInterval() {
            return scheduleInterval;
        }

        public int getMinThreads() {
            return minThreads;
        }

        public int getMaxThread() {
            return maxThread;
        }

        public int getMaxBurstThreads() {
            return maxBurstThreads;
        }

        public int getPulseWaitInterval() {
            return pulseWaitInterval;
        }

        public int getConnectedDeviceCount() {
            return connectedDeviceCount;
        }

        public int getConnectedManagerCount() {
            return connectedManagerCount;
        }
    }


    public final class ManagementServer {
        private String fqdn, description, statusTime, macAddress, name, status;
        private int portNumber, totalConsoleUsers;

        public ManagementServer(String fqdn, String description, String statusTime,
                                String macAddress, String name, String status, int portNumber, int totalConsoleUsers) {
            this.fqdn = fqdn;
            this.description = description;
            this.statusTime = statusTime;
            this.macAddress = macAddress;
            this.name = name;
            this.status = status;
            this.portNumber = portNumber;
            this.totalConsoleUsers = totalConsoleUsers;
        }

        public String getFqdn() {
            return fqdn;
        }

        public String getDescription() {
            return description;
        }

        public String getStatusTime() {
            return statusTime;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public int getPortNumber() {
            return portNumber;
        }

        public int getTotalConsoleUsers() {
            return totalConsoleUsers;
        }
    }
}
