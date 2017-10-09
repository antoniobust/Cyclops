package com.mdmobile.pocketconsole.gson;


public class User {
    private String Name, DisplayName, Kind, EulaAcceptanceDate;
    private Boolean IsEulaAccepted, IsAccountLocked;
    private int NumberOfFailedLogins;

    public User(String name, String displayName, String kind, String eulaAcceptanceDate,
                Boolean isEulaAccepted, Boolean isAccountLocked, int numberOfFailedLogins) {
        this.Name = name;
        this.DisplayName = displayName;
        this.Kind = kind;
        this.EulaAcceptanceDate = eulaAcceptanceDate;
        this.IsEulaAccepted = isEulaAccepted;
        this.IsAccountLocked = isAccountLocked;
        this.NumberOfFailedLogins = numberOfFailedLogins;
    }

    public String getName() {
        return Name;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public String getKind() {
        return Kind;
    }

    public String getEulaAcceptanceDate() {
        if (IsEulaAccepted == null) {
            return null;
        } else {
            return EulaAcceptanceDate;
        }
    }

    public int getEulaAccepted() {
        if (IsEulaAccepted == null) {
            return -1;
        } else {
            return IsEulaAccepted ? 1 : 0;
        }
    }

    public int getAccountLocked() {
        return IsAccountLocked ? 1 : 0;
    }

    public int getNumberOfFailedLogins() {
        return NumberOfFailedLogins;
    }
}
