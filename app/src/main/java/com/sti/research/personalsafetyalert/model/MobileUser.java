package com.sti.research.personalsafetyalert.model;

import com.sti.research.personalsafetyalert.model.Logs;

import java.util.List;

public class MobileUser {

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
}
