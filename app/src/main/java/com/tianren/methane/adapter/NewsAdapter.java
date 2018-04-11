package com.tianren.methane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianren.methane.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //定义一个集合，接收从Activity中传递过来的数据和上下文
    private List<String> mList;
    private Context mContext;

    public NewsAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder){
            final String itemText = mList.get(position);
            ((MyHolder)holder).tv_title.setText(itemText);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_content;
        ImageView iv_img;
        public MyHolder(View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            tv_content = (TextView)itemView.findViewById(R.id.tv_content);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
