package com.jitsi.jitsi_backend.Service;

import com.jitsi.jitsi_backend.Dto.UserDto;
import com.jitsi.jitsi_backend.Entity.User;
import com.jitsi.jitsi_backend.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    public NotificationService(UserRepository userRepository,
                               FirebaseMessagingService firebaseMessagingService) {
        this.userRepository = userRepository;
        this.firebaseMessagingService = firebaseMessagingService;
    }


     //Kullanıcının FCM token'ını DB'ye kaydeder veya günceller.

    public void registerToken(UserDto userDto) {
        String phoneNumber = userDto.getPhoneNumber();
        String fcmToken = userDto.getFcmToken();

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + phoneNumber));

        user.setFcmToken(fcmToken);
        userRepository.save(user);

        System.out.printf("Token kaydedildi: phoneNumber=%s, token=%s%n", phoneNumber, fcmToken);
    }


     //Kullanıcının FCM token'ı ile bildirim gönderir.

    public boolean sendNotification(String phoneNumber, String title, String body) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + phoneNumber));

        String fcmToken = user.getFcmToken();
        if (fcmToken == null || fcmToken.isEmpty()) {
            System.out.println("Kullanıcının FCM token'ı yok.");
            return false;
        }

        try {
            firebaseMessagingService.sendNotification(fcmToken, title, body);
            System.out.println("Bildirim gönderildi: " + title);
            return true;
        } catch (Exception e) {
            System.out.println("Bildirim gönderme hatası: " + e.getMessage());
            return false;
        }
    }
}
