package com.tianren.methane.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.base.BaseRcAdapter;

/**
 * @author Mr.Qiu
 * @date 2018/4/20
 */
public class CallPoliceAdapter extends BaseRcAdapter<CallPoliceAdapter.CallPoliceBean, CallPoliceAdapter.MyViewHolder> {
    private LayoutInflater inflater;

    public CallPoliceAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_call_police, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CallPoliceBean bean = getItems().get(position);
        holder.name.setText(TextUtils.isEmpty(bean.name) ? "" : bean.name);
        holder.state.setText(bean.state == 0 ? "未处理" : "已处理");
        holder.level.setText("警告级别:( " + ((bean.level == 1) ? "超低" : (bean.level == 2) ? "低"
                : (bean.level == 3) ? "高" : (bean.level == 4) ? "超高" : "错误") + ")");
        holder.callTime.setText("告警时间: " + (TextUtils.isEmpty(bean.callTime) ? "" : bean.callTime));
        switch (bean.level) {
            case 1:
                holder.levelIv.setImageResource(R.mipmap.level1);
                break;
            case 2:
                holder.levelIv.setImageResource(R.mipmap.level2);
                break;
            case 3:
                holder.levelIv.setImageResource(R.mipmap.level3);
                break;
            case 4:
                holder.levelIv.setImageResource(R.mipmap.level4);
                break;
            default:
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, state, level, callTime;
        private ImageView levelIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            state = (TextView) itemView.findViewById(R.id.state);
            level = (TextView) itemView.findViewById(R.id.level);
            callTime = (TextView) itemView.findViewById(R.id.callTime);
            levelIv = (ImageView) itemView.findViewById(R.id.levelIv);
        }
    }

    public static class CallPoliceBean {
        private String name, callTime;
        private int state, level;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getCallTime() {
            return callTime;
        }

        public void setCallTime(String callTime) {
            this.callTime = callTime;
        }
    }
}
