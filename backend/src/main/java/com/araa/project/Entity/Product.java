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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "products_photo",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "photo_id", referencedColumnName = "id"))
    private Collection<Photo> photos;

    public void removePhoto(Long id){
        Optional<Photo> _photo = this.photos.stream()
                .filter(photo -> photo.getId() == id)
                .findFirst();
        if(_photo.isPresent()){
            this.photos.remove(_photo.get());
        }else {
            throw new ProductNotFoundException("Photo with id "+id+" not found");
        }
    }

}
