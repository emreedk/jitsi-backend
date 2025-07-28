package com.jitsi.jitsi_backend.Model;

public class CallResponse {
    private String roomId;
    private String jitsiUrl;
    private String token;

    public CallResponse(String roomId, String jitsiUrl, String token) {
        this.roomId = roomId;
        this.jitsiUrl = jitsiUrl;
        this.token = token;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getJitsiUrl() {
        return jitsiUrl;
    }

    public String getToken() {
        return token;
    }
}

