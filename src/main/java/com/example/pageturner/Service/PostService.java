package com.example.pageturner.Service;

import com.example.pageturner.Api.ApiException;
import com.example.pageturner.Model.*;

import com.example.pageturner.Repository.BookRepository;
import com.example.pageturner.Repository.PostRepository;
import com.example.pageturner.Repository.ReaderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;


    //getAllPosts
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    //addPost
    public void addPost(Integer bookId, Integer readerId, Post post){
        Book book = bookRepository.findBookByBookId(bookId);
        Reader reader = readerRepository.findReaderByReaderId(readerId);

        if (book == null){
            throw new ApiException("Book isn't found!");
        }
        if (reader == null){
            throw new ApiException("Reader isn't found!");
        }

        post.setBook(book);
        post.setReader(reader);

        postRepository.save(post);

        Double averageRating = postRepository.getAverageRatingForBook(bookId);

        if (averageRating == null) {
            averageRating = 0.0;
        }

        book.setRate(averageRating);

        bookRepository.save(book);
    }



    //updatePost
    public void updatePost(Integer bookId, Integer readerId, Integer postId, Post newPost){
        Book book = bookRepository.findBookByBookId(bookId);
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        Post post = postRepository.findPostByPostId(postId);
        if (book==null){
            throw new ApiException("book not found!");
        }
        if (reader==null){
            throw new ApiException("reader not found!");
        }
        if (post==null){
            throw new ApiException("post not found!");
        }
        post.setNotes(newPost.getNotes());
        post.setComment(newPost.getComment());
        post.setTimeRead(newPost.getTimeRead());
        post.setRate(newPost.getRate());
        postRepository.save(post);
    }
    //deletePost
    public void deletePost(Integer readerId,Integer postId){
        Post post = postRepository.findPostByPostId(postId);
        if (post == null){
            throw new ApiException("post not found!");
        }
        postRepository.delete(post);
    }



    //37 Reenad
    public List<Post> getPostsByBookTitle(Integer writerId, String bookTitle){
        Book book = bookRepository.findBookByTitle(bookTitle);
        if (book == null){
            throw new ApiException("book not found!");
        }
        if (!book.getWriter().getWriterId().equals(writerId)){
            throw new ApiException("book not found");
        }
        return postRepository.findPostByBookTitle(bookTitle);
    }


    //38 Reenad
    public List<Post> getPostsByReader(Integer readerId){
        List<Post> readerPosts = postRepository.getPostsByReaderId(readerId);
        if (readerPosts.isEmpty()){
            throw new ApiException("you have not posted anything yet");
        }
        return readerPosts;

    }

    //39 Reenad
    public List<Post> getTopRatedPost(Integer writerId, Integer bookId){
        Book book=bookRepository.findBookByBookId(bookId);
        if (book == null){
            throw new ApiException("book not found!");
        }
        return postRepository.findTop5ByBookBookIdOrderByRateDesc(bookId);
    }


}
