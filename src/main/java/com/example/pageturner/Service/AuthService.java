package com.example.pageturner.Service;

import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final MyUserDetailsService myUserDetailsService;

    public List<User> getAllUsers(){
        return authRepository.findAll();
    }


    public void login(){
    }

    public void logout(){
    }

}
