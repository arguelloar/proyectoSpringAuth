package com.araa.project.Controller;


import com.araa.project.Entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user){
        if(user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized");
        }
        return ResponseEntity.ok(user);
    }
}
