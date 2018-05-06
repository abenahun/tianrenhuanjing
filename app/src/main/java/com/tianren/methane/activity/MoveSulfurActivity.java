package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.adapter.ModelAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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

        initView();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("脱硫");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setImageResource(R.mipmap.call_police);
        moreIv.setOnClickListener(this);
        moreIv.setVisibility(View.VISIBLE);
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(this,listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_sulfur, recyclerView, false);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        if (sensorDataMap != null) {
            List<ModelAdapter.ModelBean> list = new ArrayList<>();
            list.add(getModel("d1"));
            list.add(getModel("d2"));
            list.add(getModel("d3"));
            list.add(getModel("d4"));
            list.add(getModel("d5"));
            list.add(getModel("d6"));
            list.add(getModel("d7"));
            list.add(getModel("d8"));
            list.add(getModel("d9"));
            adapter.addItems(list);
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

    private ModelAdapter.ModelListener listener = new ModelAdapter.ModelListener() {

        @Override
        public void onClick(ModelAdapter.ModelBean bean) {
            Intent intent7 = new Intent(MoveSulfurActivity.this, DataStatisticsActivity.class);
            intent7.putExtra("title", "脱硫走势");
            intent7.putExtra("statisticsName", "脱硫走势");
            startActivity(intent7);
        }
    };
}
