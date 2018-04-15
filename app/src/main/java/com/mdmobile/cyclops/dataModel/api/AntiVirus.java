package com.mdmobile.cyclops.dataModel.api;

/**
 * Represent AntiVirus Gson class
 */


public class AntiVirus {

    private String AntivirusDefinitionsVersion, LastEmptyQuarantine, LastVirusDefUpdate, LastVirusScan;

    public AntiVirus(String antivirusDefinitionsVersion, String lastEmptyQuarantine, String lastVirusDefUpdate, String lastVirusScan) {
        AntivirusDefinitionsVersion = antivirusDefinitionsVersion;
        LastEmptyQuarantine = lastEmptyQuarantine;
        LastVirusDefUpdate = lastVirusDefUpdate;
        LastVirusScan = lastVirusScan;
    }

    public String getAntivirusDefinitionsVersion() {
        return AntivirusDefinitionsVersion;
    }

    public String getLastEmptyQuarantine() {
        return LastEmptyQuarantine;
    }

    public String getLastVirusDefUpdate() {
        return LastVirusDefUpdate;
    }

    public String getLastVirusScan() {
        return LastVirusScan;
    }
}

