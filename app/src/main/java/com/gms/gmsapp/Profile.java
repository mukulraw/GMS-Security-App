package com.gms.gmsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Profile extends Fragment
{

    TextView name , person , contact , email , address , count;
    ProgressBar progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile , container , false);

        name = (TextView)view.findViewById(R.id.name);
        person = (TextView)view.findViewById(R.id.person);
        contact = (TextView)view.findViewById(R.id.contact);
        email = (TextView)view.findViewById(R.id.email);
        address = (TextView)view.findViewById(R.id.address);
        count = (TextView)view.findViewById(R.id.count);

        progress = (ProgressBar)view.findViewById(R.id.progress);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        bean b = (bean)getContext().getApplicationContext();

        Call<profileBean> call = cr.getProfile(b.user);

        call.enqueue(new Callback<profileBean>() {
            @Override
            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                name.setText(response.body().getCustName());
                person.setText(response.body().getContPerson());
                contact.setText(response.body().getContact());
                email.setText(response.body().getCustEmail());
                address.setText(response.body().getAddress());
                count.setText(response.body().getSiteCount());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<profileBean> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
            }
        });

    }
}
