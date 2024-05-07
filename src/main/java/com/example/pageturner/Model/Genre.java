package com.example.pageturner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreID;

    @NotEmpty(message = "title cannot be empty")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String title;


    @ManyToMany
    @JsonIgnore
    private Set<Book> books;

    @ManyToMany
    @JsonIgnore
    private Set<Writer> writers;

    @ManyToMany
    @JsonIgnore
    private Set<Reader>readers;
}
