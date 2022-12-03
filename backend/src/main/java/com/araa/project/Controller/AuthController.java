package com.araa.project.Controller;


import com.araa.project.DTO.UserDTO;
import com.araa.project.Entity.RefreshToken;
import com.araa.project.Entity.Role;
import com.araa.project.Entity.User;
import com.araa.project.Helper.CookieBuilder;
import com.araa.project.Helper.Validator;
import com.araa.project.Repository.RefreshTokenRepository;
import com.araa.project.Helper.JwtHelper;
import com.araa.project.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.regex.Pattern;

import static com.araa.project.Helper.CookieBuilder.cookieBuilder;
import static com.araa.project.Helper.CookieBuilder.deleteCookie;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CookieBuilder cookieBuilder;

    @Autowired
    BCryptPasswordEncoder encoder;


    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody UserDTO userDTO,
                                   HttpServletRequest request,
                                   HttpServletResponse response){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

         User user = (User)authentication.getPrincipal();
        refreshTokenRepository.deleteById(user.getId());
        RefreshToken refreshToken = new RefreshToken();
        String rToken = jwtHelper.generateRefreshToken(user,refreshToken);
        refreshToken.setOwner(user);
        refreshToken.setRefreshToken(rToken);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(user);

        response.addCookie(cookieBuilder(user,accessToken,rToken));
        return ResponseEntity.ok("Logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request,
                                   HttpServletResponse response,
                                    @AuthenticationPrincipal User user){

        refreshTokenRepository.deleteById(user.getId());
        response.addCookie(deleteCookie());
        return ResponseEntity.ok("User logout");
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        User user = new User();
        if(Validator.emailPattern(userDTO.getEmail())
                && Validator.pwPattern(userDTO.getPassword())){
            user.setEmail(userDTO.getEmail());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setRoles(Arrays.asList(new Role("ROLE_USER")));
            userService.save(user);

            RefreshToken refreshToken = new RefreshToken();
            String rToken = jwtHelper.generateRefreshToken(user,refreshToken);
            refreshToken.setOwner(user);
            refreshToken.setRefreshToken(rToken);
            refreshTokenRepository.save(refreshToken);

            String accessToken = jwtHelper.generateAccessToken(user);


            response.addCookie(cookieBuilder(user,accessToken,rToken));
            return ResponseEntity.ok("User registered");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect email or password format");
    }

    @Transactional
    @PostMapping("/register/super/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO userDTO,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_ADMIN"),new Role("ROLE_USER")));
        userService.save(user);

        RefreshToken refreshToken = new RefreshToken();
        String rToken = jwtHelper.generateRefreshToken(user,refreshToken);
        refreshToken.setOwner(user);
        refreshToken.setRefreshToken(rToken);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(user);

        response.addCookie(cookieBuilder(user,accessToken,rToken));
        return ResponseEntity.ok("Admin registered");
    }

}
