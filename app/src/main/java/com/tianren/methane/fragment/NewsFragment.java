package com.tianren.methane.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianren.methane.R;
import com.tianren.methane.adapter.NewsAdapter;
import com.tianren.methane.view.EndLessOnScrollListener;
import com.tianren.methane.view.MyDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class NewsFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private List<String> mData;
    private String url = "http://v.juhe.cn/toutiao/index?type=top&key=f8d4007a955d42fd9e1ee9dceba12466";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, null);
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
        mAdapter = new NewsAdapter(getActivity(),mData);

        //这个是下拉刷新出现的那个圈圈要显示的颜色
        mRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.maincolor,
                R.color.blueviolet
        );
    }

    //初始化一开始加载的数据
    private void initData(){
        mData = new ArrayList<String>();
        for (int i =0; i < 10; i++){
            mData.add(i+"");
        }
    }

    //每次上拉加载的时候，就加载十条数据到RecyclerView中
    private void loadMoreData(){
        for (int i =0; i < 10; i++){
            mData.add("嘿，我是“上拉加载”生出来的"+i);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 重写SwipeRefreshLayout.OnRefreshListener的OnRefresh方法
     * 在这里面去做下拉刷新的操作
     */
    @Override
    public void onRefresh() {
        updateData();
        //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    private void updateData(){
        //我在List最前面加入一条数据
        mData.add(0, "嘿，我是“下拉刷新”生出来的");
    }

    @Override
    public void onClick(View v) {

    }
}
