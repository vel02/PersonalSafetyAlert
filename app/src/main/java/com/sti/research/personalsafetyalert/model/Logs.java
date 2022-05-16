package com.sti.research.personalsafetyalert.model;

import java.util.List;

public class Logs {

    private String mobileusers_id;
    private String title;
    private String message;
    private List<String> images;
    private String video;
    private String count;
    private String timestamp;

    public Logs() {
    }

    public Logs(String mobileusers_id, String title, String message, List<String> images, String video, String count, String timestamp) {
        this.mobileusers_id = mobileusers_id;
        this.title = title;
        this.message = message;
        this.images = images;
        this.video = video;
        this.count = count;
        this.timestamp = timestamp;
    }

    public String getMobileusers_id() {
        return mobileusers_id;
    }

    public void setMobileusers_id(String mobileusers_id) {
        this.mobileusers_id = mobileusers_id;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
