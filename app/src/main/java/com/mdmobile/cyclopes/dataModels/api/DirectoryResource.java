package com.mdmobile.cyclopes.dataModels.api;


public class DirectoryResource {
    private String kind, name, sid;

    public DirectoryResource(String kind, String name, String sid) {
        this.kind = kind;
        this.name = name;
        this.sid = sid;
    }

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public String getSid() {
        return sid;
    }
}
