package com.gms.gmsapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TicketChat extends AppCompatActivity {

    Toolbar toolbar;
    ListView grid;
    FloatingActionButton fab;
    EditText edit;
    ProgressBar progress;
    String tId;
    String head;
    List<chatBean> list;
    ChatAdapter adapter;
    int count = 0;
    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_chat);

        list = new ArrayList<>();

        t = new Timer();

        grid = (ListView)findViewById(R.id.list);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        edit = (EditText)findViewById(R.id.type);
        progress = (ProgressBar)findViewById(R.id.progress);

        tId = getIntent().getStringExtra("id");
        head = getIntent().getStringExtra("sub");

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        toolbar.setTitle(head);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adapter = new ChatAdapter(TicketChat.this , list , R.layout.chat_model);

        grid.setAdapter(adapter);
        grid.setDividerHeight(0);

        progress.setVisibility(View.VISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.118.242.137:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllAPIs cr = retrofit.create(AllAPIs.class);

        Call<List<chatBean>> call = cr.getChats(tId);

        call.enqueue(new Callback<List<chatBean>>() {
            @Override
            public void onResponse(Call<List<chatBean>> call, Response<List<chatBean>> response) {


                for (int i = count ; i < response.body().size() ; i++)
                {
                    list.add(response.body().get(i));
                    adapter.setGridData(list);
                }


                count = response.body().size();


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<chatBean>> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
            }
        });


        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://115.118.242.137:5000/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllAPIs cr = retrofit.create(AllAPIs.class);

                Call<List<chatBean>> call = cr.getChats(tId);

                call.enqueue(new Callback<List<chatBean>>() {
                    @Override
                    public void onResponse(Call<List<chatBean>> call, Response<List<chatBean>> response) {


                        for (int i = count ; i < response.body().size() ; i++)
                        {
                            list.add(response.body().get(i));
                            adapter.setGridData(list);
                        }


                        count = response.body().size();



                    }

                    @Override
                    public void onFailure(Call<List<chatBean>> call, Throwable throwable) {

                    }
                });

            }
        } , 0 , 1000);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String conte = edit.getText().toString();

                if (conte.length()>0)
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://115.118.242.137:5000/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllAPIs cr = retrofit.create(AllAPIs.class);

                    bean b = (bean)getApplicationContext();

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    Call<Integer> call = cr.sendMessage(b.user , tId , conte , date);

                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {

                            if (response.body() == 1)
                            {
                                edit.setText("");
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable throwable) {

                        }
                    });
                }




            }
        });



    }
}