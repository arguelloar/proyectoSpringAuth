package com.araa.project.Controller;


import com.araa.project.Entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {



    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public User meUser(@AuthenticationPrincipal User user){
        return user;
    }
}
