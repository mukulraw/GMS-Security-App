package com.gms.gmsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class contactBean {

    @SerializedName("site_code")
    @Expose
    private String siteCode;
    @SerializedName("site_name")
    @Expose
    private String siteName;
    @SerializedName("site_add")
    @Expose
    private String siteAdd;
    @SerializedName("site_person")
    @Expose
    private String sitePerson;
    @SerializedName("site_contact")
    @Expose
    private String siteContact;
    @SerializedName("site_email")
    @Expose
    private String siteEmail;
    @SerializedName("site_person_desig")
    @Expose
    private String sitePersonDesig;

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAdd() {
        return siteAdd;
    }

    public void setSiteAdd(String siteAdd) {
        this.siteAdd = siteAdd;
    }

    public String getSitePerson() {
        return sitePerson;
    }

    public void setSitePerson(String sitePerson) {
        this.sitePerson = sitePerson;
    }

    public String getSiteContact() {
        return siteContact;
    }

    public void setSiteContact(String siteContact) {
        this.siteContact = siteContact;
    }

    public String getSiteEmail() {
        return siteEmail;
    }

    public void setSiteEmail(String siteEmail) {
        this.siteEmail = siteEmail;
    }

    public String getSitePersonDesig() {
        return sitePersonDesig;
    }

    public void setSitePersonDesig(String sitePersonDesig) {
        this.sitePersonDesig = sitePersonDesig;
    }

}
