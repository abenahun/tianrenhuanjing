package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.fragment.HomeFragment;
import com.tianren.methane.fragment.ManagerFragment;
import com.tianren.methane.fragment.MeFragment;
import com.tianren.methane.fragment.NewsFragment;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.home,R.string.guanli,R.string.zixun,R.string.usercenter};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]
            {R.drawable.tab_home_select_drawable,
                    R.drawable.tab_zixun_select_drawable,
                    R.drawable.tab_zixun_select_drawable,
                    R.drawable.tab_wode_select_drawable};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]
            {new HomeFragment(), new ManagerFragment(), new NewsFragment(), new MeFragment()};
    //Tab 数目
    private MyViewPagerAdapter mAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mTabLayout = (TabLayout)findViewById(R.id.tablayout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES,TAB_IMGS);

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs){
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.maintab_custom,null);
            tab.setCustomView(view);

            TextView tvTitle = (TextView)view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String str = data.getExtras().getString(CaptureActivity.EXTRA_STRING);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
