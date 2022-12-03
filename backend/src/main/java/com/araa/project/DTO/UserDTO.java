package com.araa.project.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Data
public class UserDTO {
    private String email;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
