package com.jitsi.jitsi_backend.Controller;

import com.jitsi.jitsi_backend.Model.Meeting;
import com.jitsi.jitsi_backend.Service.MeetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/meeting")
    public Meeting getMeeting() {
        return meetingService.getCurrentMeeting();
    }
}
