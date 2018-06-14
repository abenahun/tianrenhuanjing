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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.activity.DataStatisticsActivity;
import com.tianren.methane.adapter.ModelAdapter;
import com.tianren.methane.event.ModelEvent;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author qiushengtao
 */
public class YanYangFragment1 extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "YanYangFragment1";
    private TextView tv_qiguishaixuan;
    private Button btn_data;
    private String[] items = {"厌氧罐 No1", "厌氧罐 No2", "厌氧罐 No3"};
    private Spinner spinner;
    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;
    private View view;

    private TextView real_date, lab_date;
    private ArrayAdapter<String> spinnerAdapter;
    private List<Map<String, String>> labList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_yan_yang_fragment1, container, false);
        initView();
        loadRealData();
        return view;
    }

    private void initView() {
        recyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(getActivity(), listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_yanyang, recyclerView, false);
        tv_qiguishaixuan = (TextView) headView.findViewById(R.id.tv_qiguishaixuan);
        btn_data = (Button) headView.findViewById(R.id.btn_data);
        real_date = (TextView) headView.findViewById(R.id.real_date);
        lab_date = (TextView) headView.findViewById(R.id.test_date);
        spinner = (Spinner) headView.findViewById(R.id.spinner);

        tv_qiguishaixuan.setOnClickListener(this);
        btn_data.setOnClickListener(this);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
        real_date.setSelected(true);
        real_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!real_date.isSelected()) {
                    spinner.setVisibility(View.GONE);
                    real_date.setSelected(true);
                    lab_date.setSelected(false);
                    adapter.clear();
                    loadRealData();
                }
            }
        });
        lab_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lab_date.isSelected()) {
                    spinner.setVisibility(View.VISIBLE);
                    real_date.setSelected(false);
                    lab_date.setSelected(true);
                    adapter.clear();
                    loadLabData();
                }
            }
        });


        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.clear();
                Map<String, String> map = labList.get(position);
                for (String i : map.keySet()) {
                    ModelAdapter.ModelBean labModel = getLabModel(i, map.get(i));
                    if (labModel != null) {
                        adapter.addItem(labModel);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadRealData() {
        List<ModelAdapter.ModelBean> list = new ArrayList<>();
        if (sensorDataMap == null) {
            list = null;
        } else {
            list.add(getModel("d9"));
            list.add(getModel("d21"));
            list.add(getModel("d7"));
            list.add(getModel("d23"));
            list.add(getModel("d29"));
            list.add(getModel("d4"));
            list.add(getModel("d3"));
            list.add(getModel("d46"));
            list.add(getModel("d22"));
            list.add(getModel("d13"));
            list.add(getModel("d54"));
            list.add(getModel("d17"));
            list.add(getModel("d26"));
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getNickName().trim()) && !TextUtils.isEmpty(list.get(i).getData())) {
                    adapter.addItem(list.get(i));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void loadLabData() {
        WebServiceManage.getService(EntryService.class).getAnaerobicTankData().setCallback(new SCallBack<BaseResponse<List<Map<String, String>>>>() {
            @Override
            public void callback(boolean isok, String msg, BaseResponse<List<Map<String, String>>> res) {
                if (isok) {
                    spinnerAdapter.clear();
                    labList = res.getData();
                    for (int i = 0; i < labList.size(); i++) {
                        String samplingPoint = labList.get(i).get("samplingPoint");
                        spinnerAdapter.add(samplingPoint);
                    }
                    spinnerAdapter.notifyDataSetChanged();

                    Map<String, String> map = labList.get(0);
                    for (String i : map.keySet()) {
                        ModelAdapter.ModelBean labModel = getLabModel(i, map.get(i));
                        if (labModel != null) {
                            adapter.addItem(labModel);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(msg);
                }
            }
        });
    }

    public ModelAdapter.ModelBean getModel(String s) {
        if (sensorDataMap == null) {
            return null;
        } else {
            return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s),
                    sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum(),
                    sensorDataMap.get(s).getSensorUnit());
        }
    }

    public ModelAdapter.ModelBean getLabModel(String s, String data) {
        if (sensorDataMap == null) {
            return null;
        } else {
            if (sensorDataMap.get(s) == null) {
                return null;
            }
            return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), data,
                    sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum(),
                    sensorDataMap.get(s).getSensorUnit());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qiguishaixuan:
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("厌氧罐筛选")
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
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ModelEvent event) {
        if (real_date.isSelected()) {
            loadRealData();
        }
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
