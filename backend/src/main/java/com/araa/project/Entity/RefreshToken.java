package com.araa.project.Entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RefreshToken {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User owner;

    private String refreshToken;
}
