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
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class SiteFrag extends Fragment {

    TabLayout tabs;
    ViewPager pager;
    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.site_frag , container , false);

        tabs = (TabLayout)view.findViewById(R.id.tabs);
        pager = (ViewPager)view.findViewById(R.id.pager);

        id = getArguments().getString("id");

        tabs.addTab(tabs.newTab().setText("Geofence").setIcon(R.drawable.geo));
        tabs.addTab(tabs.newTab().setText("Address").setIcon(R.drawable.home2));
        tabs.addTab(tabs.newTab().setText("Schedule").setIcon(R.drawable.events));


        PagerAdapter adapter1 = new PagerAdapter(getChildFragmentManager());

        pager.setAdapter(adapter1);

        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("Geofence").setIcon(R.drawable.geo);
        tabs.getTabAt(1).setText("Address").setIcon(R.drawable.home2);
        tabs.getTabAt(2).setText("Schedule").setIcon(R.drawable.events);



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
                Geo g = new Geo();
                Bundle b = new Bundle();

                b.putString("id" , id);

                g.setArguments(b);
                return g;
            }
            else if (position == 1)
            {
                Address a = new Address();
                Bundle b = new Bundle();

                b.putString("id" , id);
                a.setArguments(b);

                return a;
            }
            else if (position == 2)
            {
                Address a = new Address();
                Bundle b = new Bundle();

                b.putString("id" , id);
                a.setArguments(b);

                return a;
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

        String id;
        TextView code , name , add , person , contact , email , desig;
        ProgressBar progress;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.address , container , false);

            id = getArguments().getString("id");

            progress = (ProgressBar)view.findViewById(R.id.progress);
            code = (TextView)view.findViewById(R.id.site_code);
            name = (TextView)view.findViewById(R.id.site_name);
            add = (TextView)view.findViewById(R.id.site_add);
            person = (TextView)view.findViewById(R.id.site_person);
            contact = (TextView)view.findViewById(R.id.site_contact);
            email = (TextView)view.findViewById(R.id.site_email);
            desig = (TextView)view.findViewById(R.id.site_desig);


            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://115.118.242.137:5000/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllAPIs cr = retrofit.create(AllAPIs.class);

            Call<contactBean> call = cr.getContact(id);

            call.enqueue(new Callback<contactBean>() {
                @Override
                public void onResponse(Call<contactBean> call, Response<contactBean> response) {

                    code.setText(response.body().getSiteCode());
                    name.setText(response.body().getSiteName());
                    add.setText(response.body().getSiteAdd());
                    person.setText(response.body().getSitePerson());
                    contact.setText(response.body().getSiteContact());
                    email.setText(response.body().getSiteEmail());
                    desig.setText(response.body().getSitePersonDesig());

                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<contactBean> call, Throwable throwable) {
                    progress.setVisibility(View.GONE);
                }
            });

            return view;
        }
    }

}
