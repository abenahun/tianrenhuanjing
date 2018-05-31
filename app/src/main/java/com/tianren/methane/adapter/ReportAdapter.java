package com.tianren.methane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.base.BaseRcAdapter;

/**
 * @author Mr.Qiu
 * @date 2018/5/31
 */
public class ReportAdapter extends BaseRcAdapter<String, ReportAdapter.MyViewHolder> {
    private LayoutInflater inflater;

    public ReportAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String s = getItems().get(position);
        holder.textView.setText(TextUtils.isEmpty(s) ? "" : s);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

    }
}
