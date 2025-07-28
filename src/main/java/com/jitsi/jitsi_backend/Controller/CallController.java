package com.jitsi.jitsi_backend.Controller;

import com.jitsi.jitsi_backend.Service.CallService;
import com.jitsi.jitsi_backend.Service.FirebaseMessagingService;
import com.jitsi.jitsi_backend.Service.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/call")
public class CallController {

    private final JwtService jwtService;
    private final CallService callService;
    private final FirebaseMessagingService firebaseMessagingService;
    private final ConcurrentHashMap<String, String> tokenStore;

    public CallController(JwtService jwtService, CallService callService, FirebaseMessagingService firebaseMessagingService, ConcurrentHashMap<String, String> tokenStore) {
        this.jwtService = jwtService;
        this.callService = callService;
        this.firebaseMessagingService = firebaseMessagingService;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/start")
    public Map<String, String> startCall(@RequestBody Map<String, String> body) {
        String callerId = body.get("callerId");
        String calleeId = body.get("calleeId");

        String roomId = "room-" + UUID.randomUUID();
        String token = jwtService.generateToken(callerId, roomId);
        String jitsiUrl = "https://meet.jit.si/" + roomId;

        callService.startCall(callerId, calleeId, roomId);
        String calleeToken = tokenStore.get(calleeId.toLowerCase());

        if (calleeToken != null) {
            try {
                String title = "Yeni çağrı";
                String bodyText = callerId + " seni arıyor";
                firebaseMessagingService.sendNotificationWithLink(calleeToken, title, bodyText, jitsiUrl);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Bildirim gönderilemedi: " + e.getMessage());
            }
        } else {
            System.out.println(calleeId + " için kayıtlı token yok.");
        }

        Map<String, String> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("token", token);
        response.put("jitsiUrl", jitsiUrl);
        return response;
    }


    @PostMapping("/accept")
    public Object acceptCall(@RequestParam String calleeId) {
        var callInfo = callService.acceptCall(calleeId);
        if (callInfo == null) {
            return Map.of("error", "Call not found");
        }

        String token = jwtService.generateToken(calleeId, callInfo.roomId);
        String jitsiUrl = "https://meet.jit.si/" + callInfo.roomId;
        System.out.println("ℹ️ " + callInfo.roomId + " 'ye giriş yapıldı");

        return Map.of("roomId", callInfo.roomId, "jitsiUrl", jitsiUrl);
    }

    @PostMapping("/end")
    public Object endCall(@RequestParam String userId) {
        callService.endCall(userId);
        return Map.of("message", "Call ended");
    }
}
