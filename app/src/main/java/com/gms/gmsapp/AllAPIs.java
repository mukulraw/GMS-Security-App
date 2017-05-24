package com.gms.gmsapp;


import com.gms.gmsapp.getPOJO.geoBean;
import com.gms.gmsapp.schedulePOJO.scheduleBean;
import com.gms.gmsapp.sitesPOJO.sitesBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AllAPIs {

    @GET("GuardIT-RWS/rest/myresource/login")
    Call<Integer> login(
            @Query("username") String username,
            @Query("password") String password
    );


    @GET("GuardIT-RWS/rest/myresource/getmenuList")
    Call<menuBean> getMenu(
            @Query("email_id") String email_id
    );

    @GET("GuardIT-RWS/rest/myresource/getdashboard")
    Call<List<locationBean>> getLocations(
            @Query("email_id") String email_id,
            @Query("date") String date
    );

    @GET("GuardIT-RWS/rest/myresource/noOfSites")
    Call<List<sitesBean>> getSites(
            @Query("email_id") String email_id
    );

    @GET("GuardIT-RWS/rest/myresource/getgeoFences")
    Call<geoBean> getGeo(
            @Query("site_id") String siteId
    );

    @GET("GuardIT-RWS/rest/myresource/getsiteAddress")
    Call<contactBean> getContact(
            @Query("site_id") String siteId
    );

    @GET("GuardIT-RWS/rest/myresource/getticketList")
    Call<List<supportListBean>> getSupport(
            @Query("email_id") String email_id
    );

    @GET("GuardIT-RWS/rest/myresource/getticketDetail")
    Call<List<chatBean>> getChats(
            @Query("ticket_id") String ticketId
    );


    @GET("GuardIT-RWS/rest/myresource/replyticket")
    Call<Integer> sendMessage(
            @Query("email_id") String email_id,
            @Query("ticket_id") String tId,
            @Query("content") String content,
            @Query("date") String date
    );

    @GET("GuardIT-RWS/rest/myresource/customerDetails")
    Call<profileBean> getProfile(
            @Query("email_id") String email_id
    );

    @GET("GuardIT-RWS/rest/myresource/viewshiftSchedule")
    Call<scheduleBean> getSchedule(
            @Query("shift_id") String shift_id,
            @Query("date") String date
    );


}
