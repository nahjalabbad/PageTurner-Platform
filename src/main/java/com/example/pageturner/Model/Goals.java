package com.example.pageturner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goalsID;

    @NotEmpty(message = "title cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @Pattern(regexp = "^(not finished|finished)$")
    @Column(columnDefinition = "varchar(20) ")
    private String status;


    //Relation
    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Reader reader;
}
