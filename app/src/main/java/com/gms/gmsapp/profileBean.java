package com.gms.gmsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class profileBean {

    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("cont_person")
    @Expose
    private String contPerson;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cust_email")
    @Expose
    private String custEmail;
    @SerializedName("site_count")
    @Expose
    private String siteCount;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getContPerson() {
        return contPerson;
    }

    public void setContPerson(String contPerson) {
        this.contPerson = contPerson;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(String siteCount) {
        this.siteCount = siteCount;
    }

}
