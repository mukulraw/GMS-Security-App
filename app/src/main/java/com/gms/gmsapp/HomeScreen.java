package com.gms.gmsapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeScreen extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawer;
    LinearLayout linear;
    TextView home , profile , sites , tna , incidents , support , info , settings , logout , title;

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        linear = (LinearLayout)findViewById(R.id.linear);

        progress = (ProgressBar)findViewById(R.id.progress);

        home = (TextView)findViewById(R.id.home);
        profile = (TextView)findViewById(R.id.profile);
        sites = (TextView)findViewById(R.id.sites);

        tna = (TextView)findViewById(R.id.tna);
        incidents = (TextView)findViewById(R.id.incidents);
        support = (TextView)findViewById(R.id.support);
        info = (TextView)findViewById(R.id.info);
        settings = (TextView)findViewById(R.id.settings);
        logout = (TextView)findViewById(R.id.log);
        title = (TextView)findViewById(R.id.title);





        linear.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        bean b = (bean)getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + b.baseURL + ":5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AllAPIs cr = retrofit.create(AllAPIs.class);

        Call<menuBean> call = cr.getMenu(b.user);


        call.enqueue(new Callback<menuBean>() {
            @Override
            public void onResponse(Call<menuBean> call, Response<menuBean> response) {


                if (Objects.equals(response.body().getDashboard(), "on"))
                {
                    home.setVisibility(View.VISIBLE);
                }
                else
                {
                    home.setVisibility(View.GONE);
                }

                if (Objects.equals(response.body().getContract(), "on"))
                {
                    sites.setVisibility(View.VISIBLE);
                }
                else
                {
                    sites.setVisibility(View.GONE);
                }

                if (Objects.equals(response.body().getTna(), "on"))
                {
                    tna.setVisibility(View.VISIBLE);
                }
                else
                {
                    tna.setVisibility(View.GONE);
                }


                if (Objects.equals(response.body().getIncident(), "on"))
                {
                    incidents.setVisibility(View.VISIBLE);
                }
                else
                {
                    incidents.setVisibility(View.GONE);
                }


                if (Objects.equals(response.body().getSupport(), "on"))
                {
                     support.setVisibility(View.VISIBLE);
                }
                else
                {
                    support.setVisibility(View.GONE);
                }


                linear.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<menuBean> call, Throwable t) {

            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                MapScreen frag1 = new MapScreen();

                ft.replace(R.id.layout_to_hide , frag1);
                ft.commit();

                title.setText("home");

                drawer.closeDrawer(GravityCompat.START);


            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Profile frag1 = new Profile();

                ft.replace(R.id.layout_to_hide , frag1);
                ft.commit();

                title.setText("my profile");

                drawer.closeDrawer(GravityCompat.START);


            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Support frag1 = new Support();

                ft.replace(R.id.layout_to_hide , frag1);
                ft.commit();

                drawer.closeDrawer(GravityCompat.START);

            }
        });


        sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Sites frag1 = new Sites();

                ft.replace(R.id.layout_to_hide , frag1);
                ft.commit();
                title.setText("my sites");


                drawer.closeDrawer(GravityCompat.START);


            }
        });

        tna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Schedule frag1 = new Schedule();

                ft.replace(R.id.layout_to_hide , frag1);
                ft.commit();

                drawer.closeDrawer(GravityCompat.START);


            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Info frag1 = new Info();

                ft.replace(R.id.layout_to_hide , frag1);
                ft.commit();

                title.setText("about");

                drawer.closeDrawer(GravityCompat.START);


            }
        });


        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //toolbar.setLogo(R.drawable.guardit);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        MapScreen frag = new MapScreen();

        transaction.replace(R.id.layout_to_hide , frag);
        //transaction.addToBackStack(null);
        transaction.commit();



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
