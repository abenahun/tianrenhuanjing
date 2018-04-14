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
 * Created by Mr.Qiu on 2018\4\14 0014.
 * 厌氧气柜脱硫脱碳的适配器
 */
public class ModelAdapter extends BaseRcAdapter<ModelAdapter.ModelBean, ModelAdapter.MyViewHolder> {
    private LayoutInflater inflater;

    public ModelAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_model, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelBean bean = getItems().get(position);
        holder.name.setText(bean.getNickName());
        holder.content.setText(TextUtils.isEmpty(bean.getData()) ? "" : bean.getData());
        holder.range.setText(bean.getSuitableMinimum() + "～" + bean.getSuitableMaximum());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, content, range;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);
            range = (TextView) itemView.findViewById(R.id.range);
        }
    }

    public static class ModelBean {
        private String sensorName;
        private String nickName;
        private String data;
        private Double suitableMaximum;

        private Double suitableMinimum;

        public ModelBean(String sensorName, String nickName, String data, Double suitableMaximum, Double suitableMinimum) {
            this.sensorName = sensorName;
            this.nickName = nickName;
            this.data = data;
            this.suitableMaximum = suitableMaximum;
            this.suitableMinimum = suitableMinimum;
        }

        public Double getSuitableMaximum() {
            return suitableMaximum;
        }

        public void setSuitableMaximum(Double suitableMaximum) {
            this.suitableMaximum = suitableMaximum;
        }

        public Double getSuitableMinimum() {
            return suitableMinimum;
        }

        public void setSuitableMinimum(Double suitableMinimum) {
            this.suitableMinimum = suitableMinimum;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }


        public String getSensorName() {
            return sensorName;
        }

        public void setSensorName(String sensorName) {
            this.sensorName = sensorName;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
