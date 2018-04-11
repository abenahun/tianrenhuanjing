package com.tianren.methane.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianren.methane.R;


/**
 * Created by Administrator on 2016/4/6.
 */
public class ImgPickerPopupwindow extends PopupWindow implements View.OnClickListener {


    private Context context;
    private View parentView;
    private Handler myHandler;
    private View root;
    private TextView btn_take_photo, btn_pick_photo,btn_cancel;
    private View ll_parent;



    public ImgPickerPopupwindow(Context context, View parentView,
                                final Handler myHandler, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.parentView = parentView;
        this.myHandler = myHandler;
        this.context = context;
        this.parentView = parentView;
        setTouchable(true);
        setOutsideTouchable(true);
        root = LayoutInflater.from(context).inflate(
                R.layout.popwindow_photo, null);

        setUpViews();
        setContentView(root);
        initLayout();
        ColorDrawable dw = new ColorDrawable(0x7DC0C0C0);
        setBackgroundDrawable(dw);

        setClippingEnabled(true);

    }

    // 弹出动画、高度等设置
    private void initLayout() {
        // setAnimationStyle(R.style.anim_issue_popwindow);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
    }


    public void show() {
        showAtLocation(parentView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL,
                0, 0);
    }

    private void setUpViews() {

        ll_parent=root.findViewById(R.id.ll_parent);
        btn_take_photo = (TextView) root.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (TextView) root.findViewById(R.id.btn_pick_photo);
        btn_cancel = (TextView) root.findViewById(R.id.btn_cancel);
        // 添加onclick事件
        btn_take_photo.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        ll_parent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Message message= Message.obtain();
        switch (v.getId()) {
            case R.id.btn_take_photo://相机
                message.arg1=1;
                myHandler.sendMessage(message);
                dismiss();
                break;
            case R.id.btn_pick_photo://相册
                message.arg1=2;
                dismiss();
                myHandler.sendMessage(message);
                break;
            case R.id.btn_cancel:

                dismiss();
            case R.id.ll_parent:
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }



















}
