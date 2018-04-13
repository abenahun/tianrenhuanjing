package com.tianren.methane.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.constant.MsgDefCtrl;
import com.tianren.methane.fragment.HomeFragment;
import com.tianren.methane.fragment.ManagerFragment;
import com.tianren.methane.fragment.MeFragment;
import com.tianren.methane.fragment.NewsFragment;
import com.tianren.methane.jniutils.CommandDev;
import com.tianren.methane.jniutils.ParseDataFromDev;
import com.tianren.methane.service.SipService;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.home,R.string.guanli,R.string.usercenter};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]
            {R.drawable.tab_home_select_drawable,
                    R.drawable.tab_zixun_select_drawable,
                    R.drawable.tab_wode_select_drawable};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]
            {new HomeFragment(), new ManagerFragment(), new MeFragment()};
    //Tab 数目
    private MyViewPagerAdapter mAdapter;
    private ViewPager mViewPager;
    public static String userName;
    public  static String mDeviceId;
    private CommandDev commandDevObj = null;
    private ParseDataFromDev dataParseDevObj = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        userName = getValueFromTable("username", "");
        mDeviceId = "1001";//固定设备id

        SipService.setMidlHandler(myHandler);
        myHandler.sendEmptyMessage(MsgDefCtrl.MSG_FRESH_REFRIGERATOR);
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

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MsgDefCtrl.MSG_FRESH_REFRIGERATOR:
//                    freshFridgeManageUI();
                    myHandler.sendEmptyMessageDelayed(MsgDefCtrl.MSG_FRESH_REFRIGERATOR,5000);
                    break;
            }
        }
    };


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String str = data.getExtras().getString(CaptureActivity.EXTRA_STRING);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
