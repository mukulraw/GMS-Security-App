package com.gms.gmsapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {

    TextView login;

    EditText username , password;
    TextView forgot;
    //ProgressBar progress;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (TextView)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        //progress = (ProgressBar)findViewById(R.id.progress);
        forgot = (TextView)findViewById(R.id.forgot);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.length() > 0)
                {

                    if (pass.length() > 0)
                    {

                        //progress.setVisibility(View.VISIBLE);
                        dialog.show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://115.118.242.137:5000/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllAPIs cr = retrofit.create(AllAPIs.class);


                        Call<Integer> call = cr.login(user , pass);

                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {

                                bean b = (bean) getApplicationContext();

                                int result = response.body();

                                if (result == 1)
                                {
                                    Toast.makeText(Login.this , "Invalid Password" , Toast.LENGTH_SHORT).show();
                                    //progress.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }

                                else if (result == 2)
                                {
                                    Toast.makeText(Login.this , "User doesn't exist" , Toast.LENGTH_SHORT).show();
                                    //progress.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }

                                else if (result == 3)
                                {
                                    Toast.makeText(Login.this , "Login Success" , Toast.LENGTH_SHORT).show();
                                    b.user = user;

                                    Intent intent = new Intent(Login.this , HomeScreen.class);
                                    startActivity(intent);
                                    //progress.setVisibility(View.GONE);
                                    dialog.dismiss();
                                    finish();

                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Login.this , "Password is required" , Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(Login.this , "Username is required" , Toast.LENGTH_SHORT).show();
                }




            }
        });

    }
}
