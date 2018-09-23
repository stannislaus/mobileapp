package com.driverapp.riderapp.Model;

public class Services {

    public Services(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    private String name, icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
