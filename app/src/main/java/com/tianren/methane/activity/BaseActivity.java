package com.tianren.methane.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tianren.methane.App;
import com.tianren.methane.R;
import com.tianren.methane.bean.DevInfo;
import com.tianren.methane.manager.SystemBarTintManager;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
    protected SystemBarTintManager mTintManager;  //定义个管理器
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).addActivities(this);
        // 沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBar(true);
            setTranslucentStatus(true);
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(getResources().getColor(R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(getResources().getColor(R.color.transparent));
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar(boolean navi) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        if (navi) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN// 状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
//					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION// 导航栏不会被隐藏但activity布局会扩展到导航栏所在位置
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//			window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable edt) {
            String temp = edt.toString();
            int posDot = temp.indexOf(".");
            if (posDot <= 0){ return;}
            if (temp.length() - posDot - 1 > 2) {
                edt.delete(posDot + 3, posDot + 4);
            }
        }
    };

    public void putValueToTable(String key, String value) {
        //得SharedPreferences对象,第一个参数为文件名，没有则创建,第二个参数为存储模式
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValueFromTable(String key, String defaultValue) {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String value = sp.getString(key, defaultValue);
        return value;
    }

    /*获取本地设备信息*/
    @SuppressWarnings("unchecked")
    public ArrayList<DevInfo> getDevInfo(String username) {
        ArrayList<DevInfo> devInfoList = new ArrayList<DevInfo>();

        SharedPreferences preferences = getSharedPreferences("base64", MODE_PRIVATE);
        String infoBase64 = preferences.getString(username + "devinfo", "");

        byte[] base64 = Base64.decodeBase64(infoBase64.getBytes());    //读字节
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);   //封装到字节流
        try {
            ObjectInputStream bis = new ObjectInputStream(bais);        //再次封装
            try {
                //读取对象
                devInfoList = (ArrayList<DevInfo>) bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return devInfoList;
    }

}
