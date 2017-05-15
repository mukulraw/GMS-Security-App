package com.gms.gmsapp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class menuBean {

    @SerializedName("dashboard")
    @Expose
    private String dashboard;
    @SerializedName("contract")
    @Expose
    private String contract;
    @SerializedName("tna")
    @Expose
    private String tna;
    @SerializedName("incident")
    @Expose
    private String incident;
    @SerializedName("support")
    @Expose
    private String support;

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getTna() {
        return tna;
    }

    public void setTna(String tna) {
        this.tna = tna;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

}
