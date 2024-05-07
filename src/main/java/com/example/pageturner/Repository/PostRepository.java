package com.example.pageturner.Repository;

import com.example.pageturner.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findPostByPostId(Integer postId);
    @Query("SELECT AVG(p.rate) FROM Post p WHERE p.book.bookId = ?1")
    Double getAverageRatingForBook(Integer bookId);
    List<Post> findPostByBookTitle(String booktitle);
    List<Post> findTop5ByBookBookIdOrderByRateDesc(Integer bookId);

    @Query("select post from Post post where post.reader.readerId = ?1")
    List<Post> getPostsByReaderId(Integer readerId);




}
