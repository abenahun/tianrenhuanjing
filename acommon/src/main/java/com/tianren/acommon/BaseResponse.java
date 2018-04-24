package com.tianren.acommon;

/**
 * @author Mr.Qiu
 * @date 2018/4/24
 */
public class BaseResponse<T> {
    private Boolean result;
    private T data;
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
