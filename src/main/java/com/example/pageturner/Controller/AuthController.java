package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(){
        logger.info("inside login");
        authService.login();
        return ResponseEntity.ok(new ApiResponse("Welcome back."));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        logger.info("inside logout");
        authService.logout();
        return ResponseEntity.ok(new ApiResponse("See you soon."));
    }


    @GetMapping("/get-all")
    public ResponseEntity getAllUsers(){
        logger.info("inside get all users");
        return ResponseEntity.ok(authService.getAllUsers());
    }


}
