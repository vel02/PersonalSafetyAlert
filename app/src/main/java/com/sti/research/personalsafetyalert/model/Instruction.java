package com.sti.research.personalsafetyalert.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {

    private String titleText;
    private String contentText;
    private String accessText;

    public Instruction(String titleText, String contentText, String accessText) {
        this.titleText = titleText;
        this.contentText = contentText;
        this.accessText = accessText;
    }

    protected Instruction(Parcel in) {
        titleText = in.readString();
        contentText = in.readString();
        accessText = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleText);
        dest.writeString(contentText);
        dest.writeString(accessText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Instruction> CREATOR = new Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getAccessText() {
        return accessText;
    }

    public void setAccessText(String accessText) {
        this.accessText = accessText;
    }
}
