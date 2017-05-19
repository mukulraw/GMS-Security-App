package com.gms.gmsapp.getPOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class geoBean {

    @SerializedName("iSiteId")
    @Expose
    private Integer iSiteId;
    @SerializedName("geo_points")
    @Expose
    private List<GeoPoint> geoPoints = null;
    @SerializedName("b_isInside")
    @Expose
    private Boolean bIsInside;

    public Integer getISiteId() {
        return iSiteId;
    }

    public void setISiteId(Integer iSiteId) {
        this.iSiteId = iSiteId;
    }

    public List<GeoPoint> getGeoPoints() {
        return geoPoints;
    }

    public void setGeoPoints(List<GeoPoint> geoPoints) {
        this.geoPoints = geoPoints;
    }

    public Boolean getBIsInside() {
        return bIsInside;
    }

    public void setBIsInside(Boolean bIsInside) {
        this.bIsInside = bIsInside;
    }

}
