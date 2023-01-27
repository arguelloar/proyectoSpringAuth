package com.araa.project.Helper;


import java.util.regex.Pattern;

public class Validator {

    public static boolean emailPattern(String emailAddress) {
        String regexPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean pwPattern(String password) {
        String regexPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}$";
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }
}
