package com.tianren.methane.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.activity.DataEntryActivity;
import com.tianren.methane.activity.LoginActivity;
import com.tianren.methane.activity.MoveCarbonActivity;
import com.tianren.methane.activity.MoveSulfurActivity;
import com.tianren.methane.activity.QiGuiActivity;
import com.tianren.methane.activity.YanYangActivity;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ManagerFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private LinearLayout ll_yanyang, ll_yuchuli, ll_qigui, ll_tuoliu, ll_tuotan;
    private TextView tv_entry;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manager, null);
        initView();
        return view;
    }

    private void initView() {

        ll_yanyang = (LinearLayout) view.findViewById(R.id.ll_yanyang);
        ll_yanyang.setOnClickListener(this);

        ll_yuchuli = (LinearLayout) view.findViewById(R.id.ll_yuchuli);
        ll_yuchuli.setOnClickListener(this);

        ll_tuoliu = (LinearLayout) view.findViewById(R.id.ll_tuoliu);
        ll_tuoliu.setOnClickListener(this);

        ll_tuotan = (LinearLayout) view.findViewById(R.id.ll_tuotan);
        ll_tuotan.setOnClickListener(this);

        ll_qigui = (LinearLayout) view.findViewById(R.id.ll_qigui);
        ll_qigui.setOnClickListener(this);

        tv_entry= (TextView) view.findViewById(R.id.tv_entry);
        tv_entry.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String str = data.getExtras().getString(CaptureActivity.EXTRA_STRING);
//        ToastUtils.show(str);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_qigui:
                Intent intent_qigui = new Intent(getActivity(), QiGuiActivity.class);
                startActivity(intent_qigui);
                break;

            case R.id.ll_yanyang:
                Intent intent_yanyang = new Intent(getActivity(), YanYangActivity.class);
                startActivity(intent_yanyang);
                break;

            case R.id.ll_yuchuli:
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent2, 1);
                break;

            case R.id.ll_tuoliu:
                Intent intent_tuoliu = new Intent(getActivity(), MoveSulfurActivity.class);
                startActivity(intent_tuoliu);
                break;

            case R.id.ll_tuotan:
                Intent intent_tuotan = new Intent(getActivity(), MoveCarbonActivity.class);
                startActivity(intent_tuotan);
                break;

            case R.id.tv_entry:
                Intent intent_entry = new Intent(getActivity(), DataEntryActivity.class);
                startActivity(intent_entry);
                break;
        }
    }
}
