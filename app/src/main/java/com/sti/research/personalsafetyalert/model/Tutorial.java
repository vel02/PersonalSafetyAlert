package com.sti.research.personalsafetyalert.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tutorial implements Parcelable {

    private String title;
    private String description;
    private int logo;

    public Tutorial(String title, String description, int logo) {
        this.title = title;
        this.description = description;
        this.logo = logo;
    }

    protected Tutorial(Parcel in) {
        title = in.readString();
        description = in.readString();
        logo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(logo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tutorial> CREATOR = new Creator<Tutorial>() {
        @Override
        public Tutorial createFromParcel(Parcel in) {
            return new Tutorial(in);
        }

        @Override
        public Tutorial[] newArray(int size) {
            return new Tutorial[size];
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

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
