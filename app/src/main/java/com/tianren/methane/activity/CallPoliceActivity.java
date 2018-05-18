package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.AlarmBean;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.AlarmService;
import com.tianren.methane.R;
import com.tianren.methane.adapter.CallPoliceAdapter;
import com.tianren.methane.base.BasePopupWindow;
import com.tianren.methane.utils.ToastUtils;
import com.tianren.methane.view.RecycleViewDivider;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

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

    private int page = 1;
    private int isDeal = -1;

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
                Integer id = adapter.getItems().get(position).getAlarmId();
                intent.putExtra("alarmId", id);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                page = 1;
                loadData();
            }
        });
        recyclerView.setLoadMoreListener(new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
        recyclerView.loadMoreFinish(false, true);
    }

    private void loadData() {
        recyclerView.loadMoreFinish(false, true);
        WebServiceManage.getService(AlarmService.class).getAlarmListForApp(isDeal, page).setCallback(new SCallBack<BaseResponse<List<AlarmBean>>>() {
            @Override
            public void callback(boolean isok, String msg, BaseResponse<List<AlarmBean>> res) {
                if (isok) {
                    List<AlarmBean> list = res.getData();
                    if (list != null && list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            AlarmBean alarmBean = list.get(i);
                            CallPoliceAdapter.CallPoliceBean item = new CallPoliceAdapter.CallPoliceBean();
                            item.setSensorId(alarmBean.getSensorId());
                            item.setAlarmId(alarmBean.getAlarmId());
                            item.setState(alarmBean.getIsDeal());
                            item.setName(alarmBean.getSensor().getNickName());
                            item.setCallTime(alarmBean.getAlarmTime().toString());
                            item.setLevel(alarmBean.getAlarmType());
                            adapter.addItem(item);
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.loadMoreFinish(false, true);
                    } else {
                        recyclerView.loadMoreFinish(true, false);
                    }
                    if (page == 1) {
                        refreshLayout.setRefreshing(false);
                    }
                } else {
                    ToastUtils.show(msg);
                    if (page == 1) {
                        refreshLayout.setRefreshing(false);
                    }
                }
            }
        });
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("isDeal", isDeal);
//        parameters.put("pageNum", page);
//        Novate novate = new Novate.Builder(this)
//                .connectTimeout(8)
//                .baseUrl(BaseWebService.BASE_URL)
//                .addLog(true)
//                .build();
//
//        novate.post(Constant.ENTRYALARM_URL, parameters,
//                new MyBaseSubscriber<ResponseBody>(CallPoliceActivity.this) {
//                    @Override
//                    public void onError(Throwable e) {
//                        if (!TextUtils.isEmpty(e.getMessage())) {
//                            ToastUtils.show(e.getMessage());
//                            ALog.e("syl", e.getMessage());
//                        }
//                        if (page == 1) {
//                            refreshLayout.setRefreshing(false);
//                        }
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            String jstr = new String(responseBody.bytes());
//                            Log.e(TAG, "onNext: " + jstr);
//                            if (StringUtil.isEmpty(jstr) || jstr.equals("true")) {
//                                adapter.notifyDataSetChanged();
//                                recyclerView.loadMoreFinish(true, false);
//                            } else {
//                                Gson gson = new Gson();
//                                List<AlarmBean> list = gson.fromJson(jstr, new TypeToken<List<AlarmBean>>() {
//                                }.getType());
//                                if (list != null && list.size() != 0) {
//                                    for (int i = 0; i < list.size(); i++) {
//                                        AlarmBean alarmBean = list.get(i);
//                                        CallPoliceAdapter.CallPoliceBean item = new CallPoliceAdapter.CallPoliceBean();
//                                        item.setSensorId(alarmBean.getSensorId());
//                                        item.setAlarmId(alarmBean.getAlarmId());
//                                        item.setState(alarmBean.getIsDeal());
//                                        item.setName(alarmBean.getSensor().getNickName());
//                                        item.setCallTime(alarmBean.getAlarmTime().toString());
//                                        item.setLevel(alarmBean.getAlarmType());
//                                        adapter.addItem(item);
//                                    }
//                                    adapter.notifyDataSetChanged();
//                                    recyclerView.loadMoreFinish(false, true);
//                                } else {
//                                    recyclerView.loadMoreFinish(true, false);
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        if (page == 1) {
//                            refreshLayout.setRefreshing(false);
//                        }
//                    }
//                });
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
                        adapter.clear();
                        page = 1;
                        isDeal = -1;
                        loadData();
                        window.dismiss();
                    }
                });
                window.addItem("已处理", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreIv.setVisibility(View.GONE);
                        moreTv.setVisibility(View.VISIBLE);
                        moreTv.setText("已处理");
                        page = 1;
                        isDeal = 1;
                        adapter.clear();
                        loadData();
                        window.dismiss();
                    }
                });

                window.addItem("未处理", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moreIv.setVisibility(View.GONE);
                        moreTv.setVisibility(View.VISIBLE);
                        moreTv.setText("未处理");
                        isDeal = 0;
                        page = 1;
                        adapter.clear();
                        loadData();
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
