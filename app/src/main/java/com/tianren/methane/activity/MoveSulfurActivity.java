package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.adapter.ModelAdapter;
import com.tianren.methane.event.ModelEvent;
import com.tianren.methane.utils.StringUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * Created by Qiu on 2018/4/10.
 * 脱硫
 */
public class MoveSulfurActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;
    private ImageView moreIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_sulfur);
        EventBus.getDefault().register(this);
        initView();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("脱硫单元");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setImageResource(R.mipmap.call_police);
        moreIv.setOnClickListener(this);
        moreIv.setVisibility(View.VISIBLE);
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(this, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_sulfur, recyclerView, false);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        if (sensorDataMap != null) {
            List<ModelAdapter.ModelBean> list = new ArrayList<>();
            list.add(getModel("d9"));
            list.add(getModel("d10"));
            list.add(getModel("d11"));
            list.add(getModel("d12"));
            list.add(getModel("d13"));
            list.add(getModel("d14"));
            list.add(getModel("d15"));
            list.add(getModel("d16"));
            list.add(getModel("d17"));
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getNickName().trim()) && !TextUtils.isEmpty(list.get(i).getData())) {
                    adapter.addItem(list.get(i));
                }
            }
        }
    }

    public ModelAdapter.ModelBean getModel(String s) {
        if (sensorDataMap == null) {
            return null;
        } else {
            return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s), sensorDataMap.get(s).getSuitableMaximum(),
                    sensorDataMap.get(s).getSuitableMinimum(), sensorDataMap.get(s).getSensorUnit());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.moreIv:
                startActivity(new Intent(this, CallPoliceActivity.class));
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ModelEvent event) {
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private ModelAdapter.ModelListener listener = new ModelAdapter.ModelListener() {

        @Override
        public void onClick(ModelAdapter.ModelBean bean) {
            Intent intent7 = new Intent(MoveSulfurActivity.this, DataStatisticsActivity.class);
            intent7.putExtra("title", bean.getNickName());
            intent7.putExtra("unit", "吨");
            intent7.putExtra("tableName", "Sensor");
            intent7.putExtra("columnName", StringUtil.humpToLine2(bean.getSensorName()));
            startActivity(intent7);
        }
    };
}
