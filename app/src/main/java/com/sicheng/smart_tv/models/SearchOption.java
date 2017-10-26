package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by av on 2017/10/5.
 */

public class SearchOption implements Parcelable {
    private String key;
    private String text;
    private String type;
    private ArrayList<SearchOptionValue> values;

    protected SearchOption(Parcel in) {
        key = in.readString();
        text = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(text);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchOption> CREATOR = new Creator<SearchOption>() {
        @Override
        public SearchOption createFromParcel(Parcel in) {
            return new SearchOption(in);
        }

        @Override
        public SearchOption[] newArray(int size) {
            return new SearchOption[size];
        }
    };

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public ArrayList<SearchOptionValue> getValues() {
        return values;
    }

    public static class SearchOptionValue {
        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }

        private String text;
        private String value;
    }
}
