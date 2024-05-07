package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.JoinSessionService;
import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/join")
@RequiredArgsConstructor
public class JoinSessionController {

    Logger logger = LoggerFactory.getLogger(JoinSessionController.class);
    private final JoinSessionService joinSessionService;

    @PostMapping("/join-meeting/{meetingId}")
    public ResponseEntity joinMeeting(@PathVariable Integer meetingId, @AuthenticationPrincipal User reader){
        logger.info("inside join meeting");
        joinSessionService.joinMeeting(meetingId, reader.getUserId());
        return ResponseEntity.ok().body(new ApiResponse("reader joined successfully!"));
    }

    @PostMapping("/join-event/{eventId}")
    public ResponseEntity joinEvent(@PathVariable Integer eventId, @AuthenticationPrincipal User reader){
        logger.info("inside join event");
        joinSessionService.joinEvent(eventId, reader.getUserId());
        return ResponseEntity.ok().body(new ApiResponse("reader joined successfully!"));
    }

    @PostMapping("/leave-meeting/{meetingId}")
    public ResponseEntity leaveMeeting(@PathVariable Integer meetingId, @AuthenticationPrincipal User reader){
        logger.info("inside leave meeting");
        joinSessionService.leaveMeeting(meetingId, reader.getUserId());
        return ResponseEntity.ok().body(new ApiResponse("reader leaved successfully!"));
    }

    @PostMapping("/leave-event/{eventId}")
    public ResponseEntity leaveEvent(@PathVariable Integer eventId, @AuthenticationPrincipal User reader){
        logger.info("inside leave event");
        joinSessionService.leaveEvent(eventId, reader.getUserId());
        return ResponseEntity.ok().body(new ApiResponse("reader leaved successfully!"));
    }



    //Reenad 5
    @GetMapping("/get-meeting-code/{meetingId}")
    public ResponseEntity getMeetingCode(@AuthenticationPrincipal User reader,@PathVariable Integer meetingId){
        logger.info("inside get meeting code!");
        return ResponseEntity.ok(joinSessionService.getMeetingCode(reader.getUserId(),meetingId));
    }

    //Reenad 6
    @GetMapping("/get-event-code/{eventId}")
    public ResponseEntity getEventCode(@AuthenticationPrincipal User reader,@PathVariable Integer eventId){
        logger.info("inside get event code!");
        return ResponseEntity.ok(joinSessionService.getEventCode(reader.getUserId(), eventId));
    }





}
