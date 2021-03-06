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
 * A simple {@link Fragment} subclass.
 *
 * @author qiushengtao
 */
public class QiGuiFragment1 extends BaseFragment implements View.OnClickListener {

    private TextView tv_qiguishaixuan;
    private Button btn_data;
    private String[] items = {"pro00000001.1", "pro00000002.1", "pro00000003.1", "pro00000004.1"};

    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_qigui1, container, false);
        initView();
        loadData();
        return view;
    }

    private void initView() {

        recyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(getActivity(), listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_qigui, recyclerView, false);
        tv_qiguishaixuan = (TextView) headView.findViewById(R.id.tv_qiguishaixuan);
        btn_data = (Button) headView.findViewById(R.id.btn_data);
        tv_qiguishaixuan.setOnClickListener(this);
        btn_data.setOnClickListener(this);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<ModelAdapter.ModelBean> list = new ArrayList<>();
        adapter.clear();
        adapter.notifyDataSetChanged();
        if (sensorDataMap == null) {
            list = null;
        } else {
            list.add(getModel("d67"));
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getNickName().trim()) && !TextUtils.isEmpty(list.get(i).getData())) {
                    adapter.addItem(list.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public ModelAdapter.ModelBean getModel(String s) {
        if (sensorDataMap == null) {
            return null;
        } else {
            return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s), sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum(), sensorDataMap.get(s).getSensorUnit());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qiguishaixuan:
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("气柜筛选")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_qiguishaixuan.setText(items[which]);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;

            case R.id.btn_data:
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("type", Constant.INTENT_TYPE_YANYANG);
                startActivity(intent);
                break;

            case R.id.moreIv:
                startActivity(new Intent(getActivity(), CallPoliceActivity.class));
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private ModelAdapter.ModelListener listener = new ModelAdapter.ModelListener() {

        @Override
        public void onClick(ModelAdapter.ModelBean bean) {
            Intent intent7 = new Intent(getActivity(), DataStatisticsActivity.class);
            intent7.putExtra("title", bean.getNickName());
            intent7.putExtra("unit", "m³");
            intent7.putExtra("tableName", "Sensor");
            intent7.putExtra("columnName", StringUtil.humpToLine2(bean.getSensorName()));
            startActivity(intent7);
        }
    };
}
