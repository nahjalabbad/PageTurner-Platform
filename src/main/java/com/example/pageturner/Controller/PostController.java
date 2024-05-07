package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.Post;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;


    @GetMapping("/get-all")
    public ResponseEntity getAllPosts(){
        logger.info("inside get all posts");
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity addPost(@PathVariable Integer bookId, @AuthenticationPrincipal User reader, @RequestBody @Valid Post post){
        logger.info("inside add post");
        postService.addPost(bookId, reader.getUserId(), post);
        return ResponseEntity.ok().body(new ApiResponse("post added successfully!"));
    }

    @PutMapping("/update/{bookId}/{postId}")
    public ResponseEntity updatePost(@PathVariable Integer bookId, @AuthenticationPrincipal User reader, @PathVariable Integer postId, @RequestBody @Valid Post post){
        logger.info("inside update post");
        postService.updatePost(bookId, reader.getUserId(), postId, post);
        return ResponseEntity.ok().body(new ApiResponse("post updated successfully!"));
    }


    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deletePost(@AuthenticationPrincipal User reader,@PathVariable Integer postId){
        logger.info("inside delete post");
        postService.deletePost(reader.getUserId(),postId);
        return ResponseEntity.ok().body(new ApiResponse("post deleted successfully!"));
    }

    //Reenad 1
    @GetMapping("/get-posts-by-book-title/{bookTitle}")
    public ResponseEntity getPostsByBookTitle(@AuthenticationPrincipal User writer, @PathVariable String bookTitle){
        logger.info("inside get posts by book title");
        return ResponseEntity.ok().body(postService.getPostsByBookTitle(writer.getUserId(),bookTitle));
    }


    //Reenad 11
    @GetMapping("/get-post/{readerId}")
    public ResponseEntity getPostsByReader(@AuthenticationPrincipal User reader){
        logger.info("inside get posts by reader");
        return ResponseEntity.ok().body(postService.getPostsByReader(reader.getUserId()));
    }

    //Reenad 12
    @GetMapping("/get-top-rated-post/{bookId}")
    public ResponseEntity getTopRatedPost(@AuthenticationPrincipal User writer,@PathVariable Integer bookId){
        logger.info("inside get top rated post");
        return ResponseEntity.ok().body(postService.getTopRatedPost(writer.getUserId(), bookId));
    }
}
