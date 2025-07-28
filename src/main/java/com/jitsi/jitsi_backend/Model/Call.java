package com.jitsi.jitsi_backend.Model;

public class Call {
    public enum Status {
        STARTED,
        ACCEPTED,
        ENDED
    }

    private String roomId;
    private String callerId;
    private String receiverId;
    private Status status;

    public Call(String roomId, String callerId, String receiverId) {
        this.roomId = roomId;
        this.callerId = callerId;
        this.receiverId = receiverId;
        this.status = Status.STARTED;
    }


    public String getRoomId() { return roomId; }
    public String getCallerId() { return callerId; }
    public String getReceiverId() { return receiverId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
