package com.tianren.methane.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tianren.methane.R;
import com.tianren.methane.activity.DataEntryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private View view;

    private ViewFlipper flipper;
    private List testList;
    private int count;

    private TextView tv_luru;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_new, null);
//        initFlipper();
//        initView();
        return view;
    }

    private void initView() {
        tv_luru = (TextView) view.findViewById(R.id.tv_luru);
        tv_luru.setOnClickListener(this);
    }

    private void initFlipper() {
        flipper = (ViewFlipper) view.findViewById(R.id.flipper);
        //初始化list数据
        testList = new ArrayList();
        testList.add(0, "气柜压力超标");
        testList.add(1, "未持卡人员闯入报警区域内");
        testList.add(2, "预处理搅拌机运行异常");
        testList.add(3, "厌氧罐出现异常运转");
        testList.add(4, "脱碳出现异常");
        count = testList.size();
        for (int i = 0; i < count; i++) {
            final View ll_content = View.inflate(getActivity(), R.layout.item_flipper, null);
            TextView tv_content = (TextView) ll_content.findViewById(R.id.tv_content);
            tv_content.setText(testList.get(i).toString());
            flipper.addView(ll_content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_luru:
                Intent intent = new Intent(getActivity(), DataEntryActivity.class);
                startActivity(intent);
                break;

        }
    }
}
