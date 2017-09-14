package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by av on 2017/9/3.
 */

public class TV implements Parcelable {
    private String _id;
    private String name;
    private String folder;
    private int part_count;
    private ArrayList<String> actors;
    private ArrayList<Resource> resources;

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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getPart_count() {
        return part_count;
    }

    public void setPart_count(int part_count) {
        this.part_count = part_count;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }


    protected TV(Parcel in) {
        _id = in.readString();
        name = in.readString();
        folder = in.readString();
        part_count = in.readInt();
        actors = in.createStringArrayList();
        resources = in.createTypedArrayList(Resource.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(folder);
        dest.writeInt(part_count);
        dest.writeStringList(actors);
        dest.writeTypedList(resources);
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
}
