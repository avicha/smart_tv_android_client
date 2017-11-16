package com.sicheng.smart_tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by av on 2017/11/16.
 */

public class VideoPlayInfo implements Parcelable {
    private Video next;
    private Video previous;
    private ArrayList<VideoType> playlist;

    protected VideoPlayInfo(Parcel in) {
        next = in.readParcelable(Video.class.getClassLoader());
        previous = in.readParcelable(Video.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(next, flags);
        dest.writeParcelable(previous, flags);
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

    public Video getNext() {
        return next;
    }

    public void setNext(Video next) {
        this.next = next;
    }

    public Video getPrevious() {
        return previous;
    }

    public void setPrevious(Video previous) {
        this.previous = previous;
    }

    public ArrayList<VideoType> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<VideoType> playlist) {
        this.playlist = playlist;
    }

    public static class VideoType {
        private int duration;
        private int width;
        private int height;
        private String type;
        private String lang;
        private String url;

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

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

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public VideoType getDefaultPlayVideoType() {
        VideoType defaultPlayVideoType = null;
        for (VideoType videoType : this.getPlaylist()) {
            if (videoType.getType().equals("mp4hd2")) {
                return videoType;
            } else {
                if (defaultPlayVideoType == null) {
                    defaultPlayVideoType = videoType;
                } else {
                    if (videoType.getType().equals("mp4hd") && defaultPlayVideoType.getType().equals("mp4sd")) {
                        defaultPlayVideoType = videoType;
                    }

                }
            }
        }
        return defaultPlayVideoType;
    }
}
