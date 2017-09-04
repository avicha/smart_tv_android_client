package com.sicheng.smart_tv.models;

import android.app.Activity;

/**
 * Created by av on 2017/9/4.
 */

public class Menu {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }

    private Class<? extends Activity> activity;

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private int backgroundColor;

    public Menu(String text, Class<? extends Activity> activity) {
        this.setText(text);
        this.setActivity(activity);
    }
}
