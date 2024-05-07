package com.example.pageturner.Repository;

import com.example.pageturner.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findBookByTitle(String title);
    Book findBookByBookId(Integer genreId);
    @Query("SELECT b.title FROM Book b JOIN b.genre g WHERE g.title = ?1 AND b.rate >= ?2")
    List<String> getBooksByBookGenreAndRateGreaterThanEqual(String genre, Double rate);

    @Query("SELECT b.title FROM Book b WHERE b.language = ?1")
    List<String> getBooksByLanguage(String language);

    @Query("SELECT b.title FROM Book b JOIN b.writer w JOIN w.user u WHERE u.name = ?1 AND u.role = ?2")
    List<String> getBooksByWriterNameAndRole(String author,String role);


    @Query("SELECT b.title FROM Book b JOIN b.writer w JOIN w.user u WHERE u.name = ?1 AND u.role = ?2 AND b.rate >= ?3")
    List<String> getBooksByWriterNameAndRoleAndRate(String author, String role, Double rate);


    @Query("SELECT b.title FROM Book b JOIN b.writer w WHERE w.writerId = ?1")
    List<String> getMyPublishedBooks(Integer writerId);

    @Query("SELECT book.title FROM Book book WHERE book.rate >= ?1")
    List<String> getBookNamesByRateGreaterThanEqual(Double rate);

    @Query("select post.book FROM Post post WHERE post.book.title = ?1")
    List<Book> getBookByPosts(String title);


}
