package com.tianren.methane.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Qiu on 2018\4\14 0014.
 * RecycleView基础适配器
 */
public abstract class BaseRcAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = "BaseRcAdapter";
    protected List<T> items;
    protected Context context;

    public BaseRcAdapter(Context context) {
        items = new ArrayList<T>();
        this.context = context;
    }

    public void addItems(List<T> items) {
        if (items != null && items.size() > 0) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void addItem(T item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    /**
     * 动画移除某个位置的元素
     *
     * @param p
     */
    public void remove(int p) {
        if (p > items.size()) {
            throw new IndexOutOfBoundsException("Index: " + p + ", Size: " + items.size());
        } else {
            items.remove(p);
            notifyItemRemoved(p);
        }
    }

    /**
     * 动画添加到某个位置
     *
     * @param p
     * @param item
     */
    public void insert(int p, T item) {
        if (p > items.size()) {
            throw new IndexOutOfBoundsException("Index: " + p + ", Size: " + items.size());
        } else {
            Log.e(TAG, "insert: 适配器插入一条数据");
            items.add(p, item);
            notifyItemInserted(p);
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
