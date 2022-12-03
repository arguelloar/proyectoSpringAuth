package com.araa.project.Helper;


import java.util.regex.Pattern;

public class Validator {

    public static boolean emailPattern(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean pwPattern(String password) {
        String regexPattern = "^(?=.*[0-9]).{8,15}$";
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }
}
