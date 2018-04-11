package com.tianren.methane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.bean.YanYangBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/20.
 */

public class YanYangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //定义一个集合，接收从Activity中传递过来的数据和上下文
    private ArrayList<YanYangBean.YanYangDate> mList;
    private Context mContext;

    public YanYangAdapter(Context context, ArrayList<YanYangBean.YanYangDate> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_yanyang, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder){
            ((MyHolder)holder).tv_ch4.setText(mList.get(position).getCh4());
            ((MyHolder)holder).tv_o2.setText(mList.get(position).getO2());
            ((MyHolder)holder).tv_h2s.setText(mList.get(position).getH2s());
            ((MyHolder)holder).tv_co2.setText(mList.get(position).getCo2());
            ((MyHolder)holder).tv_status.setText(mList.get(position).getStatus());
            ((MyHolder)holder).tv_time.setText(mList.get(position).getTime());
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_ch4,tv_o2,tv_h2s,tv_co2,tv_status,tv_time;
        public MyHolder(View itemView) {
            super(itemView);
            tv_ch4 = (TextView)itemView.findViewById(R.id.tv_ch4);
            tv_o2 = (TextView)itemView.findViewById(R.id.tv_o2);
            tv_h2s = (TextView)itemView.findViewById(R.id.tv_h2s);
            tv_co2 = (TextView)itemView.findViewById(R.id.tv_co2);
            tv_status = (TextView)itemView.findViewById(R.id.tv_status);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);

        }
    }
}
