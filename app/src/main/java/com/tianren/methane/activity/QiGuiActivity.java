package com.tianren.methane.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.tianren.methane.R;
import com.tianren.methane.adapter.ModelAdapter;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.event.ModelEvent;
import com.tianren.methane.fragment.QiGuiFragment;
import com.tianren.methane.fragment.QiGuiFragment1;
import com.tianren.methane.fragment.QiGuiFragment2;
import com.tianren.methane.fragment.QiGuiFragment3;
import com.tianren.methane.fragment.QiGuiFragment4;
import com.tianren.methane.fragment.YanYangFragment1;
import com.tianren.methane.fragment.YanYangFragment2;
import com.tianren.methane.fragment.YanYangFragment3;
import com.tianren.methane.fragment.YanYangFragment4;
import com.tianren.methane.fragment.YanYangFragment5;
import com.tianren.methane.fragment.YanYangFragment6;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;
import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * Created by Administrator on 2018/3/20.
 */

public class QiGuiActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private ImageView moreIv;

    private TabLayout mTabLayout;
    private final String[] TAB_TITLES = new String[]{"数据统计", "效益报告", "智能抗灾", "智能安防"};
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]
            {new QiGuiFragment1(), new QiGuiFragment2(), new QiGuiFragment3(), new QiGuiFragment4()};
    private QiGuiActivity.MyViewPagerAdapter mAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanyang);
        initView();
    }


    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("气柜");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setImageResource(R.mipmap.call_police);
        moreIv.setOnClickListener(this);
        moreIv.setVisibility(View.VISIBLE);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        for (int i = 0; i < TAB_TITLES.length; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(TAB_TITLES[i]);
            mTabLayout.addTab(tab);
        }
        mTabLayout.setTabMode(MODE_SCROLLABLE);
        mAdapter = new QiGuiActivity.MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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

    /**
     * @description: ViewPager 适配器
     */
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TAB_FRAGMENTS[position];
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }
    }

}
