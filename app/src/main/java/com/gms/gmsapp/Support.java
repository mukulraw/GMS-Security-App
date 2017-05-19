package com.gms.gmsapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Support extends Fragment{

    RecyclerView grid;
    ProgressBar progress;
    SupportAdapter adapter;
    List<supportListBean> list;
    GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support , container , false);


        list = new ArrayList<>();
        manager = new GridLayoutManager(getContext() , 1);
        adapter = new SupportAdapter(getContext() , list);


        grid = (RecyclerView)view.findViewById(R.id.grid);
        progress = (ProgressBar)view.findViewById(R.id.progress);


        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        bean b = (bean)getContext().getApplicationContext();

        Call<List<supportListBean>> call = cr.getSupport(b.user);


        call.enqueue(new Callback<List<supportListBean>>() {
            @Override
            public void onResponse(Call<List<supportListBean>> call, Response<List<supportListBean>> response) {


                adapter.setGridData(response.body());


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<supportListBean>> call, Throwable throwable) {

                progress.setVisibility(View.GONE);

            }
        });

        return view;
    }



}
