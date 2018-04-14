package com.tianren.methane.activity;

import android.content.Intent;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.methane.bean.DevInfo;
import com.tianren.methane.bean.SensorDataBean;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.constant.MsgDefCtrl;
import com.tianren.methane.fragment.HomeFragment;
import com.tianren.methane.fragment.ManagerFragment;
import com.tianren.methane.fragment.MeFragment;
import com.tianren.methane.fragment.NewsFragment;
import com.tianren.methane.jniutils.CommandDev;
import com.tianren.methane.jniutils.MyInterface;
import com.tianren.methane.jniutils.ParseDataFromDev;
import com.tianren.methane.service.SipService;
import com.tianren.methane.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

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
    public  static String mDeviceId;
    public static String userName;
    private Novate novate;
    public static Map<String,String> modelMap = new HashMap<>();
    private ArrayList<SensorDataBean> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        userName = getValueFromTable("username", "");
        getDeviceModel();

        initStaticData();

    }

    private void initStaticData() {
        Map<String, Object> parameters = new HashMap<>();
//        parameters.put(Constant.STATICDATANAME_URL, "");
        novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Constant.BASE_URL)
                //.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        novate.post(Constant.ALLDATA_URL + "/" + Constant.STATICDATANAME_URL, parameters,
                new MyBaseSubscriber<ResponseBody>(MainActivity.this) {
                    @Override
                    public void onError(Throwable e) {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            ToastUtils.show(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jstr = new String(responseBody.bytes());
                            ToastUtils.show(jstr);

                            Gson gson = new Gson();

                            Log.e("syl",jstr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initViews() {
        mDeviceId = "1001";//固定设备id
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES, TAB_IMGS);

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.maintab_custom, null);
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
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

            }
        }
    };

    private void getDeviceModel() {
        String res = "add_time:2018-04-13-09-17-23\n" +
                "d1:5.04\n" +
                "d2:35.21\n" +
                "d3:50.04\n" +
                "d4:7.05\n" +
                "d5:2.00\n" +
                "d6:1.99\n" +
                "d7:40.33\n" +
                "d8:79.86\n" +
                "d9:5.03\n" +
                "d10:2.00\n" +
                "d11:35.28\n" +
                "d12:35.22\n" +
                "d13:34.69\n" +
                "d14:35.18\n" +
                "d15:35.06\n" +
                "d16:35.08\n" +
                "d17:40.06\n" +
                "d18:39.76\n" +
                "d19:79.94\n" +
                "d20:100.58\n" +
                "d21:100.34\n" +
                "d22:59.78\n" +
                "d23:59.91\n" +
                "d24:60.05\n" +
                "d25:49.76\n" +
                "d26:99.44\n" +
                "d27:7.95\n" +
                "d28:7.93\n" +
                "d29:4.02\n" +
                "d30:1.00\n" +
                "d31:59.85\n" +
                "d32:3.02\n" +
                "d33:2.01\n" +
                "d34:30.23\n" +
                "d35:2.00\n" +
                "d36:99.53\n" +
                "d37:7.01\n" +
                "d38:50.29\n" +
                "d39:19.95\n" +
                "d40:7.95\n" +
                "d41:50.49\n" +
                "d42:20.09\n" +
                "d43:37.26\n" +
                "d44:37.01\n" +
                "d45:36.51\n" +
                "d46:36.04\n" +
                "d47:35.75\n" +
                "d48:9.97\n" +
                "d49:3.01\n" +
                "d50:100.45";
        String[] attr = res.split("\\n");
        if (attr.length > 0) {
            for (int i = 1; i < attr.length; i++) {
                if (!attr[i].contains(":")) {
                    continue;
                }
                String[] tempArr = attr[i].split(":");
                modelMap.put(tempArr[0], tempArr[1]);
            }
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String str = data.getExtras().getString(CaptureActivity.EXTRA_STRING);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
