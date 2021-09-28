package com.sti.research.personalsafetyalert.model.single;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    private String number;
    private String email;

    public Contact(String number, String email) {
        this.number = number;
        this.email = email;
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        number = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
