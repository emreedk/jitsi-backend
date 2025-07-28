package com.jitsi.jitsi_backend.Dto;

import com.jitsi.jitsi_backend.Entity.Message;
import com.jitsi.jitsi_backend.Enum.MessageType;

import java.time.LocalDateTime;

public class MessageDto {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Long senderId;
    private Long receiverId;
    private Long groupId;
    private MessageType messageType;
    private boolean delivered;
    private boolean read;

    public MessageDto() {}

    public MessageDto(Long id, String content, LocalDateTime timestamp, Long senderId, Long receiverId, Long groupId, MessageType messageType,boolean delivered, boolean read) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.groupId = groupId;
        this.messageType = messageType;
        this.delivered = delivered;
        this.read = read;
    }

    public MessageDto(Long id, String content, LocalDateTime timestamp, Long senderId, Long receiverId, Long groupId) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.groupId = groupId;
    }


    public static MessageDto fromEntity(Message message) {
        return new MessageDto(
                message.getId(),
                message.getContent(),
                message.getTimestamp(),
                message.getSender() != null ? message.getSender().getId() : null,
                message.getReceiver() != null ? message.getReceiver().getId() : null,
                message.getGroup() != null ? message.getGroup().getId() : null,
                message.getMessageType(),
                message.isDelivered(),
                message.isRead()
        );
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public MessageType getMessageType() { return messageType; }
    public void setMessageType(MessageType messageType) { this.messageType = messageType; }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
