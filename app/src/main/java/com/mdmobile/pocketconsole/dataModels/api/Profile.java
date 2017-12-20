package com.mdmobile.pocketconsole.dataModels.api;


public class Profile {

    private String ReferenceId, Name, Status, AssignmentDate;
    private int VersionNumber;
    private Boolean IsMandatory;

    public Profile(String referenceId, String name, String status, String assignmentDate, int versionNumber, Boolean isMandatory) {
        this.ReferenceId = referenceId;
        this.Name = name;
        this.Status = status;
        this.AssignmentDate = assignmentDate;
        this.VersionNumber = versionNumber;
        this.IsMandatory = isMandatory;
    }

    public String getReferenceId() {
        return ReferenceId;
    }

    public String getName() {
        return Name;
    }

    public String getStatus() {
        return Status;
    }

    public String getAssignmentDate() {
        return AssignmentDate;
    }

    public int getVersionNumber() {
        return VersionNumber;
    }

    public Boolean getMandatory() {
        return IsMandatory;
    }
}
