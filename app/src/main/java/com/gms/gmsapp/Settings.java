package com.gms.gmsapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Settings extends Fragment {


    EditText enterIP;
    Button submit;
    TextView change;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings , container , false);

        enterIP = (EditText)view.findViewById(R.id.et_ip);
        submit = (Button)view.findViewById(R.id.submit);
        change = (TextView)view.findViewById(R.id.psswd);

        final bean b = (bean)getContext().getApplicationContext();

        enterIP.setText(b.baseURL);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (enterIP.getText().toString().trim().length()>0)
                {
                    b.baseURL = enterIP.getText().toString();
                }
                else
                {
                    Toast.makeText(getContext() , "Invalid IP" , Toast.LENGTH_SHORT).show();
                }

            }
        });



        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.password_dialog);
                dialog.show();

                final EditText old = (EditText)dialog.findViewById(R.id.old);
                final EditText newP = (EditText)dialog.findViewById(R.id.newP);
                final EditText confirm = (EditText)dialog.findViewById(R.id.confirm);
                Button sub = (Button)dialog.findViewById(R.id.submit);

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://" + b.baseURL + ":5000/")
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllAPIs cr = retrofit.create(AllAPIs.class);



                        Call<Integer> call = cr.changePassword(b.user , old.getText().toString() , newP.getText().toString() , confirm.getText().toString());

                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {

                                if (response.body().equals(0))
                                {
                                    dialog.dismiss();
                                    Toast.makeText(getContext() , "Password changed successfully" , Toast.LENGTH_SHORT);
                                }
                                else if (response.body().equals(1))
                                {
                                    Toast.makeText(getContext() , "Invalid details" , Toast.LENGTH_SHORT);
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable throwable) {

                            }
                        });

                    }
                });

            }
        });


        return view;
    }
}
