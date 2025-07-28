package com.jitsi.jitsi_backend.Controller;

import com.jitsi.jitsi_backend.Dto.UserDto;
import com.jitsi.jitsi_backend.Service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    //Kullanıcının FCM token'ını kaydet
    @PostMapping("/register-token")
    public ResponseEntity<String> registerToken(@RequestBody UserDto userDto) {
        notificationService.registerToken(userDto);
        return ResponseEntity.ok("Token kaydedildi");
    }

    //Kullanıcıya bildirim gönder
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody UserDto userDto) {
        boolean sent = notificationService.sendNotification(userDto.getPhoneNumber(), "Test Bildirimi", "Bu bir testtir.");
        if (sent) {
            return ResponseEntity.ok("Bildirim gönderildi");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bildirim gönderilemedi");
        }
    }

}














//package com.jitsi.jitsi_backend.Controller;
//
//import com.jitsi.jitsi_backend.Service.FirebaseMessagingService;
//import com.jitsi.jitsi_backend.Model.TokenRequest;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//@RestController
//@RequestMapping("/notification")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
//public class NotificationController {
//
//    private final FirebaseMessagingService firebaseMessagingService;
//
//    // Basit hafıza içi token deposu (userId → token)
//    private final ConcurrentHashMap<String, String> tokenStore;
//
//    public NotificationController(FirebaseMessagingService firebaseMessagingService, ConcurrentHashMap<String, String> tokenStore) {
//        this.firebaseMessagingService = firebaseMessagingService;
//        this.tokenStore = tokenStore;
//    }
//
//    // Mevcut bildirim gönderme endpoint'i
//    @PostMapping("/send")
//    public String sendNotification(@RequestParam String token,
//                                   @RequestParam String title,
//                                   @RequestParam String body) {
//        try {
//            return firebaseMessagingService.sendNotification(token, title, body);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Notification sending failed: " + e.getMessage();
//        }
//    }
//
//    // Yeni: Token kaydetme endpoint'i
//    @PostMapping("/register-token")
//    public String registerToken(@RequestBody TokenRequest request) {
//        String userIdKey = request.getUserId().toLowerCase();
//        tokenStore.put(userIdKey, request.getToken());
//        System.out.printf("✅ Token kaydedildi: userId=%s, token=%s%n", userIdKey, request.getToken());
//        return "Token kaydedildi";
//    }
//
//
//    // Opsiyonel: Token sorgulama endpoint'i
//    @GetMapping("/token/{userId}")
//    public String getToken(@PathVariable String userId) {
//        return tokenStore.getOrDefault(userId, "Token bulunamadı");
//    }
//
//    // Jitsi linkli bildirim gönderme endpoint'i
//    @PostMapping("/send-with-link")
//    public String sendNotificationWithLink(@RequestParam String userId,
//                                           @RequestParam String title,
//                                           @RequestParam String body,
//                                           @RequestParam String clickAction) {
//        try {
//            String token = tokenStore.get(userId);
//            if (token == null) {
//                return "Kullanıcıya ait token bulunamadı: " + userId;
//            }
//            return firebaseMessagingService.sendNotificationWithLink(token, title, body, clickAction);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Notification with link failed: " + e.getMessage();
//        }
//    }
//
//}
