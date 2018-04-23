package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.aloglibrary.ALog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.methane.adapter.CallPoliceAdapter;
import com.tianren.methane.base.BasePopupWindow;
import com.tianren.methane.bean.AlarmBean;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;
import com.tianren.methane.view.RecycleViewDivider;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * @author Mr.Qiu
 */
public class CallPoliceActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CallPoliceActivity";
    private LinearLayout ll_back;
    private TextView tv_title;
    private SwipeMenuRecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private CallPoliceAdapter adapter;

    private ImageView moreIv;
    private TextView moreTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_police);
        initView();
        loadData();
    }


    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("报警信息");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreTv = (TextView) findViewById(R.id.moreTv);
        moreIv.setVisibility(View.VISIBLE);
        findViewById(R.id.more).setOnClickListener(this);

        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.division_line)));
        recyclerView.useDefaultLoadMore();
        adapter = new CallPoliceAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(CallPoliceActivity.this, HandleCallPoliceActivity.class);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setLoadMoreListener(new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
            }
        });
    }

    private void loadData() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("isDeal", 1);
        parameters.put("pageNum", 1);
        Novate novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Constant.BASE_URL)
                .addLog(true)
                .build();

        novate.post(Constant.ENTRYALARM_URL, parameters,
                new MyBaseSubscriber<ResponseBody>(CallPoliceActivity.this) {
                    @Override
                    public void onError(Throwable e) {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            ToastUtils.show(e.getMessage());
                            ALog.e("syl",e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jstr = new String(responseBody.bytes());
                            Log.e(TAG, "onNext: " + jstr);
                            if (StringUtil.isEmpty(jstr)) {
                            } else {
                                Gson gson = new Gson();
                                gson.fromJson(jstr, new TypeToken<List<AlarmBean>>() {
                                }.getType());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        CallPoliceAdapter.CallPoliceBean item1 = new CallPoliceAdapter.CallPoliceBean();
        item1.setLevel(1);
        item1.setCallTime("2018-12-26 12:13:00");
        item1.setName("智能传感器1");
        item1.setState(1);
        adapter.addItem(item1);
        CallPoliceAdapter.CallPoliceBean item2 = new CallPoliceAdapter.CallPoliceBean();
        item2.setLevel(2);
        item2.setCallTime("2018-12-26 12:13:00");
        item2.setName("智能传感器2");
        item2.setState(1);
        adapter.addItem(item2);
        CallPoliceAdapter.CallPoliceBean item3 = new CallPoliceAdapter.CallPoliceBean();
        item3.setLevel(3);
        item3.setCallTime("2018-12-26 12:13:00");
        item3.setName("智能传感器3");
        item3.setState(1);
        adapter.addItem(item3);
        CallPoliceAdapter.CallPoliceBean item4 = new CallPoliceAdapter.CallPoliceBean();
        item4.setLevel(4);
        item4.setCallTime("2018-12-26 12:13:00");
        item4.setName("智能传感器4");
        item4.setState(2);
        adapter.addItem(item4);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.more:
                final BasePopupWindow window = new BasePopupWindow(this);
                window.addItem("全部", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreIv.setVisibility(View.VISIBLE);
                        moreTv.setVisibility(View.GONE);
                        moreTv.setText("全部");
                        window.dismiss();
                    }
                });
                window.addItem("已处理", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreIv.setVisibility(View.GONE);
                        moreTv.setVisibility(View.VISIBLE);
                        moreTv.setText("已处理");
                        window.dismiss();
                    }
                });

                window.addItem("未处理", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreIv.setVisibility(View.GONE);
                        moreTv.setVisibility(View.VISIBLE);
                        moreTv.setText("未处理");
                        window.dismiss();
                    }
                });
                window.show(v);
                break;
            default:
                break;
        }
    }
}
