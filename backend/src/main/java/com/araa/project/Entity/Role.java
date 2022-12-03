package com.araa.project.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
