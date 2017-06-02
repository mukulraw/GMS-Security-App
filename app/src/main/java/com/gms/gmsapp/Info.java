package com.gms.gmsapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Info extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_layout , container , false);


        TextView txtbody = (TextView) view.findViewById(R.id.txtbody);

        String first = " Guard";
        String last = " powered by World Security is a simple " +
                "and easy to use application to manage security\n" +
                "operations. It allows to stay connected with\n" +
                "your guards all the time, track location and\n" +
                "capture incidents in real time. It’s interactive\n" +
                "system is a user friendly platform that\n" +
                "connects field staff to management and\n" +
                "customers via multiple tools, smart reports and dashboards." + "<br/>" + "<br/>" + "Start to ";
        String third = " today! \n The solution is powered by World Security.\n The entire work is the copyright of World Security.\n Copying or reproducing this solution in any form without prior consent of the company is strictly prohibited" + "<br/>" + "<br/>" + "For more information on this product";

        String next = "<font color='#019EEd'>IT</font>";

        txtbody.setText(Html.fromHtml(first + next + last + first + next + third));

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        textView1.setClickable(true);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());


        return view;
    }
}
