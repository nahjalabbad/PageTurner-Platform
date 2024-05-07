package com.example.pageturner.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @NotEmpty(message = "please fill the time read")
    @Column(columnDefinition = "varchar(15) not null")
    private String timeRead;

    @NotNull(message = "please fill the rate")
    @Column(columnDefinition = "double not null")
    private Double rate;

    @NotEmpty(message = "please fill the time comment")
    @Column(columnDefinition = "varchar(255) not null")
    private String comment;

    @NotEmpty(message = "please fill the time note")
    @Column(columnDefinition = "varchar(100) not null")
    private String notes;

    @NotEmpty(message = "please fill the tag")
    @Column(columnDefinition = "varchar(20) not null")
    private String tag;

    //relations:

    //many to one with reader
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Reader reader;

    //many to one with writer
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    @JsonIgnore
    private Book book;


}
