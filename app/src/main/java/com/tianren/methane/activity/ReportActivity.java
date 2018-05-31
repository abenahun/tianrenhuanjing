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
import com.tianren.acommon.bean.ReportBean;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.adapter.ReportAdapter;
import com.tianren.methane.utils.ToastUtils;
import com.tianren.methane.view.RecycleViewDivider;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

/**
 * @author qiushengtao
 */
public class ReportActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private ImageView moreIv;

    private SwipeMenuRecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ReportAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("生产报表");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setImageResource(R.mipmap.call_police);
        moreIv.setOnClickListener(this);
        moreIv.setVisibility(View.VISIBLE);

        adapter = new ReportAdapter(this);
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.useDefaultLoadMore();
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.divider_more)));
        recyclerView.setItemViewSwipeEnabled(false);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(ReportActivity.this, ProductReportActivity.class);
                startActivity(intent);
            }
        });
        loadData();
    }

    private void loadData() {
        WebServiceManage.getService(EntryService.class).getProData(page).setCallback(new SCallBack<BaseResponse<List<ReportBean>>>() {
            @Override
            public void callback(boolean isok, String msg, BaseResponse<List<ReportBean>> res) {
                if (isok) {
                    if (page == 1) {
                        adapter.clear();
                        refreshLayout.setRefreshing(false);
                    }
                    if (res != null && res.getData() != null && res.getData().size() != 0) {
                        for (int i = 0; i < res.getData().size(); i++) {
                            adapter.addItem(res.getData().get(i).getReportTime());
                        }
                        recyclerView.loadMoreFinish(false, true);
                    } else {
                        recyclerView.loadMoreFinish(true, false);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(msg);
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }
}
