package com.example.pageturner.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;

    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    @Column(columnDefinition = "varchar(10) check(role='WRITER' or role='READER' or role='ADMIN')")
    private String role;

    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @Column(columnDefinition = "varchar(255)")
    private String bio;

    @Column(columnDefinition = "int")
    private Integer following;

    @Column(columnDefinition = "int")
    private Integer followers;

    @Column(columnDefinition = "int")
    private Integer bookNumber;

    @Column(columnDefinition = "varchar(50)")
    private String isJoin;


    //relations:

    //one to one with writer
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Writer writer;

    //one to one with reader
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Reader reader;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
