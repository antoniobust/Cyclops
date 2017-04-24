package com.mdmobile.pocketconsole.gson;


public class User {
    private String name, displayName, kind, eulaAcceptanceDate;
    private Boolean isEulaAccepted , isAccountLocked;
    private int numberOfFailedLogins;

    public User(String name, String displayName, String kind, String eulaAcceptanceDate,
                Boolean isEulaAccepted, Boolean isAccountLocked, int numberOfFailedLogins) {
        this.name = name;
        this.displayName = displayName;
        this.kind = kind;
        this.eulaAcceptanceDate = eulaAcceptanceDate;
        this.isEulaAccepted = isEulaAccepted;
        this.isAccountLocked = isAccountLocked;
        this.numberOfFailedLogins = numberOfFailedLogins;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKind() {
        return kind;
    }

    public String getEulaAcceptanceDate() {
        return eulaAcceptanceDate;
    }

    public Boolean getEulaAccepted() {
        return isEulaAccepted;
    }

    public Boolean getAccountLocked() {
        return isAccountLocked;
    }

    public int getNumberOfFailedLogins() {
        return numberOfFailedLogins;
    }
}
