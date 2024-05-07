package com.example.pageturner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @NotEmpty(message = "title cannot be empty")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String title;

    @NotEmpty(message = "ISBN cannot be empty")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String ISBN;

    @NotEmpty(message = "bookGenre cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String bookGenre;

    @NotEmpty(message = "language cannot be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String language;

    @NotEmpty(message = "description cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;

    @NotEmpty(message = "link cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String linkToAmazon;

    @Column(columnDefinition = "varchar(20)") //check(status='not read' or status='not read' or status='want to read' or status='reading now' or status='finished')
    @Pattern(regexp = "^(not read|want to read|reading now|finished)$")
    private String status;

    @NotNull(message = "number of pages cannot be empty")
    @Column(columnDefinition = "int not null")
    private Integer noOfPages;

    @Column(columnDefinition = "double")
    private Double rate;

    @Column(columnDefinition = "DATE not null")
    private LocalDate publishDate;



    //Relation

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Writer writer;

    @ManyToMany(cascade =CascadeType.ALL ,mappedBy = "books")
    private Set<Genre> genre;

    @ManyToMany

    @JsonIgnore
    private Set<Reader> readers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book")
    private Set<Post>posts;

}
