package com.gms.gmsapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.gms.gmsapp.sitesPOJO.sitesBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Sites extends Fragment {

    AppCompatSpinner spinner;
    String id;

    List<String> sites;
    List<String> sitesId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sites_layout , container , false);

        sites = new ArrayList<>();
        sitesId = new ArrayList<>();

        spinner = (AppCompatSpinner)view.findViewById(R.id.spinner);

        spinner.getBackground().setColorFilter(Color.parseColor("#02b0f0"), PorterDuff.Mode.SRC_ATOP);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        bean b = (bean)getContext().getApplicationContext();

        Call<List<sitesBean>> call = cr.getSites(b.user);

        call.enqueue(new Callback<List<sitesBean>>() {
            @Override
            public void onResponse(Call<List<sitesBean>> call, Response<List<sitesBean>> response) {

                for (int i = 0 ; i < response.body().size() ; i++)
                {
                    sites.add(response.body().get(i).getSiteName());
                    sitesId.add(response.body().get(i).getSiteId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_model , sites);

                spinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<sitesBean>> call, Throwable throwable) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();

                SiteFrag frag = new SiteFrag();

                Bundle b = new Bundle();

                b.putString("id" , sitesId.get(position));

                frag.setArguments(b);

                ft.replace(R.id.layout_to_replace , frag);
                ft.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        return view;
    }



}