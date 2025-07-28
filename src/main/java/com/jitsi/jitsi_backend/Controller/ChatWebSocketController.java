package com.jitsi.jitsi_backend.Controller;

import com.jitsi.jitsi_backend.Dto.MessageDto;
import com.jitsi.jitsi_backend.Model.MessageWS;
import com.jitsi.jitsi_backend.Service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatWebSocketController {

    private final MessageService messageService;

    public ChatWebSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageWS sendMessage(MessageWS messageWS) {
        System.out.println("Gelen mesaj: " + messageWS.getContent());

        // DB'ye kaydet
        MessageDto dto = new MessageDto();
        dto.setContent(messageWS.getContent());
        dto.setSenderId(Long.parseLong(messageWS.getSenderId()));
        dto.setReceiverId(messageWS.getReceiverId() != null ? Long.parseLong(messageWS.getReceiverId()) : null);
        dto.setGroupId(messageWS.getGroupId() != null ? Long.parseLong(messageWS.getGroupId()) : null);

        messageService.sendMessage(dto);

        return messageWS;
    }
}
