package com.araa.project.Controller;


import com.araa.project.DTO.UserDTO;
import com.araa.project.Entity.RefreshToken;
import com.araa.project.Entity.Role;
import com.araa.project.Entity.User;
import com.araa.project.Exception.EmailAlreadyRegisteredException;
import com.araa.project.Exception.IncorrectFormatException;
import com.araa.project.Helper.CookieHelper;
import com.araa.project.Helper.JwtHelper;
import com.araa.project.Helper.Validator;
import com.araa.project.Repository.RefreshTokenRepository;
import com.araa.project.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static com.araa.project.Helper.CookieHelper.cookieBuilder;
import static com.araa.project.Helper.CookieHelper.deleteCookie;

@Log4j2
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
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
    private CookieHelper cookieHelper;

    @Autowired
    BCryptPasswordEncoder encoder;


    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody UserDTO userDTO,
                                   HttpServletRequest requvest,
                                   HttpServletResponse response,
                                   @AuthenticationPrincipal User user) {

        if(user != null){
            return ResponseEntity.ok("Already logged in");
        }else {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User userData = (User) authentication.getPrincipal();
            refreshTokenRepository.deleteById(userData.getId());
            RefreshToken refreshToken = new RefreshToken();
            String rToken = jwtHelper.generateRefreshToken(userData, refreshToken);
            refreshToken.setOwner(userData);
            refreshToken.setRefreshToken(rToken);
            refreshTokenRepository.save(refreshToken);

            String accessToken = jwtHelper.generateAccessToken(userData);

            response.addCookie(cookieBuilder(userData, accessToken, rToken));
            return ResponseEntity.ok("Logged in");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @AuthenticationPrincipal User user) {

        refreshTokenRepository.deleteById(user.getId());
        response.addCookie(deleteCookie());
        return ResponseEntity.ok("User logout");
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws EmailAlreadyRegisteredException {

        User user = new User();

        if (Validator.emailPattern(userDTO.email())
                && Validator.pwPattern(userDTO.password())) {
            user.setEmail(userDTO.email());
            user.setPassword(encoder.encode(userDTO.password()));
            user.setRoles(Arrays.asList(new Role("ROLE_USER")));
            userService.save(user);



            RefreshToken refreshToken = new RefreshToken();
            String rToken = jwtHelper.generateRefreshToken(user, refreshToken);
            refreshToken.setOwner(user);
            refreshToken.setRefreshToken(rToken);
            refreshTokenRepository.save(refreshToken);

            String accessToken = jwtHelper.generateAccessToken(user);


            response.addCookie(cookieBuilder(user, accessToken, rToken));
            return ResponseEntity.ok("User registered");
        }
        throw new IncorrectFormatException("Bad email/password format");
    }

    @Transactional
    @PostMapping("/register/super/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO userDTO,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws EmailAlreadyRegisteredException {
        User user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(encoder.encode(userDTO.password()));
        user.setRoles(Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER")));
        userService.save(user);

        RefreshToken refreshToken = new RefreshToken();
        String rToken = jwtHelper.generateRefreshToken(user, refreshToken);
        refreshToken.setOwner(user);
        refreshToken.setRefreshToken(rToken);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(user);

        response.addCookie(cookieBuilder(user, accessToken, rToken));
        return ResponseEntity.ok("Admin registered");
    }

}
