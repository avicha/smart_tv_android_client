package com.sicheng.smart_tv.models;

/**
 * Created by av on 2017/8/13.
 */

public class Response<T> {
    private String errcode;
    private String errmsg;
    private T result;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
