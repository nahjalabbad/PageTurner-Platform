package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.Genre;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreController {

    Logger logger = LoggerFactory.getLogger(GenreController.class);
    private final GenreService genreService;

    @GetMapping("/get-all")
    public ResponseEntity getAllGenres(){
        logger.info("inside get all genres");
        return ResponseEntity.ok(genreService.getAllGenres());
    }


    @PostMapping("/add")
    public ResponseEntity addGenre(@AuthenticationPrincipal User admin, @RequestBody @Valid Genre genre){
        logger.info("inside add genre");
        genreService.addGenre(admin.getUserId(), genre);
        return ResponseEntity.ok(new ApiResponse("new genre added"));
    }

    @PutMapping("/update/{genreId}")
    public ResponseEntity updateGenre(@AuthenticationPrincipal User adminId, @PathVariable Integer genreId, @RequestBody @Valid Genre genre){
        logger.info("inside update genre");
        genreService.updateGenre(adminId.getUserId(), genreId ,genre);
        return ResponseEntity.ok(new ApiResponse("genre has been updated"));
    }

    @DeleteMapping("/delete/{genreId}")
    public ResponseEntity deleteGenre(@AuthenticationPrincipal User adminId,@PathVariable Integer genreId){
        logger.info("inside delete genre");
        genreService.deleteGenre(adminId.getUserId(), genreId);
        return ResponseEntity.ok(new ApiResponse("genre has been deleted"));
    }

    //Reenad 7
    @GetMapping("/get-books-by-genre/{genreTitle}")
    public ResponseEntity getBooksByGenre(@PathVariable String genreTitle){
        logger.info("inside get books by genre");
        return ResponseEntity.ok(genreService.getBooksByGenre(genreTitle));
    }
}
