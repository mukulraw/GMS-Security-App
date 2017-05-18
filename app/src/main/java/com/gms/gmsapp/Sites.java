package com.gms.gmsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Sites extends Fragment {

    Spinner spinner;
    TabLayout tabs;
    ViewPager pager;
    String id;

    List<String> sites;
    List<String> sitesId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sites_layout , container , false);

        sites = new ArrayList<>();
        sitesId = new ArrayList<>();

        spinner = (Spinner)view.findViewById(R.id.spinner);
        tabs = (TabLayout)view.findViewById(R.id.tabs);
        pager = (ViewPager)view.findViewById(R.id.pager);

        tabs.addTab(tabs.newTab().setText("Geofence").setIcon(R.drawable.geo));
        tabs.addTab(tabs.newTab().setText("Address").setIcon(R.drawable.home));
        tabs.addTab(tabs.newTab().setText("Schedule").setIcon(R.drawable.events));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        bean b = (bean)getContext().getApplicationContext();

        Call<List<sitesBean>> call = cr.getSites(b.user);

        PagerAdapter adapter1 = new PagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter1);

        call.enqueue(new Callback<List<sitesBean>>() {
            @Override
            public void onResponse(Call<List<sitesBean>> call, Response<List<sitesBean>> response) {

                for (int i = 0 ; i < response.body().size() ; i++)
                {
                    sites.add(response.body().get(i).getSiteName());
                    sitesId.add(response.body().get(i).getSiteId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,sites);

                spinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<sitesBean>> call, Throwable throwable) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter
    {


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
            {
                return new Geo();
            }
            else if (position == 1)
            {
                return new Address();
            }
            else if (position == 2)
            {
                return new Address();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    public static class Address extends Fragment
    {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.address , container , false);

            return view;
        }
    }

}