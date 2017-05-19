package com.gms.gmsapp;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;


class ChatAdapter extends BaseAdapter {

    Context context;
    List<chatBean> list = new ArrayList<>();
    LayoutInflater inflater;
    private int res;


    public ChatAdapter(Context context, List<chatBean> list, int layoutResId) {
        this.context = context;
        this.list = list;
        this.res = layoutResId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setGridData(List<chatBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View vi = view;
        ViewHolder holder;
        if (view == null) {

            vi = inflater.inflate(res, null);

            holder = new ViewHolder();
            holder.layoutToAdd = (LinearLayout) vi.findViewById(R.id.add_bubble);
            holder.bubble = (TextView) vi.findViewById(R.id.bubble);
            holder.container = (LinearLayout)vi.findViewById(R.id.container);


            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();


        final chatBean item = list.get(i);
        final bean b = (bean) context.getApplicationContext();


        if (Objects.equals("Customer", item.getCommentOwnerRole())) {

            holder.bubble.setText(item.getTicketContent());
            holder.layoutToAdd.setBackground(context.getResources().getDrawable(R.drawable.back_me));
            //holder.sender.setBackground(context.getResources().getDrawable(R.drawable.back_me));
            holder.container.setGravity(Gravity.END);
        } else {

            holder.bubble.setText(item.getTicketContent());
            holder.layoutToAdd.setBackground(context.getResources().getDrawable(R.drawable.back_you));
            //holder.sender.setBackground(context.getResources().getDrawable(R.drawable.back_you));
            holder.container.setGravity(Gravity.START);
        }


        return vi;


    }

    public class ViewHolder {

        LinearLayout layoutToAdd, container;
        TextView bubble;

    }

}
