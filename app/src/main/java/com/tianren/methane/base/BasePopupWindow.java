package com.tianren.methane.base;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianren.methane.R;


/**
 * @author Mr.Qiu
 * @date 2018/1/16
 */
public class BasePopupWindow extends PopupWindow {
    private Context context;
    private ListView listView;
    private PopAdapter adapter;

    public BasePopupWindow(Context context) {
        super(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.context = context;
        init();
    }

    /**
     * 初始化PopupWindow
     */
    private void init() {
        View winView = LayoutInflater.from(context).inflate(R.layout.window_base, null, false);
        setContentView(winView);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        adapter = new PopAdapter(context);
        listView = (ListView) getContentView().findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    /**
     * 展示弹出框
     * 参数的view下面
     *
     * @param v
     */
    public void show(View v) {
        adapter.notifyDataSetChanged();
        if (adapter == null || adapter.getItems().size() == 0) {
            return;
        }
        int totalHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            int width = listItem.getMeasuredWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        params.width = maxWidth;
        listView.setLayoutParams(params);
        showAsDropDown(v, 0, 0);
    }

    /**
     * 展示弹出框
     * toolbar高度下
     *
     * @param height
     */
    public void show(int height) {
        adapter.notifyDataSetChanged();
        if (adapter == null || adapter.getItems().size() == 0) {
            return;
        }
        int totalHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            int width = listItem.getMeasuredWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        params.width = maxWidth;
        listView.setLayoutParams(params);
        showAtLocation(getContentView(), Gravity.RIGHT | Gravity.TOP, 0, height - 10);
    }

    /**
     * 添加显示的条目
     *
     * @param text
     */
    public void addItem(String text, final View.OnClickListener onClickListener) {
        adapter.addItem(new PopBean(text, onClickListener));
    }

    /**
     * 适配器
     */
    class PopAdapter extends BaseAdapterEx<PopBean> {

        public PopAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_window_base, parent, false);
            ((TextView) convertView.findViewById(R.id.text)).setText(getItem(position).getItem());
            convertView.findViewById(R.id.text).setOnClickListener(getItem(position).getListener());
            return convertView;
        }
    }

    public static class PopBean {
        private String item;
        private View.OnClickListener listener;

        public PopBean(String item, View.OnClickListener listener) {
            this.item = item;
            this.listener = listener;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public View.OnClickListener getListener() {
            return listener;
        }

        public void setListener(View.OnClickListener listener) {
            this.listener = listener;
        }
    }
}
