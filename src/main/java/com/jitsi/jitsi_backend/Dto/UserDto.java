package com.jitsi.jitsi_backend.Dto;

import com.jitsi.jitsi_backend.Entity.User;

import java.time.LocalDateTime;


public class UserDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String profileImageUrl;
    private String fcmToken;
    private boolean isOnline;
    private LocalDateTime lastSeen;

    public UserDto() {}

    public UserDto(Long id, String username, String phoneNumber, String profileImageUrl, String fcmToken, boolean isOnline, LocalDateTime lastSeen) {
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.fcmToken = fcmToken;
        this.isOnline = isOnline;
        this.lastSeen = lastSeen;
    }

    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getProfileImageUrl(),
                user.getFcmToken(),
                user.getIsOnline(),
                user.getLastSeen()
        );
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPhoneNumber(this.phoneNumber);
        user.setProfileImageUrl(this.profileImageUrl);
        user.setFcmToken(this.fcmToken);
        user.setIsOnline(this.isOnline);
        user.setLastSeen(this.lastSeen);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }
}
