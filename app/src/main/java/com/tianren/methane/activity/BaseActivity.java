package com.tianren.methane.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.tianren.methane.App;
import com.tianren.methane.bean.DevInfo;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).addActivities(this);
    }

    protected void showToast(String msg) {
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
            if (posDot <= 0) return;
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
