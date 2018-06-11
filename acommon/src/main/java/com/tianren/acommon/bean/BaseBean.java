package com.tianren.acommon.bean;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2018/6/11.
 */

public class BaseBean {

    private boolean result;
    private String message;
    private Object data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
