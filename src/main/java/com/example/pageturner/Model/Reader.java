package com.example.pageturner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reader {
    @Id
    private Integer readerId;

    //=============By Abdulrahman ==============
    @ManyToMany
    private List<Book> wantToRead;

    @ManyToMany
    private List<Book> doneReading;

    @ManyToMany
    private List<Book> readingNow;

    @ManyToMany
    private List<Book> purchasedBooks;
    //==========================================


    //relations

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reader")
    private Set<Goals> goals;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reader")
    private Set<Post> posts;


    @ManyToMany(cascade =CascadeType.ALL,mappedBy = "readers")
    private Set<Book> books;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reader")
    private Set<Event> event;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Meeting meeting;


    // Maybe works
    @ManyToMany
    @JoinTable(
            name = "reader_following",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> followingUsers = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "reader_favorite_genre",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> favoriteGenre;

}
