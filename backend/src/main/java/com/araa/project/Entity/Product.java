package com.araa.project.Entity;


import com.araa.project.Exception.ProductNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NonNull
    private String name;


    @NonNull
    private int stock;

    @NonNull
    private BigDecimal price;

    @NonNull
    private String description;

    @NonNull
    @Lob
    @Column(length = 100000, unique = true)
    private byte[] photo;


}
