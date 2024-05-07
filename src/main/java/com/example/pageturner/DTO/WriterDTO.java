package com.example.pageturner.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WriterDTO {

    @NotEmpty(message = "please fill the name")
    private String name;

    @NotEmpty(message = "please fill the username")
    @Size(min = 4)
    private String username;

    @NotEmpty(message = "please fill the password")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d_@$%!]{8,20}$")
    private String password;

    @NotEmpty(message = "please fill the phone number")
    @Pattern(regexp = "^(05)[0-9]+$")
    private String phoneNumber;

    @NotEmpty(message = "please fill the email")
    @Email
    private String email;

    @NotEmpty(message = "please fill the bio")
    private String bio;

    private Integer following;

    private Integer followers;

    private Integer bookNumber;

    private String xAccount;

    private String instagramAccount;

    private String personalWebsite;

    private String publishedGenre;


}
