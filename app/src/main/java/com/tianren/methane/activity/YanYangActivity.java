package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.fragment.YanYangFragment1;
import com.tianren.methane.fragment.YanYangFragment2;
import com.tianren.methane.fragment.YanYangFragment3;
import com.tianren.methane.fragment.YanYangFragment4;
import com.tianren.methane.fragment.YanYangFragment5;
import com.tianren.methane.fragment.YanYangFragment6;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

/**
 * @author Qiu
 * @date 2018/4/09
 */
public class YanYangActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private ImageView moreIv;

    private TabLayout mTabLayout;
    private final String[] TAB_TITLES = new String[]{"工艺流程", "稳定指数", "智能进料", "聚类分析", "智能搅拌", "日达标率"};
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]
            {new YanYangFragment1(), new YanYangFragment2(), new YanYangFragment3(), new YanYangFragment4(), new YanYangFragment5(), new YanYangFragment6()};
    private MyViewPagerAdapter mAdapter;
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
        tv_title.setText("厌氧单元");
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
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
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
