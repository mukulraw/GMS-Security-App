package com.gms.gmsapp.sitesPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class sitesBean {

    @SerializedName("site_id")
    @Expose
    private String siteId;
    @SerializedName("site_name")
    @Expose
    private String siteName;
    @SerializedName("shift_detail")
    @Expose
    private List<ShiftDetail> shiftDetail = null;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<ShiftDetail> getShiftDetail() {
        return shiftDetail;
    }

    public void setShiftDetail(List<ShiftDetail> shiftDetail) {
        this.shiftDetail = shiftDetail;
    }

}
