package com.mdmobile.cyclops.dataModel.api;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class ServerInfo implements Parcelable {

    public static final Creator<ServerInfo> CREATOR = new Creator<ServerInfo>() {
        @Override
        public ServerInfo createFromParcel(Parcel in) {
            return new ServerInfo(in);
        }

        @Override
        public ServerInfo[] newArray(int size) {
            return new ServerInfo[size];
        }
    };
    @SerializedName("DeploymentServers")
    List<DeploymentServer> deploymentServers;
    @SerializedName("ManagementServers")
    List<ManagementServer> managementServers;
    String ProductVersion, ProductVersionBuild;

    public ServerInfo(Parcel in) {
        deploymentServers = in.createTypedArrayList(DeploymentServer.CREATOR);
        managementServers = in.createTypedArrayList(ManagementServer.CREATOR);
    }

    public ServerInfo(String ProductVersion, String ProductVersionBuild) {
        this.ProductVersion = ProductVersion;
        this.ProductVersionBuild = ProductVersionBuild;

    }

    public Integer getProductVersion() {
        //If product Version is not populated is because in v13 APIs there wasn't such info.
        //Assuming we are using a v13.3
        if(ProductVersion == null){
            return 1330;
        }
        StringBuilder builder = new StringBuilder();
        String[] array = ProductVersion.split("\\.");
        for (String value : array) {
            builder.append(value);
        }
        Integer v = Integer.valueOf(builder.toString());

        return v == 0 ? 1330 : v;
    }

    public Integer getProductVersionBuild() {
        //If product Version is not populated is because in v13 APIs there wasn't such info.
        //Assuming we are using build 3766
        if(ProductVersionBuild == null){
            return 3766;
        }
        StringBuilder builder = new StringBuilder();
        String[] array = ProductVersionBuild.split("\\.");
        for (String value : array) {
            builder.append(value);
        }
        Integer b = Integer.valueOf(builder.toString());
        return b == 0 ? 3766 : b;
    }

    public List<DeploymentServer> getDeploymentServers() {
        return deploymentServers;
    }

    public List<ManagementServer> getManagementServers() {
        return managementServers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(deploymentServers);
        parcel.writeTypedList(managementServers);
    }

    public static final class DeploymentServer implements Parcelable {
        public static final Creator<DeploymentServer> CREATOR = new Creator<DeploymentServer>() {
            @Override
            public DeploymentServer createFromParcel(Parcel in) {
                return new DeploymentServer(in);
            }

            @Override
            public DeploymentServer[] newArray(int size) {
                return new DeploymentServer[size];
            }
        };
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

        public DeploymentServer(Parcel in) {
            PrimaryManagementAddress = in.readString();
            SecondaryManagementAddress = in.readString();
            PrimaryAgentAddress = in.readString();
            SecondaryAgentAddress = in.readString();
            DeviceManagementAddress = in.readString();
            Name = in.readString();
            Status = in.readString();
            PulseTimeout = in.readInt();
            RuleReload = in.readInt();
            ScheduleInterval = in.readInt();
            MinThreads = in.readInt();
            MaxThread = in.readInt();
            MaxBurstThreads = in.readInt();
            PulseWaitInterval = in.readInt();
            ConnectedDeviceCount = in.readInt();
            ConnectedManagerCount = in.readInt();
            MsgQueueLength = in.readInt();
            CurrentThreadCount = in.readInt();
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

        public String[] infoToArray() {
            //keep the following order to match @Array -> ds_information_labels
            return new String[]{getName(), getStatus(), "Yes/No", String.valueOf(getConnectedDeviceCount()),
                    String.valueOf(getMsgQueueLength()), String.valueOf(getConnectedManagerCount()), getPrimaryAgentAddress(), getSecondaryAgentAddress(),
                    getDeviceManagementAddress(), getPrimaryManagementAddress(), getSecondaryManagementAddress(), String.valueOf(getCurrentThreadCount()),
                    String.valueOf(getMinThreads()), String.valueOf(getMaxThread()), String.valueOf(getMaxBurstThreads()), String.valueOf(getScheduleInterval()),
                    String.valueOf(getRuleReload())};
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(getPrimaryManagementAddress());
            parcel.writeString(getSecondaryManagementAddress());
            parcel.writeString(getPrimaryAgentAddress());
            parcel.writeString(getSecondaryAgentAddress());
            parcel.writeString(getDeviceManagementAddress());
            parcel.writeString(getName());
            parcel.writeString(getStatus());
            parcel.writeByte((byte) (getConnected() ? 1 : 0));
            parcel.writeInt(getPulseTimeout());
            parcel.writeInt(getRuleReload());
            parcel.writeInt(getScheduleInterval());
            parcel.writeInt(getMinThreads());
            parcel.writeInt(getMaxThread());
            parcel.writeInt(getMaxBurstThreads());
            parcel.writeInt(getPulseWaitInterval());
            parcel.writeInt(getConnectedDeviceCount());
            parcel.writeInt(getMsgQueueLength());
            parcel.writeInt(getCurrentThreadCount());
        }
    }


    public static final class ManagementServer implements Parcelable {
        public static final Creator<ManagementServer> CREATOR = new Creator<ManagementServer>() {
            @Override
            public ManagementServer createFromParcel(Parcel in) {
                return new ManagementServer(in);
            }

            @Override
            public ManagementServer[] newArray(int size) {
                return new ManagementServer[size];
            }
        };
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

        protected ManagementServer(Parcel in) {
            Fqdn = in.readString();
            Description = in.readString();
            StatusTime = in.readString();
            MacAddress = in.readString();
            Name = in.readString();
            Status = in.readString();
            PortNumber = in.readInt();
            TotalConsoleUsers = in.readInt();
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

        public String[] infoToArray() {
            return new String[]{getName(), getFqdn(), getDescription(), getStatus(), getStatusTime(), getMacAddress(),
                    String.valueOf(getPortNumber()), String.valueOf(getTotalConsoleUsers())};
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(Fqdn);
            parcel.writeString(Description);
            parcel.writeString(StatusTime);
            parcel.writeString(MacAddress);
            parcel.writeString(Name);
            parcel.writeString(Status);
            parcel.writeInt(PortNumber);
            parcel.writeInt(TotalConsoleUsers);
        }
    }
}
