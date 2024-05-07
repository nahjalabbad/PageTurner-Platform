package com.example.pageturner;

import com.example.pageturner.Controller.BookController;
import com.example.pageturner.Model.Book;
import com.example.pageturner.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BookController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class BookControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private List<Book> books;

    @BeforeEach
    void setUp() {
        Book book1 = new Book();
        book1.setBookId(1);
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setBookId(2);
        book2.setTitle("Book 2");

        books = Arrays.asList(book1, book2);
    }


    @Test //test passed!
    public void getAllBooks() throws Exception {
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/v1/book/get-all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"));
    }


    @Test //test passed!
    public void searchBookByName() throws Exception {
        Book book = new Book();
        book.setTitle("The Great Gatsby");
        Mockito.when(bookService.searchBookName("The Great Gatsby")).thenReturn(book);

        mockMvc.perform(get("/api/v1/book/search-name/{bookName}", "The Great Gatsby"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Great Gatsby"));
    }



    @Test //test passed!
    public void getTopBooksOfAllTime() throws Exception {
        List<String> books = Arrays.asList("Book 1", "Book 2");
        Mockito.when(bookService.getTopAllTime()).thenReturn(books);

        mockMvc.perform(get("/api/v1/book/get-alltime"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test//test passed!
    public void getTopByGenre() throws Exception {
        Mockito.when(bookService.getTopByGenre(Mockito.anyString())).thenReturn(Arrays.asList("Book 1", "Book 2"));

        mockMvc.perform(get("/api/v1/book/get-genre/{genre}", "fiction"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("Book 2"));
    }

    @Test //test passed!
    public void getTopByAuthor() throws Exception {
        Mockito.when(bookService.getTopByAuthor(Mockito.anyString())).thenReturn(Arrays.asList("Book 1", "Book 2"));

        mockMvc.perform(get("/api/v1/book/get-author/{author}", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("Book 2"));
    }

}
