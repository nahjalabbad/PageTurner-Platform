package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.Meeting;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meeting")
public class MeetingController {
    Logger logger = LoggerFactory.getLogger(MeetingController.class);
    private final MeetingService meetingService;

    @GetMapping("/get-all")
    public ResponseEntity getAllMeetings(){
        logger.info("inside get all meetings!");
        return ResponseEntity.ok(meetingService.getAllMeeting());
    }


    @PostMapping("/add")
    public ResponseEntity addMeeting(@AuthenticationPrincipal User writer, @RequestBody @Valid Meeting meeting){
        logger.info("inside add meeting!");
    meetingService.addMeeting(writer.getUserId(), meeting);
    return ResponseEntity.ok(new ApiResponse("meeting added successfully"));
    }


    @PutMapping("/update/{meetingId}")
    public ResponseEntity updateMeeting(@AuthenticationPrincipal User writer, @PathVariable Integer meetingId, @RequestBody @Valid Meeting meeting){
        logger.info("inside update meeting!");
        meetingService.updateMeeting(writer.getUserId(), meetingId ,meeting);
        return ResponseEntity.ok(new ApiResponse("meeting has been updated"));
    }


    @DeleteMapping("/delete/{meetingId}")
    public ResponseEntity deleteMeeting(@AuthenticationPrincipal User writer, @PathVariable Integer meetingId){
        logger.info("inside delete meeting!");
        meetingService.deleteMeeting(writer.getUserId(), meetingId);
        return ResponseEntity.ok(new ApiResponse("meeting has been deleted"));
    }

    //=============By Abdulrahman ==========================

    @PutMapping("/start-meeting/{meetingId}")
    public ResponseEntity startMeeting(@AuthenticationPrincipal User writer, @PathVariable Integer meetingId){
        logger.info("inside start meeting!");
        meetingService.startMeeting(writer.getUserId(), meetingId);
        return ResponseEntity.ok().body(new ApiResponse("Meeting STARTED!"));
    }

    @PutMapping("/end-meeting/{meetingId}")
    public ResponseEntity endMeeting(@AuthenticationPrincipal User writer, @PathVariable Integer meetingId){
        logger.info("inside end meeting!");
        meetingService.endMeeting(writer.getUserId(), meetingId);
        return ResponseEntity.ok().body(new ApiResponse("Meeting ENDED!"));
    }

    @GetMapping("/get-all-previous-meetings-for-reader")
    public ResponseEntity getAllPreviousMeetingsForReader(@AuthenticationPrincipal User readId){
        logger.info("inside get all previous meetings!");
        return ResponseEntity.ok().body(meetingService.getAllPreviousMeetingsForReader(readId.getUserId()));
    }

    @GetMapping("/get-all-previous-meetings-for-writer")
    public ResponseEntity getAllPreviousMeetingsForWriter(@AuthenticationPrincipal User writer){
        logger.info("inside get all previous meetings!");
        return ResponseEntity.ok().body(meetingService.getAllPreviousMeetingsForWriter(writer.getUserId()));
    }

    @GetMapping("/get-all-upcoming-meetings")
    public ResponseEntity getAllUpcomingMeetings(){
        logger.info("inside get all upcoming meetings!");
        return ResponseEntity.ok().body(meetingService.getAllUpcomingMeetings());
    }

    @GetMapping("/search-meeting-by-id/{meetingId}")
    public ResponseEntity searchForMeetingByMeetingId(@PathVariable Integer meetingId){
        logger.info("inside search meeting by id!");
        return ResponseEntity.ok().body(meetingService.searchForMeetingByMeetingId(meetingId));
    }

    @GetMapping("/search-meeting-by-title/{title}")
    public ResponseEntity searchForMeetingByMeetingTitle(@PathVariable String title){
        logger.info("inside search meeting by title!");
        return ResponseEntity.ok().body(meetingService.searchForMeetingByMeetingTitle(title));
    }

    @GetMapping("/search-meeting-by-book-name/{bookName}")
    public ResponseEntity searchForMeetingByBookName(@PathVariable String bookName){
        logger.info("inside search meeting by book name");
        return ResponseEntity.ok().body(meetingService.searchForMeetingByBookName(bookName));
    }


    @GetMapping("/get-current-meeting/{meetingId}")
    public ResponseEntity getCurrentMeetings(@PathVariable Integer meetingId){
        logger.info("inside get current meeting");
        return ResponseEntity.ok().body(meetingService.getCurrentMeeting(meetingId));
    }


    @GetMapping("/get-current-meetings")
    public ResponseEntity getCurrentMeetings(){
        logger.info("inside get current meeting");
        return ResponseEntity.ok().body(meetingService.getCurrentMeetings());
    }

    @GetMapping("/get-all-my-meetings")
    public ResponseEntity getAllMyMeetings(@AuthenticationPrincipal User writer){
        logger.info("inside get all my meetings!");
        return ResponseEntity.ok().body(meetingService.getAllMyMeetings(writer.getUserId()));
    }
    //===============================================================================

}
