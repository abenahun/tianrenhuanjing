package com.tianren.methane.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.activity.CallPoliceActivity;
import com.tianren.methane.activity.DataStatisticsActivity;
import com.tianren.methane.activity.ListActivity;
import com.tianren.methane.adapter.ModelAdapter;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.event.ModelEvent;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author qiushengtao
 */
public class QiGuiFragment2 extends BaseFragment implements View.OnClickListener {

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_qigui2, container, false);
        initView();
        loadData();
        return view;
    }

    private void initView() {

    }

    private void loadData() {}


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ModelEvent event) {
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
