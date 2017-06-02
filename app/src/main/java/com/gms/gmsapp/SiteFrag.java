package com.gms.gmsapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.gms.gmsapp.schedulePOJO.EmpList;
import com.gms.gmsapp.schedulePOJO.scheduleBean;
import com.gms.gmsapp.sitesPOJO.sitesBean;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                Schedule a = new Schedule();
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

            bean b = (bean)getContext().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + b.baseURL + ":5000/")
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



    public static class Schedule extends Fragment{

        RecyclerView grid;
        ProgressBar progress;
        Spinner spinner;
        String id;
        TextView org , time;
        List<String> shifts;
        String date = "";
        String shiftId = "";
        FloatingActionButton fab;
        List<EmpList> list;
        GridLayoutManager manager;
        ScheduleAdapter adapter;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.schedule , container , false);

            id = getArguments().getString("id");

            list = new ArrayList<>();

            adapter = new ScheduleAdapter(getContext() , list);
            manager = new GridLayoutManager(getContext() , 1);

            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            shifts = new ArrayList<>();

            grid = (RecyclerView)view.findViewById(R.id.gird);
            progress = (ProgressBar)view.findViewById(R.id.progress);
            spinner = (Spinner)view.findViewById(R.id.spinner);
            org = (TextView)view.findViewById(R.id.org);
            time = (TextView)view.findViewById(R.id.time);
            fab = (FloatingActionButton)view.findViewById(R.id.fab);

            spinner.getBackground().setColorFilter(Color.parseColor("#02b0f0"), PorterDuff.Mode.SRC_ATOP);
            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);


            progress.setVisibility(View.VISIBLE);

            bean b = (bean)getContext().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + b.baseURL + ":5000/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final AllAPIs cr = retrofit.create(AllAPIs.class);



            Call<List<sitesBean>> call = cr.getSites(b.user);

            call.enqueue(new Callback<List<sitesBean>>() {
                @Override
                public void onResponse(Call<List<sitesBean>> call, Response<List<sitesBean>> response) {

                    for (int i = 0 ; i < response.body().size() ; i++)
                    {

                        if (Objects.equals(response.body().get(i).getSiteId(), id))
                        {


                            for (int j = 0 ; j < response.body().get(i).getShiftDetail().size() ; j++)
                            {
                                shifts.add(response.body().get(i).getShiftDetail().get(j).getShiftId());
                            }
                        }

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_model , shifts);

                    spinner.setAdapter(adapter);

                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<List<sitesBean>> call, Throwable throwable) {
                    progress.setVisibility(View.GONE);
                }
            });


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    progress.setVisibility(View.VISIBLE);

                    shiftId = shifts.get(position);

                    Call<scheduleBean> call1 = cr.getSchedule(shifts.get(position) , date);

                    call1.enqueue(new Callback<scheduleBean>() {
                        @Override
                        public void onResponse(Call<scheduleBean> call, Response<scheduleBean> response) {

                            org.setText(response.body().getParentOrg());
                            time.setText(response.body().getShiftTime());

                            adapter.setGridData(response.body().getEmpList());

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<scheduleBean> call, Throwable throwable) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.date_filter);
                    dialog.show();

                    final DatePicker dp = (DatePicker)dialog.findViewById(R.id.date_picker);
                    Button submit = (Button)dialog.findViewById(R.id.submit);

                    dp.setMinDate(System.currentTimeMillis());

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

                            //getLocations(String.valueOf(dp.getYear()) + "-" + m + "-" + d);

                            date = String.valueOf(dp.getYear()) + "-" + m + "-" + d;


                            progress.setVisibility(View.VISIBLE);


                            Call<scheduleBean> call1 = cr.getSchedule(shiftId , date);

                            call1.enqueue(new Callback<scheduleBean>() {
                                @Override
                                public void onResponse(Call<scheduleBean> call, Response<scheduleBean> response) {

                                    org.setText(response.body().getParentOrg());
                                    time.setText(response.body().getShiftTime());

                                    adapter.setGridData(response.body().getEmpList());

                                    progress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<scheduleBean> call, Throwable throwable) {
                                    progress.setVisibility(View.GONE);
                                }
                            });


                            dialog.dismiss();

                        }
                    });

                }
            });

            return view;
        }
    }


}
