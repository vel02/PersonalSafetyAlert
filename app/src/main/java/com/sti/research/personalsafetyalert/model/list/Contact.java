package com.sti.research.personalsafetyalert.model.list;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Contacts")
public class Contact implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "mobileNumber")
    private String mobileNumber;
    @ColumnInfo(name = "mobileNetwork")
    private String mobileNetwork;
    @ColumnInfo(name = "email")
    private String email;

    public Contact(int id, String name, String mobileNumber, String mobileNetwork, String email) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.mobileNetwork = mobileNetwork;
        this.email = email;
    }

    @Ignore
    public Contact() {
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mobileNumber = in.readString();
        mobileNetwork = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(mobileNumber);
        dest.writeString(mobileNetwork);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNetwork() {
        return mobileNetwork;
    }

    public void setMobileNetwork(String mobileNetwork) {
        this.mobileNetwork = mobileNetwork;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", mobileNetwork='" + mobileNetwork + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
