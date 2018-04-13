package com.tianren.methane.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView tv_yanyang, tv_jiankong, tv_dingwei, tv_qigui, tv_tuoliu, tv_tuotan,tv_entry;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manager, null);
        initView();
        return view;
    }

    private void initView() {

        tv_yanyang = (TextView) view.findViewById(R.id.tv_yanyang);
        tv_yanyang.setOnClickListener(this);

        tv_jiankong = (TextView) view.findViewById(R.id.tv_jiankong);
        tv_jiankong.setOnClickListener(this);

        tv_dingwei = (TextView) view.findViewById(R.id.tv_dingwei);
        tv_dingwei.setOnClickListener(this);

        tv_qigui = (TextView) view.findViewById(R.id.tv_qigui);
        tv_qigui.setOnClickListener(this);

        tv_tuoliu = (TextView) view.findViewById(R.id.tv_tuoliu);
        tv_tuoliu.setOnClickListener(this);

        tv_tuotan = (TextView) view.findViewById(R.id.tv_tuotan);
        tv_tuotan.setOnClickListener(this);

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
            case R.id.tv_qigui:
                Intent intent_qigui = new Intent(getActivity(), QiGuiActivity.class);
                startActivity(intent_qigui);
                break;

            case R.id.tv_yanyang:
                Intent intent_yanyang = new Intent(getActivity(), YanYangActivity.class);
                startActivity(intent_yanyang);
                break;

            case R.id.tv_jiankong:
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent2, 1);
                break;

            case R.id.tv_dingwei:

                break;

            case R.id.tv_tuoliu:
                Intent intent_tuoliu = new Intent(getActivity(), MoveSulfurActivity.class);
                startActivity(intent_tuoliu);
                break;

            case R.id.tv_tuotan:
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
