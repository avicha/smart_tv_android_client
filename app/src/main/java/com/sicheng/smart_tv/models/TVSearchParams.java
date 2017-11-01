package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by av on 2017/9/29.
 */

public class TVSearchParams implements Parcelable {
    private String keywords = "";
    private String type = "-1";
    private String years = "";
    private String is_vip = "-1";
    private String region = "-1";
    private String sort = "new";
    private String page = "1";
    private String rows = "18";

    public TVSearchParams() {

    }


    protected TVSearchParams(Parcel in) {
        keywords = in.readString();
        type = in.readString();
        years = in.readString();
        is_vip = in.readString();
        region = in.readString();
        sort = in.readString();
        page = in.readString();
        rows = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keywords);
        dest.writeString(type);
        dest.writeString(years);
        dest.writeString(is_vip);
        dest.writeString(region);
        dest.writeString(sort);
        dest.writeString(page);
        dest.writeString(rows);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TVSearchParams> CREATOR = new Creator<TVSearchParams>() {
        @Override
        public TVSearchParams createFromParcel(Parcel in) {
            return new TVSearchParams(in);
        }

        @Override
        public TVSearchParams[] newArray(int size) {
            return new TVSearchParams[size];
        }
    };

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("type", type);
        map.put("years", years);
        map.put("is_vip", is_vip);
        map.put("region", region);
        map.put("sort", sort);
        map.put("page", page);
        map.put("rows", rows);
        return map;
    }
}
