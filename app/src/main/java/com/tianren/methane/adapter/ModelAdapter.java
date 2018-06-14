package com.tianren.methane.adapter;

import android.content.Context;
import android.graphics.Color;
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
    private ModelListener listener;

    public ModelAdapter(Context context, ModelListener listener) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_model, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ModelBean bean = getItems().get(position);
        if (bean == null) {
            holder.name.setText("");
            holder.content.setText("");
            holder.range.setText("");
        } else {
            holder.name.setText(bean.getNickName());
            String sensorUnit = bean.getSensorUnit();
            if (sensorUnit != null) {
                if (sensorUnit.contains("m3")) {
                    sensorUnit = sensorUnit.replace("m3", "m³");
                }
                if (sensorUnit.contains("m3")) {
                    sensorUnit = sensorUnit.replace("m2", "m²");
                }
            }
            try {
                Double min = bean.getSuitableMinimum();
                Double max = bean.getSuitableMaximum();
                holder.range.setText(min + "～" + max + " " + sensorUnit);
                double data = Double.parseDouble(bean.getData());
                if (data > min && data <= max) {
                    holder.content.setTextColor(context.getResources().getColor(R.color.green));
                } else {
                    holder.content.setTextColor(Color.YELLOW);
                }
                holder.content.setText((TextUtils.isEmpty(bean.getData()) ? "" : bean.getData()) + " " + (TextUtils.isEmpty(sensorUnit) ? "" : sensorUnit));
            } catch (Exception e) {
                holder.content.setText("空数据");
            }
            holder.trend_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(bean);
                }
            });
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, content, range, trend_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);
            range = (TextView) itemView.findViewById(R.id.range);
            trend_tv = (TextView) itemView.findViewById(R.id.trend_tv);
        }
    }

    public static class ModelBean {
        private String sensorName;
        private String nickName;
        private String data;
        private Double suitableMaximum;
        private Double suitableMinimum;
        private String sensorUnit;

        public ModelBean(String sensorName, String nickName, String data, Double suitableMaximum, Double suitableMinimum, String sensorUnit) {
            this.sensorName = sensorName;
            this.nickName = nickName;
            this.data = data;
            this.suitableMaximum = suitableMaximum;
            this.suitableMinimum = suitableMinimum;
            this.sensorUnit = sensorUnit;
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

        public String getSensorUnit() {
            return sensorUnit;
        }

        public void setSensorUnit(String sensorUnit) {
            this.sensorUnit = sensorUnit;
        }
    }

    public interface ModelListener {
        void onClick(ModelBean bean);
    }
}
