package com.gms.gmsapp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class locationBean {

    @SerializedName("site_name")
    @Expose
    private String siteName;
    @SerializedName("latt")
    @Expose
    private String latt;
    @SerializedName("longg")
    @Expose
    private String longg;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("emp_id")
    @Expose
    private List<String> empId = null;
    @SerializedName("emp_name")
    @Expose
    private List<String> empName = null;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLatt() {
        return latt;
    }

    public void setLatt(String latt) {
        this.latt = latt;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<String> getEmpId() {
        return empId;
    }

    public void setEmpId(List<String> empId) {
        this.empId = empId;
    }

    public List<String> getEmpName() {
        return empName;
    }

    public void setEmpName(List<String> empName) {
        this.empName = empName;
    }

}
