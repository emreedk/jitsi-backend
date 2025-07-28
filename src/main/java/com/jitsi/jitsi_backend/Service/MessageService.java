package com.jitsi.jitsi_backend.Service;

import com.jitsi.jitsi_backend.Dto.MessageDto;
import com.jitsi.jitsi_backend.Entity.Group;
import com.jitsi.jitsi_backend.Entity.Message;
import com.jitsi.jitsi_backend.Entity.User;
import com.jitsi.jitsi_backend.Enum.MessageType;
import com.jitsi.jitsi_backend.Repository.GroupRepository;
import com.jitsi.jitsi_backend.Repository.MessageRepository;
import com.jitsi.jitsi_backend.Repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, GroupRepository groupRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public MessageDto sendMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());

        MessageType detectedType = detectMessageType(messageDto.getContent());
        message.setMessageType(detectedType);

        if (messageDto.getGroupId() != null) {
            //Grup mesajı
            Group group = groupRepository.findById(messageDto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Grup bulunamadı: " + messageDto.getGroupId()));
            message.setGroup(group);
            message.setSender(userRepository.findById(messageDto.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Gönderen kullanıcı bulunamadı")));
        } else {
            //Birebir mesaj
            User sender = userRepository.findById(messageDto.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Gönderen kullanıcı bulunamadı"));
            User receiver = userRepository.findById(messageDto.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("Alıcı kullanıcı bulunamadı"));
            message.setSender(sender);
            message.setReceiver(receiver);
        }
        message.setDelivered(true);
        Message savedMessage = messageRepository.save(message);
        MessageDto savedDto = MessageDto.fromEntity(savedMessage);

        //WebSocket yayınını tetikle
        if (savedDto.getGroupId() != null) {
            //Grup mesajı → Grup kanalına yayınla
            messagingTemplate.convertAndSend("/topic/group/" + savedDto.getGroupId(), savedDto);
        } else {
            //Birebir mesaj → Mevcut kanal
            messagingTemplate.convertAndSend("/topic/messages", savedDto);
        }

        return savedDto;
    }

    //  Mesajı okundu olarak işaretleme
    public MessageDto markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı: " + messageId));

        message.setRead(true);
        return MessageDto.fromEntity(messageRepository.save(message));
    }

    //  Kullanıcının tüm okunmamış mesajlarını okundu olarak işaretleme
    public void markAllAsRead(Long userId) {
        List<Message> unreadMessages = messageRepository.findByReceiverIdAndReadFalse(userId);
        for (Message msg : unreadMessages) {
            msg.setRead(true);
        }
        messageRepository.saveAll(unreadMessages);
    }

    //  Kullanıcının iletilmemiş mesajlarını delivered = true yapma
    public void markAllAsDelivered(Long userId) {
        List<Message> undeliveredMessages = messageRepository.findByReceiverIdAndDeliveredFalse(userId);
        for (Message msg : undeliveredMessages) {
            msg.setDelivered(true);
        }
        messageRepository.saveAll(undeliveredMessages);
    }

    private MessageType detectMessageType(String content) {
        if (content == null || !content.contains(".")) {
            System.out.println("İçerikte uzantı bulunamadı, varsayılan TEXT olarak ayarlandı.");
            return MessageType.TEXT;
        }

        String extension = content.substring(content.lastIndexOf(".") + 1).toLowerCase();
        System.out.println("Algılanan uzantı: " + extension);

        return switch (extension) {
            case "jpg", "jpeg", "png", "gif" -> MessageType.IMAGE;
            case "mp4", "mov", "avi" -> MessageType.VIDEO;
            case "mp3", "wav", "ogg" -> MessageType.AUDIO;
            case "pdf", "docx", "xlsx", "pptx" -> MessageType.FILE;
            default -> MessageType.TEXT;
        };
    }

    public List<MessageDto> getMessagesForUser(Long userId) {
        return messageRepository.findAllByUserId(userId)
                .stream()
                .map(MessageDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MessageDto> getMessagesForGroup(Long groupId) {
        return messageRepository.findByGroupId(groupId)
                .stream()
                .map(MessageDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(MessageDto::fromEntity)
                .collect(Collectors.toList());
    }


}
