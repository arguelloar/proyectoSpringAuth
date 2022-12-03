package com.araa.project.Helper;

import com.araa.project.Entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieBuilder {

    public static Cookie cookieBuilder(User user, String accessToken, String refreshToken){
        String cookie = user.getEmail()+"%"+accessToken+"&%"+refreshToken;
        Cookie setCookie = new Cookie("userLogin",cookie);
        setCookie.setHttpOnly(true);
        setCookie.setPath("/api");
        setCookie.setDomain("localhost");
        return setCookie;
    }

    public static Cookie deleteCookie(){
        Cookie setCookie = new Cookie("userLogin",null);
        setCookie.setHttpOnly(true);
        setCookie.setPath("/api");
        setCookie.setDomain("localhost");
        setCookie.setMaxAge(0);
        return setCookie;
    }
}
