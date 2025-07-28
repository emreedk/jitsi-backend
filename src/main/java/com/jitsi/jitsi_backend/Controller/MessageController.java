package com.jitsi.jitsi_backend.Controller;

import com.jitsi.jitsi_backend.Dto.MessageDto;
import com.jitsi.jitsi_backend.Service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto dto) {
        MessageDto saved = messageService.sendMessage(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageDto>> getMessagesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesForUser(userId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<MessageDto>> getMessagesForGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(messageService.getMessagesForGroup(groupId));
    }

    // Mesajı tek tek okundu olarak işaretleme
    @PatchMapping("/{id}/read")
    public ResponseEntity<MessageDto> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.markAsRead(id));
    }

    // Kullanıcının tüm mesajlarını okundu olarak işaretleme
    @PatchMapping("/user/{userId}/read")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long userId) {
        messageService.markAllAsRead(userId);
        return ResponseEntity.ok("Tüm mesajlar okundu olarak işaretlendi.");
    }

    // Kullanıcının tüm mesajlarını delivered olarak işaretleme
    @PatchMapping("/user/{userId}/delivered")
    public ResponseEntity<String> markAllAsDelivered(@PathVariable Long userId) {
        messageService.markAllAsDelivered(userId);
        return ResponseEntity.ok("Tüm mesajlar delivered olarak işaretlendi.");
    }
}
