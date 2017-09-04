package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by av on 2017/9/3.
 */

public class Resource implements Parcelable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public boolean is_free() {
        return is_free;
    }

    public void setIs_free(boolean is_free) {
        this.is_free = is_free;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public long getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(long deleted_at) {
        this.deleted_at = deleted_at;
    }

    private int source;
    private int status;
    private int play_count;
    private boolean is_free;
    private long created_at;
    private long updated_at;
    private long deleted_at;

    protected Resource(Parcel in) {
        id = in.readString();
        source = in.readInt();
        status = in.readInt();
        play_count = in.readInt();
        is_free = in.readByte() != 0;
        created_at = in.readLong();
        updated_at = in.readLong();
        deleted_at = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(source);
        dest.writeInt(status);
        dest.writeInt(play_count);
        dest.writeByte((byte) (is_free ? 1 : 0));
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
