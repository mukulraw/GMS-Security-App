package com.gms.gmsapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gms.gmsapp.getPOJO.geoBean;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Geo extends Fragment implements OnMapReadyCallback {

    String id;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.geo , container , false);
        id = getArguments().getString("id");


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        Call<geoBean> call = cr.getGeo(id);

        call.enqueue(new Callback<geoBean>() {
            @Override
            public void onResponse(Call<geoBean> call, Response<geoBean> response) {

                for (int i = 0 ; i < response.body().getGeoPoints().size() ; i++)
                {

                    LatLng mark = new LatLng(Double.parseDouble(String.valueOf(response.body().getGeoPoints().get(i).getLatitude())) , Double.parseDouble(String.valueOf(response.body().getGeoPoints().get(i).getLongitude())));
                    final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(String.valueOf(response.body().getGeoPoints().get(i).getPointNo())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                }

            }

            @Override
            public void onFailure(Call<geoBean> call, Throwable throwable) {

            }
        });


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

    }
}
