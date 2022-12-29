package com.araa.project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String contentType;

    @NonNull
    private String name;

    @NonNull
    @Lob
    @Column(length = 100000, unique = true)
    private byte[] bytes;

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
}
