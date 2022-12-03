package com.araa.project.Entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "product_list")
@Entity
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int stock;

    private BigDecimal price;

    private String photo;
}
