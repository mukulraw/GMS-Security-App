package com.gms.gmsapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class Schedule extends Fragment{

    Spinner spinner1 , spinner2;
    TextView org , time;
    RecyclerView grid;
    GridLayoutManager manager;
    ScheduleAdapter adapter;
    List<String> shifts;
    List<EmpList> list;
    String id;
    String date = "";
    FloatingActionButton fab;
    String shiftId = "";
    ProgressBar progress;
    List<String> sites;
    List<String> sitesId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tna , container , false);

        list = new ArrayList<>();
        shifts = new ArrayList<>();
        sites = new ArrayList<>();
        sitesId = new ArrayList<>();

        spinner1 = (Spinner)view.findViewById(R.id.spinner1);
        spinner2 = (Spinner)view.findViewById(R.id.spinner2);
        org = (TextView)view.findViewById(R.id.org);
        time = (TextView)view.findViewById(R.id.time);
        grid = (RecyclerView)view.findViewById(R.id.grid);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        progress = (ProgressBar)view.findViewById(R.id.progress);

        manager = new GridLayoutManager(getContext() , 1);
        adapter = new ScheduleAdapter(getContext() , list);

        progress.setVisibility(View.VISIBLE);

        spinner1.getBackground().setColorFilter(Color.parseColor("#02b0f0"), PorterDuff.Mode.SRC_ATOP);
        spinner2.getBackground().setColorFilter(Color.parseColor("#02b0f0"), PorterDuff.Mode.SRC_ATOP);


        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllAPIs cr = retrofit.create(AllAPIs.class);

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

                spinner1.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<sitesBean>> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                //b.putString("id" , sitesId.get(position));
                progress.setVisibility(View.VISIBLE);

                shifts.clear();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://115.118.242.137:5000/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final AllAPIs cr = retrofit.create(AllAPIs.class);

                bean b = (bean)getContext().getApplicationContext();

                Call<List<sitesBean>> call = cr.getSites(b.user);

                call.enqueue(new Callback<List<sitesBean>>() {
                    @Override
                    public void onResponse(Call<List<sitesBean>> call, Response<List<sitesBean>> response) {

                        for (int i = 0 ; i < response.body().size() ; i++)
                        {

                            if (Objects.equals(response.body().get(i).getSiteId(), sitesId.get(position)))
                            {

                                for (int j = 0 ; j < response.body().get(i).getShiftDetail().size() ; j++)
                                {
                                    shifts.add(response.body().get(i).getShiftDetail().get(j).getShiftId());
                                }
                            }

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_model , shifts);

                        spinner2.setAdapter(adapter);
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<sitesBean>> call, Throwable throwable) {
                        progress.setVisibility(View.GONE);
                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
