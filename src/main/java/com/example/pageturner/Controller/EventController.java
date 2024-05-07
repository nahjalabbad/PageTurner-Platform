package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.Event;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    @GetMapping("/get-all")
    public ResponseEntity getAllEvents(){
        logger.info("inside get all events!");
        return ResponseEntity.ok(eventService.getAllEvent());
    }
    @PostMapping("/add")
    public ResponseEntity addEvent(@AuthenticationPrincipal User reader, @RequestBody @Valid Event event){
        logger.info("inside add event!");
        eventService.addEvent(reader.getUserId(), event);
        return ResponseEntity.ok(new ApiResponse("event added successfully"));
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity updateEvent(@AuthenticationPrincipal User reader, @PathVariable Integer eventId, @RequestBody @Valid Event event){
        logger.info("inside update event!");
        eventService.updateEvent(reader.getUserId(), eventId, event);
        return ResponseEntity.ok(new ApiResponse("event has been updated"));
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity deleteEvent(@AuthenticationPrincipal User reader, @PathVariable Integer eventId){
        logger.info("inside delete event!");
        eventService.deleteEvent(reader.getUserId(), eventId);
        return ResponseEntity.ok(new ApiResponse("event has been deleted"));
    }

    //EXTRA

    @PutMapping("/start/{eventId}")
    public ResponseEntity startEvent(@AuthenticationPrincipal User reader, @PathVariable Integer eventId){
        logger.info("inside start event!");
        eventService.startEvent(reader.getUserId(), eventId );
        return ResponseEntity.ok(new ApiResponse("event has started"));
    }

    @PutMapping("/end/{eventId}")
    public ResponseEntity endEvent(@AuthenticationPrincipal User reader, @PathVariable Integer eventId){
        logger.info("inside end event!");
        eventService.endEvent(reader.getUserId(), eventId );
        return ResponseEntity.ok(new ApiResponse("event has ended"));
    }

    @GetMapping("/get-previous")
    public ResponseEntity getPrevious(@AuthenticationPrincipal User reader){
        logger.info("inside get previous event!");
        return ResponseEntity.ok(eventService.getPrevious(reader.getUserId()));
    }

    @GetMapping("/get-upcoming")
    public ResponseEntity getUpComing(@AuthenticationPrincipal User user){
        logger.info("inside getUpComing event!");
        return ResponseEntity.ok(eventService.getUpComing(user.getUserId()));
    }

    @GetMapping("/search-title/{title}")
    public ResponseEntity searchEventbyTitle(@AuthenticationPrincipal User user,@PathVariable String title){
        logger.info("inside searchEventByTitle ");
        return ResponseEntity.ok(eventService.searchEventbyTitle(user.getUserId(), title));
    }

    @GetMapping("/search-name/{bookName}")
    public ResponseEntity searchEventbyBookName(@AuthenticationPrincipal User user,@PathVariable String bookName){
        logger.info("inside SearchEvent By Book Name!");
        return ResponseEntity.ok(eventService.searchEventbyBookName(user.getUserId(), bookName));
    }

    @GetMapping("/get-reader-events")
    public ResponseEntity getAllReaderEvent(@AuthenticationPrincipal User reader){
        logger.info("inside get all reader event!");
        return ResponseEntity.ok(eventService.getAllReaderEvent(reader.getUserId()));
    }

    @GetMapping("/get-current-event/{eventId}")
    public ResponseEntity getCurrentEvent(@AuthenticationPrincipal User user, @PathVariable Integer eventId ){
        logger.info("inside get current event!");
        return ResponseEntity.ok(eventService.getCurrentEvent(user.getUserId(), eventId));
    }

    @GetMapping("/get-all-current")
    public ResponseEntity getCurrentEvent(@AuthenticationPrincipal User user){
        logger.info("inside get all current event!");
        return ResponseEntity.ok(eventService.getAllCurrentEvents(user.getUserId()));
    }

}
