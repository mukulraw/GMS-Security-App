package com.gms.gmsapp.schedulePOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class scheduleBean {

    @SerializedName("parent_org")
    @Expose
    private String parentOrg;
    @SerializedName("site_name")
    @Expose
    private String siteName;
    @SerializedName("shift_time")
    @Expose
    private String shiftTime;
    @SerializedName("emp_list")
    @Expose
    private List<EmpList> empList = null;

    public String getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(String parentOrg) {
        this.parentOrg = parentOrg;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public List<EmpList> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmpList> empList) {
        this.empList = empList;
    }

}
