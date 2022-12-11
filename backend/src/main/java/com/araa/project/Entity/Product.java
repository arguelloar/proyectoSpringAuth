package com.araa.project.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_photo",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "photo_id", referencedColumnName = "id"))
    private Collection<Photo> photos;

}
