package com.tianren.methane.bean;

/**
 * Created by Administrator on 2018/4/25.
 */

public class EntryBean {

    //{"result":true,"message":"录入成功"}

    private Boolean result;
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
