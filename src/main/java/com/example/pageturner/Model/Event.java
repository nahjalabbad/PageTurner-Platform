package com.example.pageturner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;


    @Column(columnDefinition = " varchar(20)")
    private String host;

    @NotEmpty(message ="title must not be empty" )
    @Column(columnDefinition = " varchar(100) not null ")
    private String title;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime startTime;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime endTime;

    @Pattern(regexp = "^(not started|active|ended)$")
    @Column(columnDefinition = " varchar(20)")
    private String status;

    @NotEmpty(message ="description must not be empty" )
    @Column(columnDefinition = " varchar(200) not null ")
    private String description;

    @NotEmpty(message ="book name must not be empty" )
    @Column(columnDefinition = " varchar(20) not null ")
    private String bookName;

    @NotEmpty(message ="author must not be empty" )
    @Column(columnDefinition = " varchar(20) not null ")
    private String author;

    @Column(columnDefinition = " varchar(20)  ")
    private String chapter;

    @NotEmpty(message ="group type must not be empty" )
    @Pattern(regexp = "^(public|private|ended)$")
    @Column(columnDefinition = " varchar(20) not null ")
    private String groupType;

    @NotEmpty(message = "please fill the event code!")
    @Column(columnDefinition = "varchar(100) not null")
    private String eventCode;

    @Column(columnDefinition = "int")
    private Integer numberOfPeople;

    @Column(columnDefinition = "int")
    private Integer totalNoP;

    //relations


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Reader reader;


    @ManyToMany(cascade =CascadeType.ALL,mappedBy = "events")
    private Set<JoinSession> joinSessions;




}
