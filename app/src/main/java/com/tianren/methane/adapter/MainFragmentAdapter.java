package com.tianren.methane.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tianren.methane.fragment.HomeFragment;

/**
 * Created by Administrator on 2018/3/19.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"首页", "发现", "进货单","我的"};

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new HomeFragment();
        } else if (position == 2) {
            return new HomeFragment();
        }else if (position==3){
            return new HomeFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}