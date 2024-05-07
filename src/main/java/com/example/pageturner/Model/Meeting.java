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

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer meetingId;

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

    @NotEmpty(message = "please fill the meeting code!")
    @Column(columnDefinition = "varchar(100) not null")
    private String meetingCode;

    @Column(columnDefinition = " double not null")
    private Double price;

    @Column(columnDefinition = "int")
    private Integer numberOfPeople;

    @Column(columnDefinition = "int")
    private Integer totalNoP;

    //relations

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Writer writer;

    @ManyToMany(cascade =CascadeType.ALL,mappedBy = "meetings")
    private Set<JoinSession> joinSessions;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "meeting")
    private Set<Reader> readers;


}

