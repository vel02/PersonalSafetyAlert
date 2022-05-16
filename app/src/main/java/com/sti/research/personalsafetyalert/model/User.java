package com.sti.research.personalsafetyalert.model;

import java.util.List;

public class User {

    private String admin_id;
    private String email;
    private List<MobileUser> mobileUsers;

    public User() {
    }

    public User(String admin_id, String email, List<MobileUser> mobileUsers) {
        this.admin_id = admin_id;
        this.email = email;
        this.mobileUsers = mobileUsers;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MobileUser> getUsers() {
        return mobileUsers;
    }

    public void setUsers(List<MobileUser> mobileUsers) {
        this.mobileUsers = mobileUsers;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "admin_id='" + admin_id + '\'' +
                ", email='" + email + '\'' +
                ", users=" + mobileUsers +
                '}';
    }
}
