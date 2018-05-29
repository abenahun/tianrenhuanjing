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
import com.tianren.methane.event.ModelEvent;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * Created by Mr.Qiu on 2018\4\14 0014.
 */

public class PreHandleActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;
    private ImageView moreIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_handle);
        EventBus.getDefault().register(this);
        initView();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("预处理");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setImageResource(R.mipmap.call_police);
        moreIv.setOnClickListener(this);
        moreIv.setVisibility(View.VISIBLE);
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(this, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_pre_handle, recyclerView, false);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
//        List<ModelAdapter.ModelBean> list = new ArrayList<>();
//        if (sensorDataMap == null) {
//            list = null;
//        } else {
//            list.add(getModel("d21"));
//            list.add(getModel("d22"));
//            list.add(getModel("d23"));
//            list.add(getModel("d24"));
//            list.add(getModel("d25"));
//            list.add(getModel("d26"));
//            list.add(getModel("d27"));
//            list.add(getModel("d28"));
//            for (int i = 0; i < list.size(); i++) {
//                if (!TextUtils.isEmpty(list.get(i).getNickName().trim()) && !TextUtils.isEmpty(list.get(i).getData())) {
//                    adapter.addItem(list.get(i));
//                }
//            }
//        }
    }

    public ModelAdapter.ModelBean getModel(String s) {
        if (sensorDataMap == null) {
            return null;
        } else {
            return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s), sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum()
                    , sensorDataMap.get(s).getSensorUnit());
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
            Intent intent7 = new Intent(PreHandleActivity.this, DataStatisticsActivity.class);
            intent7.putExtra("title", "预处理走势");
            intent7.putExtra("statisticsName", "预处理走势");
            startActivity(intent7);
        }
    };
}