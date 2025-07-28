package com.jitsi.jitsi_backend.Model;

public class MessageWS {
    private String content;
    private String senderId;
    private String receiverId;
    private String groupId;

    public MessageWS() {}

    public MessageWS(String content, String senderId, String receiverId, String groupId) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.groupId = groupId;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
}
