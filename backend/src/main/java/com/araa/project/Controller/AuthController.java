package com.araa.project.Controller;


import com.araa.project.DTO.UserDTO;
import com.araa.project.Entity.RefreshToken;
import com.araa.project.Entity.Role;
import com.araa.project.Entity.User;
import com.araa.project.Exception.EmailAlreadyRegisteredException;
import com.araa.project.Exception.IncorrectFormatException;
import com.araa.project.Helper.CookieHelper;
import com.araa.project.Helper.JwtHelper;
import com.araa.project.Helper.UserResponse;
import com.araa.project.Helper.Validator;
import com.araa.project.Repository.RefreshTokenRepository;
import com.araa.project.Service.TokenVerifierService;
import com.araa.project.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import java.util.Optional;

import static com.araa.project.Helper.CookieHelper.*;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenVerifierService tokenVerifierService;

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
    @ResponseBody
    public ResponseEntity<?> logIn(@RequestBody UserDTO userDTO,
                                   HttpServletResponse response) {

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

            UserResponse userResponse = new UserResponse(userData.getEmail(), true);
            response.setHeader(HttpHeaders.SET_COOKIE, cookieBuilder(userData,accessToken,rToken).toString());
            return new ResponseEntity(userResponse,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @AuthenticationPrincipal User user) {

        refreshTokenRepository.deleteById(user.getId());
        response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie().toString());
        return ResponseEntity.ok("User logout");
    }

    @Transactional
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws EmailAlreadyRegisteredException {

        Optional<User> userByEmail = userService.findByEmail(userDTO.email());
        userByEmail.ifPresent(s -> {
            throw new EmailAlreadyRegisteredException("Email already registered");
        });

        User user = new User();

        if (Validator.emailPattern(userDTO.email())
                && Validator.pwPattern(userDTO.password())) {
            user.setFirstName(userDTO.firstName());
            user.setLastName(userDTO.lastName());
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


            UserResponse userResponse = new UserResponse(user.getEmail(),true);
            response.setHeader(HttpHeaders.SET_COOKIE, cookieBuilder(user,accessToken,rToken).toString());
            return new ResponseEntity(userResponse,HttpStatus.OK);
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

        response.setHeader(HttpHeaders.SET_COOKIE, cookieBuilder(user,accessToken,rToken).toString());
        return ResponseEntity.ok("Admin registered");
    }

    @PostMapping("/token")
    public ResponseEntity<?> cookieAuth(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user){
        if(tokenVerifierService.isAuthenticated(cookieGet(request))){
            return new ResponseEntity<>(user.getRoles(),HttpStatus.OK);
        }
        response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie().toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
