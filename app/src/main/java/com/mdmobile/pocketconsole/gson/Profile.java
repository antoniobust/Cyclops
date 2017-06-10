package com.mdmobile.pocketconsole.gson;


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


    //Nested class representing nested json object
    public final class Configurations {
        private String name, status;

        public Configurations(String name, String status) {
            this.name = name;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }


        //Nested class representing nested json object
        public final class DeviceConfigurationType {
            public final class ConfigurationType {
                private String name;

                public ConfigurationType(String name) {
                    this.name = name;
                }

                public String getName() {
                    return name;
                }
            }

            public final class SubType {
                private String name;

                public SubType(String name) {
                    this.name = name;
                }

                public String getName() {
                    return name;
                }
            }
        }


        //Nested class representing nested json object
        public final class Packages {
            private String name, version, size, status, referenceId;

            public Packages(String name, String version, String size, String status, String referenceId) {
                this.name = name;
                this.version = version;
                this.size = size;
                this.status = status;
                this.referenceId = referenceId;
            }

            public String getName() {
                return name;
            }

            public String getVersion() {
                return version;
            }

            public String getSize() {
                return size;
            }

            public String getStatus() {
                return status;
            }

            public String getReferenceId() {
                return referenceId;
            }
        }
    }
}
