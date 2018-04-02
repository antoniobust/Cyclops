package com.mdmobile.cyclops.dataModels.api;


public class Support {
    private String email, phone, companyName, address, serviceManaged;

    public Support(String email, String phone, String companyName, String address, String serviceManaged) {
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.address = address;
        this.serviceManaged = serviceManaged;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getServiceManaged() {
        return serviceManaged;
    }
}
