package com.gms.gmsapp;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

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
    ProgressBar progress;
    Hashtable<String , locationBean> markers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_screen , container , false);

        progress = (ProgressBar)view.findViewById(R.id.progress);

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





        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_filter);
                dialog.show();

                final DatePicker dp = (DatePicker)dialog.findViewById(R.id.date_picker);
                Button submit = (Button)dialog.findViewById(R.id.submit);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int da = dp.getDayOfMonth();
                        int month = dp.getMonth() + 1;

                        String d = "", m = "";

                        if (da < 10)
                        {
                            d = "0" + String.valueOf(da);
                        }
                        else
                        {
                            d = String.valueOf(da);
                        }
                        if (month < 10)
                        {
                            m = "0" + String.valueOf(month);
                        }
                        else
                        {
                            m = String.valueOf(month);
                        }

                        getLocations(String.valueOf(dp.getYear()) + "-" + m + "-" + d);

                        dialog.dismiss();

                    }
                });

            }
        });

                return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        getLocations(date);


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

        Log.d("asdasd" , date);

        mMap.clear();

        progress.setVisibility(View.VISIBLE);
        bean b = (bean)getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + b.baseURL + ":5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);



        //Call<List<locationBean>> call = cr.getLocations(b.user , date);
        Call<List<locationBean>> call = cr.getLocations(b.user , date);

        call.enqueue(new Callback<List<locationBean>>() {
            @Override
            public void onResponse(Call<List<locationBean>> call, Response<List<locationBean>> response) {


                for (int i = 0 ; i < response.body().size() ; i++)
                {

                    if (Objects.equals(response.body().get(i).getFlag(), "red"))
                    {
                        try
                        {
                            LatLng mark = new LatLng(Double.parseDouble(response.body().get(i).getLatt()) , Double.parseDouble(response.body().get(i).getLatt()));
                            final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(response.body().get(i).getSiteName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            markers.put(mar.getId() , response.body().get(i));
                        }
                        catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                        }


                    }
                    else if (Objects.equals(response.body().get(i).getFlag(), "green"))
                    {
                        try {

                            LatLng mark = new LatLng(Double.parseDouble(response.body().get(i).getLatt()) , Double.parseDouble(response.body().get(i).getLatt()));
                            final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(response.body().get(i).getSiteName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            markers.put(mar.getId() , response.body().get(i));

                        }catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                        }

                    }
                    else if (Objects.equals(response.body().get(i).getFlag(), "yellow"))
                    {
                        try {

                            LatLng mark = new LatLng(Double.parseDouble(response.body().get(i).getLatt()) , Double.parseDouble(response.body().get(i).getLatt()));
                            final Marker mar = mMap.addMarker(new MarkerOptions().position(mark).title(response.body().get(i).getSiteName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            markers.put(mar.getId() , response.body().get(i));

                        }
                        catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                        }


                    }


                }

                Log.d("asdasdasd" , "success");

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<locationBean>> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
                Log.d("asdasdasd" , "failure");
            }
        });

    }



}
