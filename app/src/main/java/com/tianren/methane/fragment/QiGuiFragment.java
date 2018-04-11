package com.tianren.methane.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.adapter.YanYangAdapter;
import com.tianren.methane.bean.YanYangBean;
import com.tianren.methane.view.EndLessOnScrollListener;
import com.tianren.methane.view.MyDecoration;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/26.
 */

public class QiGuiFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private YanYangAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private ArrayList<YanYangBean.YanYangDate> mData;
    private YanYangBean.YanYangDate date;
    private YanYangBean yanyangbean;

    private LinearLayout ll_back;
    private TextView tv_title;

    public static QiGuiFragment newInstance(String id) {
        QiGuiFragment f = new QiGuiFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_yanyang, null);

        initData();
        initView(view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //添加分隔线
        mRecyclerView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));

        //为RecyclerView加载Adapter
        mRecyclerView.setAdapter(mAdapter);

        //监听SwipeRefreshLayout.OnRefreshListener
        mRefreshLayout.setOnRefreshListener(this);

        /**
         * 监听addOnScrollListener这个方法，新建我们的EndLessOnScrollListener
         * 在onLoadMore方法中去完成上拉加载的操作
         * */
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });
        return view;
    }

    private void initView(View view) {
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        mAdapter = new YanYangAdapter(getActivity(),mData);

        //这个是下拉刷新出现的那个圈圈要显示的颜色
        mRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.maincolor,
                R.color.blueviolet
        );

        ll_back = (LinearLayout) view.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("气柜数据");
    }

    //初始化一开始加载的数据
    private void initData(){
        yanyangbean = new YanYangBean();
        mData = new ArrayList<YanYangBean.YanYangDate>();
        for (int i =0; i < 10; i++){
            date = yanyangbean.new YanYangDate();
            date.setCh4("10Vol%");
            date.setCo2("11Vol%");
            date.setH2s("12ppm");
            date.setHeigh("1000");
            date.setO2("13Vol%");
            date.setPower("100");
            date.setTime("2018-03-27 13:15:23");
            date.setStatus("沼气泄露状态：无泄漏");
            mData.add(date);
        }
    }

    //每次上拉加载的时候，就加载十条数据到RecyclerView中
    private void loadMoreData(){
        for (int i =0; i < 10; i++){

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        updateData();
        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    private void updateData(){
        //我在List最前面加入一条数据

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_back:
                getActivity().finish();
                break;
        }
    }
}
