package com.araa.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String email;
    private String accessToken;
    private String remotAddress;

}
