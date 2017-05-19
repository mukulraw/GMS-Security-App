package com.gms.gmsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder> {

    List<supportListBean> list = new ArrayList<>();
    Context context;


    public SupportAdapter(Context context , List<supportListBean> list)
    {
        this.list = list;
        this.context = context;
    }

    public void setGridData(List<supportListBean> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.support_list_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final supportListBean item = list.get(position);

        holder.text.setText(item.getTicketContent());
        holder.type.setText(item.getTicketType());
        holder.severity.setText(item.getSeverity());
        holder.sub.setText(item.getTicketSubject());
        holder.time.setText(item.getReportOn());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , TicketChat.class);
                intent.putExtra("id" , item.getTicketId());
                intent.putExtra("sub" , item.getTicketSubject());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView type , time , severity , sub , text;

        public ViewHolder(View itemView) {
            super(itemView);

            type = (TextView)itemView.findViewById(R.id.type);
            time = (TextView)itemView.findViewById(R.id.time);
            sub = (TextView)itemView.findViewById(R.id.sub);
            severity = (TextView)itemView.findViewById(R.id.severity);
            text = (TextView)itemView.findViewById(R.id.text);

        }
    }

}
