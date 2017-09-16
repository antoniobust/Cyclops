package com.mdmobile.pocketconsole.gson;


public class Group {

    private String name, path, icon, kind;

    public Group(String name, String path, String icon, String kind) {
        this.name = name;
        this.path = path;
        this.icon = icon;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getIcon() {
        return icon;
    }

    public String getKind() {
        return kind;
    }
}
