package com.example.pageturner.Service;

import com.example.pageturner.Api.ApiException;
import com.example.pageturner.Model.Book;
import com.example.pageturner.Model.Genre;
import com.example.pageturner.Model.User;
import com.example.pageturner.Model.Writer;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.BookRepository;
import com.example.pageturner.Repository.GenreRepository;
import com.example.pageturner.Repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final GenreRepository genreRepository;
    private final AuthRepository authRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public void addBook(Integer writerID, Book book) {
        User writer = authRepository.findUserByUserId(writerID);
        Writer writer1=writerRepository.findWriterByWriterId(writerID);
        Genre genre = genreRepository.findGenreByTitle(book.getBookGenre());

        if (genre == null) {
            throw new ApiException("Genre does not exist in our system");
        }

        book.setWriter(writer1);


        if (book.getGenre() == null) {
            book.setGenre(new HashSet<>());
        }

        book.getGenre().add(genre);

        genre.getBooks().add(book);

        bookRepository.save(book);
        genreRepository.save(genre);
    }


    public void updateBook( Integer writerID, Integer bookId, Book book){
        Book book1=bookRepository.findBookByBookId(bookId);
        if (!book1.getWriter().getWriterId().equals(writerID)) {
            throw new ApiException("Writer doesnt match the one in our system");
        }
        book1.setTitle(book.getTitle());
        book1.setPublishDate(book.getPublishDate());
        book1.setNoOfPages(book.getNoOfPages());
        book1.setISBN(book.getISBN());
        book1.setDescription(book.getDescription());
        book1.setLanguage(book.getLanguage());
        book1.setLinkToAmazon(book.getLinkToAmazon());


        bookRepository.save(book1);
    }

    public void deleteBook(Integer writerID, Integer bookId) {
        Book book = bookRepository.findBookByBookId(bookId);
        if (book == null) {
            throw new ApiException("Book not found");
        }
        if (!book.getWriter().getWriterId().equals(writerID)) {
            throw new ApiException("Writer doesn't match the one in our system");
        }

        for (Genre genre : book.getGenre()) {
            genre.getBooks().remove(book);
        }
        book.getGenre().clear();

        bookRepository.delete(book);
    }


    //EXTRA

    //1 Nahj
    public List<String> getTopByGenre(String genre){
        List<String> books = bookRepository.getBooksByBookGenreAndRateGreaterThanEqual(genre,4.0);
        if (books.isEmpty()){
            throw new ApiException("Genre does not exist or no books found with a rate of 4.0 or higher");
        }
        return books;
    }

    //2 Nahj
    public List<String> getTopByAuthor(String author){
        List<String > books = bookRepository.getBooksByWriterNameAndRoleAndRate(author, "WRITER",4.0);
        if (books.isEmpty()){
            throw new ApiException("Author does not exist or no books found with a rate of 4.0 or higher");
        }
        return books;
    }

    //3 Nahj
    public List<String> getTopAllTime(){
        List<String>books=bookRepository.getBookNamesByRateGreaterThanEqual(3.5);
        if (books.isEmpty()){
            throw new ApiException("Sorry there is no match for what you searched for");
        }
        return books;
    }

    //4 Nahj
    public List<String> getMyPublishedBooks(Integer writerId){
        List<String>books=bookRepository.getMyPublishedBooks(writerId);
        if (books.isEmpty()){
            throw new ApiException("You have not published any book in our website yet");
        }
        return books;
    }

    //5 Nahj
    public List<String> getBooksByLanguage(String language){
        List<String>books=bookRepository.getBooksByLanguage(language);
        if (books.isEmpty()){
            throw new ApiException("Sorry there is no match for what you searched for");
        }
        return books;
    }

    //6 Nahj
    public Book searchBookName(String title){
        Book book=bookRepository.findBookByTitle(title);
        if (book==null){
            throw new ApiException("No book exist with title");
        }
        return book;
    }

    //7 Nahj
    public List<String> getAllByAuthor(String author){
        List<String>byAuthor=bookRepository.getBooksByWriterNameAndRole(author,"WRITER");
        if (byAuthor.isEmpty()){
            throw new ApiException("Author does not exist");
        }
        return byAuthor;
    }

    //8 Nahj
    public List<Book> getBookPosts(Integer writerId,String title){
        List<Book>posts=bookRepository.getBookByPosts(title);
        if (posts.isEmpty()){
            throw new ApiException("Title does not exist");
        }
        return posts;
    }

}
