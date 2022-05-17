package com.sti.research.personalsafetyalert.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Logs implements Parcelable {

    private String mobileusers_id;
    private String log_id;
    private String title;
    private String message;
    private String images;
    private String video;
    private String count;
    private String timestamp;

    public Logs() {
    }

    public Logs(String mobileusers_id, String log_id, String title, String message, String images, String video, String count, String timestamp) {
        this.mobileusers_id = mobileusers_id;
        this.log_id = log_id;
        this.title = title;
        this.message = message;
        this.images = images;
        this.video = video;
        this.count = count;
        this.timestamp = timestamp;
    }

    protected Logs(Parcel in) {
        mobileusers_id = in.readString();
        log_id = in.readString();
        title = in.readString();
        message = in.readString();
        images = in.readString();
        video = in.readString();
        count = in.readString();
        timestamp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileusers_id);
        dest.writeString(log_id);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(images);
        dest.writeString(video);
        dest.writeString(count);
        dest.writeString(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Logs> CREATOR = new Creator<Logs>() {
        @Override
        public Logs createFromParcel(Parcel in) {
            return new Logs(in);
        }

        @Override
        public Logs[] newArray(int size) {
            return new Logs[size];
        }
    };

    public String getMobileusers_id() {
        return mobileusers_id;
    }

    public void setMobileusers_id(String mobileusers_id) {
        this.mobileusers_id = mobileusers_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "\n\nLogs: " +
                "\nmobileusers_id='" + mobileusers_id + '\'' +
                "\nlog_id='" + log_id + '\'' +
                "\ntitle='" + title + '\'' +
                "\nmessage='" + message + '\'' +
                "\nimages=" + images +
                "\nvideo='" + video + '\'' +
                "\ncount='" + count + '\'' +
                "\ntimestamp='" + timestamp + '\'' +
                "}\n\n";
    }
}
