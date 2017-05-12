package com.gms.gmsapp;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AllAPIs {

    @GET("GuardIT-RWS/rest/myresource/login?username={username}&password={password}")
    Call<Integer> login(
            @Path("username") String username,
            @Path("password") String password
    );

}
