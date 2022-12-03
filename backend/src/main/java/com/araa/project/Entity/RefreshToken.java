package com.araa.project.Entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String refreshToken;
}
