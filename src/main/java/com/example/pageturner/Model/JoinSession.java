package com.example.pageturner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class JoinSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer joinId;

    @Column(columnDefinition = "varchar(100) ")
    private String readerName;

    @Column(columnDefinition = "varchar(100) ")
    @Pattern(regexp = "^(event|meeting)$")
    private String sessionType;

    @Column(columnDefinition = "varchar(100) ")
    private String meetingCode;

    @Column(columnDefinition = "varchar(50)")
    @Pattern(regexp = "^(joined|left)$")
    private String isJoin;





    @ManyToMany
    @JsonIgnore
    private Set<Meeting> meetings;

    @ManyToMany
    @JsonIgnore
    private Set<Event> events;

    @ManyToMany
    @JsonIgnore
    private Set<Reader> readers;

}
