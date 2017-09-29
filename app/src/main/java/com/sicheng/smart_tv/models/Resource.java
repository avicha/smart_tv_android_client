package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by av on 2017/9/3.
 */

public class Resource implements Parcelable {
    private String id;
    private int source;
    private int status;
    private ArrayList<String> alias;
    private String folder;
    private ArrayList<String> actors;
    private int play_count;
    private String publish_date;
    private boolean is_vip;
    private float score;

    public String getId() {
        return id;
    }

    public int getSource() {
        return source;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<String> getAlias() {
        return alias;
    }

    public String getFolder() {
        return folder;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public int getPlay_count() {
        return play_count;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public boolean is_vip() {
        return is_vip;
    }

    public float getScore() {
        return score;
    }

    public String getUpdate_notify_desc() {
        return update_notify_desc;
    }

    public int getCurrent_part() {
        return current_part;
    }

    public int getPart_count() {
        return part_count;
    }

    public String getDesc() {
        return desc;
    }

    public String getRegion() {
        return region;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getDirector() {
        return director;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public long getDeleted_at() {
        return deleted_at;
    }

    private String update_notify_desc;
    private int current_part;
    private int part_count;
    private String desc;
    private String region;
    private ArrayList<String> types;
    private String director;
    private long created_at;
    private long updated_at;
    private long deleted_at;

    protected Resource(Parcel in) {
        id = in.readString();
        source = in.readInt();
        status = in.readInt();
        alias = in.createStringArrayList();
        folder = in.readString();
        actors = in.createStringArrayList();
        play_count = in.readInt();
        publish_date = in.readString();
        is_vip = in.readByte() != 0;
        score = in.readFloat();
        update_notify_desc = in.readString();
        current_part = in.readInt();
        part_count = in.readInt();
        desc = in.readString();
        region = in.readString();
        types = in.createStringArrayList();
        director = in.readString();
        created_at = in.readLong();
        updated_at = in.readLong();
        deleted_at = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(source);
        dest.writeInt(status);
        dest.writeStringList(alias);
        dest.writeString(folder);
        dest.writeStringList(actors);
        dest.writeInt(play_count);
        dest.writeString(publish_date);
        dest.writeByte((byte) (is_vip ? 1 : 0));
        dest.writeFloat(score);
        dest.writeString(update_notify_desc);
        dest.writeInt(current_part);
        dest.writeInt(part_count);
        dest.writeString(desc);
        dest.writeString(region);
        dest.writeStringList(types);
        dest.writeString(director);
        dest.writeLong(created_at);
        dest.writeLong(updated_at);
        dest.writeLong(deleted_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Resource> CREATOR = new Creator<Resource>() {
        @Override
        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        @Override
        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };
}
