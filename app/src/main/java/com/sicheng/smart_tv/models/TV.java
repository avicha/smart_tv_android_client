package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by av on 2017/9/3.
 */

public class TV implements Parcelable {
    private String _id;
    private String name;
    private Resource resource;

    protected TV(Parcel in) {
        _id = in.readString();
        name = in.readString();
        resource = in.readParcelable(Resource.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeParcelable(resource, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) {
            return new TV(in);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
