package com.jitsi.jitsi_backend.Model;

public class Meeting {
    private String id;
    private String url;

    public Meeting(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

}
