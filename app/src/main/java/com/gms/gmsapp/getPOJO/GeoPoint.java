package com.gms.gmsapp.getPOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoPoint {


    @SerializedName("point_no")
    @Expose
    private Integer pointNo;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public Integer getPointNo() {
        return pointNo;
    }

    public void setPointNo(Integer pointNo) {
        this.pointNo = pointNo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
