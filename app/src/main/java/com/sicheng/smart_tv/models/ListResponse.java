package com.sicheng.smart_tv.models;

import java.util.ArrayList;

/**
 * Created by av on 2017/8/13.
 */

public class ListResponse<T extends Object> {
    private String errcode;
    private String errmsg;
    private int total_rows;
    private ArrayList<T> result;

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

    public int getTotalRows() {
        return total_rows;
    }

    public void setTotalRows(int total_rows) {
        this.total_rows = total_rows;
    }

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }


}
