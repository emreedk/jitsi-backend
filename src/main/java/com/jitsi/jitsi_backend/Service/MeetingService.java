package com.jitsi.jitsi_backend.Service;

import com.jitsi.jitsi_backend.Model.Meeting;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MeetingService {
    public Meeting getCurrentMeeting() {
        String roomId = UUID.randomUUID().toString().substring(0, 8);
        String meetingUrl = "https://meet.jit.si/" + roomId;
        return new Meeting(roomId, meetingUrl);
    }

}
