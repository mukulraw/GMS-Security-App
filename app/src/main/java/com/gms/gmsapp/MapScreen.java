package com.gms.gmsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Hashtable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MapScreen extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton filter;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView grid;
    TextView site , code;
    Hashtable<String , locationBean> markers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_screen , container , false);

        filter = (FloatingActionButton)view.findViewById(R.id.filter);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers = new Hashtable<>();

        View bottom = (View)view.findViewById(R.id.bottom_sheet);

        site = (TextView)bottom.findViewById(R.id.site);
        code = (TextView)bottom.findViewById(R.id.code);
        grid = (RecyclerView)bottom.findViewById(R.id.grid);


        bottomSheetBehavior = BottomSheetBehavior.from(bottom);

        getLocations("2017-05-18");

                return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/



        //mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());

        /*mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                site.setText(marker.getTitle());

                EmployeeAdapter adapter = new EmployeeAdapter(getContext() , markers.get(marker.getId()).getEmpName());

                grid.setAdapter(adapter);

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });*/


    }

    private void getLocations(String date)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        bean b = (bean)getContext().getApplicationContext();

        //Call<List<locationBean>> call = cr.getLocations(b.user , date);
        Call<List<locationBean>> call = cr.getLocations("c004" , date);

        call.enqueue(new Callback<List<locationBean>>() {
            @Override
            public void onResponse(Call<List<locationBean>> call, Response<List<locationBean>> response) {


                for (int i = 0 ; i < response.body().size() ; i++)
                {

                    //LatLng mark = new LatLng(Double.parseDouble(response.body().get(i).getLatt()) , Double.parseDouble(response.body().get(i).getLatt()));
                    LatLng mark = new LatLng(-34 , 151);

                    final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(response.body().get(i).getSiteName()).icon(BitmapDescriptorFactory.defaultMarker()));

                    markers.put(mar.getId() , response.body().get(i));

                }


            }

            @Override
            public void onFailure(Call<List<locationBean>> call, Throwable throwable) {

            }
        });

    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {



        TextView name;
        ImageButton close;


        @Override
        public View getInfoContents(final Marker marker) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_info, null);

            name = ((TextView)view.findViewById(R.id.name));
            close = (ImageButton)view.findViewById(R.id.close);


            name.setText(marker.getTitle());

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    marker.hideInfoWindow();

                }
            });

            return view;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }


    }

}
