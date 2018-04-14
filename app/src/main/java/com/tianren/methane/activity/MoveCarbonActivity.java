package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.adapter.ModelAdapter;
import com.tianren.methane.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * Created by Qiu on 2018/4/10.
 * 脱碳
 */
public class MoveCarbonActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_carbon);
        if (sensorDataMap.size() == 0) {
            ToastUtils.show("暂无数据");
            finish();
        }
        initView();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("脱碳");
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_carbon, recyclerView, false);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<ModelAdapter.ModelBean> list = new ArrayList<>();
        list.add(getModel("d10"));
        list.add(getModel("d11"));
        list.add(getModel("d12"));
        list.add(getModel("d13"));
        list.add(getModel("d14"));
        list.add(getModel("d15"));
        list.add(getModel("d16"));
        list.add(getModel("d17"));
        list.add(getModel("d18"));
        list.add(getModel("d19"));
        adapter.addItems(list);
    }

    public ModelAdapter.ModelBean getModel(String s) {
        return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s), sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
