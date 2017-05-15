package com.gms.gmsapp;


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


}
