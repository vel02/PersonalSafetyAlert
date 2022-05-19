package com.sti.research.personalsafetyalert.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sti.research.personalsafetyalert.model.Logs;

import java.util.List;

public class MobileUser implements Parcelable {

    private String id;
    private String admin_id;
    private String username;
    private List<Logs> logs;
    private List<UserLog> userLogs;

    public MobileUser() {
    }

    public MobileUser(String id, String admin_id, String username, List<Logs> logs, List<UserLog> userLogs) {
        this.id = id;
        this.admin_id = admin_id;
        this.username = username;
        this.logs = logs;
        this.userLogs = userLogs;
    }

    protected MobileUser(Parcel in) {
        id = in.readString();
        admin_id = in.readString();
        username = in.readString();
        logs = in.createTypedArrayList(Logs.CREATOR);
        userLogs = in.createTypedArrayList(UserLog.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(admin_id);
        dest.writeString(username);
        dest.writeTypedList(logs);
        dest.writeTypedList(userLogs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MobileUser> CREATOR = new Creator<MobileUser>() {
        @Override
        public MobileUser createFromParcel(Parcel in) {
            return new MobileUser(in);
        }

        @Override
        public MobileUser[] newArray(int size) {
            return new MobileUser[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }

    public List<UserLog> getUserLogs() {
        return userLogs;
    }

    public void setUserLogs(List<UserLog> userLogs) {
        this.userLogs = userLogs;
    }

    @Override
    public String toString() {
        return "MobileUser{" +
                "id='" + id + '\'' +
                ", admin_id='" + admin_id + '\'' +
                ", username='" + username + '\'' +
                ", logs=" + logs +
                ", userLogs=" + userLogs +
                '}';
    }
}
