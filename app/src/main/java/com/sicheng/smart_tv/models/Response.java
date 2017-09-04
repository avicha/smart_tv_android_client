package com.sicheng.smart_tv.models;

/**
 * Created by av on 2017/8/13.
 */

public class Response<T> {
    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    private String errcode;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    private String errmsg;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    private T result;
}
