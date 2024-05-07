package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.Book;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.BookService;
import io.micrometer.observation.GlobalObservationConvention;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @GetMapping("/get-all")
    public ResponseEntity getAllBooks(){
        logger.info("inside get all books!");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/add")
    public ResponseEntity addBook(@AuthenticationPrincipal User writer, @RequestBody @Valid Book book){
        logger.info("inside create book via writer!");
        bookService.addBook(writer.getUserId(), book);
        return ResponseEntity.ok(new ApiResponse("new book added"));
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@AuthenticationPrincipal User writer, @PathVariable Integer bookId, @RequestBody @Valid Book book){
        logger.info("inside update book via writer!");
        bookService.updateBook(writer.getUserId(), bookId ,book);
        return ResponseEntity.ok(new ApiResponse("book information has been  updated"));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity deleteBook(@AuthenticationPrincipal User writer, @PathVariable Integer bookId){
        logger.info("inside delete book via writer!");
        bookService.deleteBook(writer.getUserId(), bookId);
        return ResponseEntity.ok(new ApiResponse("book information has been  deleted"));
    }

    //EXTRA
    @GetMapping("/get-genre/{genre}")
    public ResponseEntity getTopByGenre(@PathVariable String genre){
        logger.info("inside get top by genre!");
        return ResponseEntity.ok(bookService.getTopByGenre(genre));
    }

    @GetMapping("/get-author/{author}")
    public ResponseEntity getTopByAuthor( @PathVariable String author){
        logger.info("inside get author!");
        return ResponseEntity.ok(bookService.getTopByAuthor(author));
    }

    @GetMapping("/get-alltime")
    public ResponseEntity getTopAllTime(){
        logger.info("inside get top all time!");
        return ResponseEntity.ok(bookService.getTopAllTime());
    }

    @GetMapping("/get-language/{language}")
    public ResponseEntity getBooksByLanguage(@PathVariable String language){
        logger.info("inside Books by language!");
        return ResponseEntity.ok(bookService.getBooksByLanguage(language));
    }

    @GetMapping("/get-mybooks")
    public ResponseEntity getMyPublishedBooks(@AuthenticationPrincipal User writer){
        logger.info("inside get my published books!");
        return ResponseEntity.ok(bookService.getMyPublishedBooks(writer.getUserId()));
    }

    @GetMapping("/search-name/{bookName}")
    public ResponseEntity searchBookName(@PathVariable String bookName){
        logger.info("inside search book name!");
        return ResponseEntity.ok(bookService.searchBookName(bookName));
    }

    @GetMapping("/get-byauthor/{author}")
    public ResponseEntity getAllByAuthor(@PathVariable String author){
        logger.info("inside get all by author!");
        return ResponseEntity.ok(bookService.getAllByAuthor(author));
    }

    @GetMapping("/get-post/{title}")
    public ResponseEntity getBookPosts(@AuthenticationPrincipal User writer,@PathVariable String title){
        logger.info("inside get book posts!");
        return ResponseEntity.ok(bookService.getBookPosts(writer.getUserId(),title));
    }

}
