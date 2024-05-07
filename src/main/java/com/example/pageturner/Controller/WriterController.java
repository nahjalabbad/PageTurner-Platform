package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.DTO.WriterDTO;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.WriterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/writer")
public class WriterController {

    Logger logger = LoggerFactory.getLogger(WriterController.class);
    private final WriterService writerService;

    @GetMapping("/get-all")
    public ResponseEntity getAllWriters(){
        logger.info("inside get all writers");
        return ResponseEntity.ok(writerService.getAllWriters());
    }

    @PostMapping("/add")
    public ResponseEntity registerWriter(@RequestBody @Valid WriterDTO writerDTO){
        logger.info("inside register writer");
        writerService.registerWriter(writerDTO);
        return ResponseEntity.ok(new ApiResponse("Writer has been registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateWriter(@AuthenticationPrincipal User writer, @RequestBody @Valid WriterDTO writerDTO){
        logger.info("inside update writer");
        writerService.updateWriter(writer.getUserId(), writerDTO);
        return ResponseEntity.ok(new ApiResponse("writer information has been updated"));
    }

    @DeleteMapping("/delete/{writerId}")
    public ResponseEntity deleteWriter(@AuthenticationPrincipal User adminId, @PathVariable Integer writerId){
        logger.info("inside delete writer");
        writerService.deleteWriter(adminId.getUserId(),writerId);
        return ResponseEntity.ok(new ApiResponse("writer information has been deleted"));
    }
    //Reenad 2
    @PutMapping("/follow/{followedId}")
    public ResponseEntity writerFollowUser(@AuthenticationPrincipal User writer, @PathVariable Integer followedId){
        logger.info("inside writer follow user");
        writerService.follow(writer.getUserId(), followedId);
        return ResponseEntity.ok(new ApiResponse("writer followed user successfully"));
    }
    //Reenad 3
    @PutMapping("/unfollow/{unfollowedId}")
    public ResponseEntity writerUnFollowUser(@AuthenticationPrincipal User writer, @PathVariable Integer unfollowedId){
        logger.info("inside writer unfollow user");
        writerService.unfollow(writer.getUserId(), unfollowedId);
        return ResponseEntity.ok(new ApiResponse("writer Unfollowed user successfully"));
    }
    //Reenad 4
    @GetMapping("/search-writer/{name}")
    public ResponseEntity searchWriter(@PathVariable String name){
        logger.info("inside search writer");
        return ResponseEntity.ok(writerService.searchWriter(name));
    }
    //Reenad 8
    @PostMapping("/add-published-genre-for-writer/{genre}")
    public ResponseEntity addPublishedGenreForWriter(@AuthenticationPrincipal User writer , @PathVariable String genre){
        logger.info("inside add published genre for writer");
        return ResponseEntity.ok(writerService.addPublishedGenreForWriter(writer.getUserId(),genre));
    }
}
