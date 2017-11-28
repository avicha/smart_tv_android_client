package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by av on 2017/11/16.
 */

public class VideoPlayInfo implements Parcelable {
    private int width;
    private int height;
    private String type;
    private String type_text;
    private String lang;
    private String lang_text;
    private String url;
    private int weight;

    protected VideoPlayInfo(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        type = in.readString();
        type_text = in.readString();
        lang = in.readString();
        lang_text = in.readString();
        url = in.readString();
        weight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(type);
        dest.writeString(type_text);
        dest.writeString(lang);
        dest.writeString(lang_text);
        dest.writeString(url);
        dest.writeInt(weight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoPlayInfo> CREATOR = new Creator<VideoPlayInfo>() {
        @Override
        public VideoPlayInfo createFromParcel(Parcel in) {
            return new VideoPlayInfo(in);
        }

        @Override
        public VideoPlayInfo[] newArray(int size) {
            return new VideoPlayInfo[size];
        }
    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang_text() {
        return lang_text;
    }

    public void setLang_text(String lang_text) {
        this.lang_text = lang_text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
