package com.sti.research.personalsafetyalert.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Permission implements Parcelable {

    private String title;
    private String description;
    private String buttonTitle;

    public Permission(String title, String description, String buttonTitle) {
        this.title = title;
        this.description = description;
        this.buttonTitle = buttonTitle;
    }

    protected Permission(Parcel in) {
        title = in.readString();
        description = in.readString();
        buttonTitle = in.readString();
    }

    public static final Creator<Permission> CREATOR = new Creator<Permission>() {
        @Override
        public Permission createFromParcel(Parcel in) {
            return new Permission(in);
        }

        @Override
        public Permission[] newArray(int size) {
            return new Permission[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(buttonTitle);
    }
}
