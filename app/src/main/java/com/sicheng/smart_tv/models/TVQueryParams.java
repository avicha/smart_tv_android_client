package com.sicheng.smart_tv.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by av on 2017/9/29.
 */

public class TVQueryParams {
    private String keywords = "";
    private int type = -1;
    private String years = "";
    private int is_vip = -1;
    private int region = -1;
    private int page = 1;
    private int rows = 18;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("type", String.valueOf(type));
        map.put("years", years);
        map.put("is_vip", String.valueOf(is_vip));
        map.put("region", String.valueOf(region));
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        return map;
    }
}
