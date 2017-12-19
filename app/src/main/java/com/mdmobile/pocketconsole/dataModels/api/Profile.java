package com.mdmobile.pocketconsole.dataModels.api;


public class Profile {

    private String referenceId, name, status, assignmentDate;
    private int versionNumber;
    private Boolean isMandatory;

    public Profile(String referenceId, String name, String status, String assignmentDate, int versionNumber, Boolean isMandatory) {
        this.referenceId = referenceId;
        this.name = name;
        this.status = status;
        this.assignmentDate = assignmentDate;
        this.versionNumber = versionNumber;
        this.isMandatory = isMandatory;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }
}
