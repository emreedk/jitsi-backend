package com.jitsi.jitsi_backend.Entity;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String username;

    @Column
    private String profileImageUrl;

    @Column
    private String fcmToken;

    @Column(name = "is_online")
    private boolean isOnline = false;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    public User() {}

    public User(String phoneNumber, String username, String profileImageUrl, String fcmToken) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.fcmToken = fcmToken;
        this.isOnline = false;
        this.lastSeen = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }

    public boolean getIsOnline() { return isOnline; }
    public void setIsOnline(boolean isOnline) { this.isOnline = isOnline; }

    public LocalDateTime getLastSeen() { return lastSeen; }
    public void setLastSeen(LocalDateTime lastSeen) { this.lastSeen = lastSeen; }
}
