package com.araa.project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
public class Photo {

    public Photo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String contentType;

    @NonNull
    private String name;

    @JsonIgnore
    @NonNull
    @Lob
    @Column(length = 100000, unique = true)
    private byte[] bytes;
}
