package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.DTO.ReaderDTO;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reader")
public class ReaderController {

    Logger logger = LoggerFactory.getLogger(ReaderController.class);
    private final ReaderService readerService;

    @GetMapping("/get-all")
    public ResponseEntity getAllReaders(){
        logger.info("inside get all reader!");
        return ResponseEntity.ok(readerService.getAllReader());
    }

    @PostMapping("/add")
    public ResponseEntity registerReader(@RequestBody @Valid ReaderDTO readerDTO){
        logger.info("inside register reader");
        readerService.registerReader(readerDTO);
        return ResponseEntity.ok(new ApiResponse("reader registered successfully"));
    }


    @PutMapping("/update")
    public ResponseEntity updateReader(@AuthenticationPrincipal User reader, @RequestBody @Valid ReaderDTO readerDTO){
        logger.info("inside update reader");
        readerService.updateReader(reader.getUserId(), readerDTO);
        return ResponseEntity.ok(new ApiResponse("reader information has updated"));
    }

    @DeleteMapping("/delete/{readerId}")
    public ResponseEntity deleteReader(@AuthenticationPrincipal User adminId, @PathVariable Integer readerId){
        logger.info("inside delete reader");
        readerService.deleteReader(adminId.getUserId(),readerId);
        return ResponseEntity.ok(new ApiResponse("reader information has been deleted"));
    }

    //Reenad 9
    @PostMapping("/add-favorite/{genre}")
    public ResponseEntity addFavoriteGenreForReader(@AuthenticationPrincipal User reader, @PathVariable String genre){
        logger.info("inside add favorite genre for reader!");
        return ResponseEntity.ok(readerService.addFavoriteGenreForReader(reader.getUserId(),genre));
    }




    //================By Abdulrahman =========================================

    @PutMapping("/follow/{userId}")
    public ResponseEntity follow(@AuthenticationPrincipal User reader , @PathVariable Integer userId){
        readerService.follow(reader.getUserId(), userId);
        return ResponseEntity.ok().body(new ApiResponse("Following Success"));
    }
    @PutMapping("/unfollow/{userId}")
    public ResponseEntity unfollow(@AuthenticationPrincipal User reader , @PathVariable Integer userId){
        readerService.unfollow(reader.getUserId(), userId);
        return ResponseEntity.ok().body(new ApiResponse("UnFollowing Success"));
    }

    @PostMapping("/add-to-purchases/{bookId}")
    public ResponseEntity addToPurchasedBooks(@AuthenticationPrincipal User reader, @PathVariable Integer bookId){
        readerService.addToPurchasedBooks(reader.getUserId(), bookId);
        return ResponseEntity.ok().body(new ApiResponse("Book Added to Purcheses Successfully!"));
    }

    @GetMapping("/get-purchased-books")
    public ResponseEntity getPurchasedBooks(@AuthenticationPrincipal User reader){
        return ResponseEntity.ok().body(readerService.getPurchasedBooks(reader.getUserId()));
    }

    @PostMapping("/add-want-to-read/{bookId}")
    public ResponseEntity wantToRead(@AuthenticationPrincipal User reader, @PathVariable Integer bookId){
        readerService.addToWantToRead(reader.getUserId(), bookId);
        return ResponseEntity.ok().body(new ApiResponse("book added to want to read list successfully!"));
    }



    @GetMapping("/get-want-to-read")
    public ResponseEntity getAllWantToRead(@AuthenticationPrincipal User reader){
        return ResponseEntity.ok().body(readerService.getWantToRead(reader.getUserId()));
    }


    @PostMapping("/done-reading/{bookId}")
    public ResponseEntity doneReading(@AuthenticationPrincipal User reader, @PathVariable Integer bookId){
        readerService.doneReading(reader.getUserId(), bookId);
        return ResponseEntity.ok().body("Done Reading!");
    }




    @GetMapping("/get-done-reading")
    public ResponseEntity getDoneReading(@AuthenticationPrincipal User reader){
        return ResponseEntity.ok().body(readerService.getDoneReading(reader.getUserId()));
    }

    @PostMapping("/start-reading/{bookId}")
    public ResponseEntity startReading(@AuthenticationPrincipal User reader, @PathVariable Integer bookId){
        readerService.startReading(reader.getUserId(), bookId);
        return ResponseEntity.ok().body("Start Reading!");
    }

    @GetMapping("/get-all-reading-now")
    public ResponseEntity getAllBooksReadingNow(@AuthenticationPrincipal User reader){
        return ResponseEntity.ok().body(readerService.getAllBooksReadingNow(reader.getUserId()));
    }

//===========================================================================================================

}
