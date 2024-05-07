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
public class Writer {

    @Id
    private Integer writerId;

    @Column(columnDefinition = "varchar(100)")
    private String xAccount;

    @Column(columnDefinition = "varchar(100)")
    private String instagramAccount;

    @Column(columnDefinition = "varchar(100)")
    private String personalWebsite;



    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade =CascadeType.ALL,mappedBy = "writer")
    private Set<Book> books;

    @OneToMany(cascade =CascadeType.ALL,mappedBy = "writer")
    private Set<Meeting> meetings;




    //maybe works
    @ManyToMany
    @JoinTable(
            name = "writer_following",
            joinColumns = @JoinColumn(name = "writer_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> followingUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "writer_published_genre",
            joinColumns = @JoinColumn(name = "writer_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> publishedGenre;
}
