package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by av on 2017/10/27.
 */

public class Video implements Parcelable {
    private String _id;
    private String album_id;
    private int source;
    private String video_id;
    private int status;
    private int sequence;
    private String thumb;
    private String brief;
    private int duration;
    private String desc;

    protected Video(Parcel in) {
        album_id = in.readString();
        source = in.readInt();
        status = in.readInt();
        sequence = in.readInt();
        thumb = in.readString();
        video_id = in.readString();
        brief = in.readString();
        duration = in.readInt();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(album_id);
        dest.writeInt(source);
        dest.writeInt(status);
        dest.writeInt(sequence);
        dest.writeString(thumb);
        dest.writeString(video_id);
        dest.writeString(brief);
        dest.writeInt(duration);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getThumb() {
        return thumb == null ? "" : thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getBrief() {
        return brief == null ? "" : brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getDuration() {
        return duration;
    }

    private String padZero(String value, int len) {
        StringBuffer stringBuffer = new StringBuffer(value);
        while (stringBuffer.length() < len) {
            stringBuffer.insert(0, "0");
        }
        return stringBuffer.toString();
    }

    public String getDurationString() {
        int hour = duration / 3600;
        int minute = (duration % 3600) / 60;
        int second = duration % 60;
        if (hour != 0) {
            return padZero(String.valueOf(hour), 2) + ":" + padZero(String.valueOf(minute), 2) + ":" + padZero(String.valueOf(second), 2);
        } else {
            if (minute != 0) {
                return padZero(String.valueOf(minute), 2) + ":" + padZero(String.valueOf(second), 2);
            } else {
                if (second != 0) {
                    return "00：" + padZero(String.valueOf(second), 2);
                } else {
                    return "";
                }
            }
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getViewType() {
        if (this.getThumb().equals("")) {
            return 1;
        } else {
            return 2;
        }
    }
}
