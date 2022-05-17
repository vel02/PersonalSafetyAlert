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

    public MobileUser() {
    }

    public MobileUser(String id, String admin_id, String username, List<Logs> logs) {
        this.id = id;
        this.admin_id = admin_id;
        this.username = username;
        this.logs = logs;
    }

    protected MobileUser(Parcel in) {
        id = in.readString();
        admin_id = in.readString();
        username = in.readString();
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

    @Override
    public String toString() {
        return "\n\nMobileUser{" +
                "\nid='" + id + '\'' +
                "\nadmin_id='" + admin_id + '\'' +
                "\nusername='" + username + '\'' +
                "\nlogs=" + logs +
                "}\n\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(admin_id);
        parcel.writeString(username);
    }
}
