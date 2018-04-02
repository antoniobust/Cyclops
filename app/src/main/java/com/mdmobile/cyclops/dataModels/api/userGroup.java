package com.mdmobile.cyclops.dataModels.api;


public class userGroup {

    private String name, groupKind;

    public userGroup(String name, String groupKind) {
        this.name = name;
        this.groupKind = groupKind;
    }

    public String getName() {
        return name;
    }

    public String getGroupKind() {
        return groupKind;
    }
}
