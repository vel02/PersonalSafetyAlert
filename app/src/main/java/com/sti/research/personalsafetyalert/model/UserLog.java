package com.sti.research.personalsafetyalert.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLog implements Parcelable {

    private String name;
    private String email;
    private String phoneNumber;
    private String audioPath;
    private String title;
    private String message;
    private String latitude;
    private String longitude;
    private String location;
    private String timestamp;

    public UserLog() {
    }

    public UserLog(String name, String email, String phoneNumber, String audioPath, String title, String message, String latitude, String longitude, String location, String timestamp) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.audioPath = audioPath;
        this.title = title;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.timestamp = timestamp;
    }

    protected UserLog(Parcel in) {
        name = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        audioPath = in.readString();
        title = in.readString();
        message = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        location = in.readString();
        timestamp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(audioPath);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(location);
        dest.writeString(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserLog> CREATOR = new Creator<UserLog>() {
        @Override
        public UserLog createFromParcel(Parcel in) {
            return new UserLog(in);
        }

        @Override
        public UserLog[] newArray(int size) {
            return new UserLog[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", audioPath='" + audioPath + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", location='" + location + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
