package com.gms.gmsapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gms.gmsapp.schedulePOJO.EmpList;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{

    private Context context;
    List<EmpList> list = new ArrayList<>();


    public ScheduleAdapter(Context context , List<EmpList> list)
    {
        this.list = list;
        this.context = context;
    }

    public void setGridData(List<EmpList> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.schedule_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmpList item = list.get(position);


        holder.fname.setText(item.getFirst());
        holder.lname.setText(item.getLast());
        holder.role.setText(item.getRole());
        holder.gender.setText(item.getSex());
        holder.code.setText(item.getEmpCode());

try {
    holder.attendance.setText(item.getTodayAttendance().get(0) + "-" + item.getTodayAttendance().get(1));
}catch (IndexOutOfBoundsException e)
{
    e.printStackTrace();
}



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView fname , lname , role , gender , code , attendance;

        public ViewHolder(View itemView) {
            super(itemView);

            fname = (TextView)itemView.findViewById(R.id.fname);
            lname = (TextView)itemView.findViewById(R.id.lname);
            role = (TextView)itemView.findViewById(R.id.role);
            gender = (TextView)itemView.findViewById(R.id.gender);
            code = (TextView)itemView.findViewById(R.id.code);
            attendance = (TextView)itemView.findViewById(R.id.attendance);

        }
    }

}


