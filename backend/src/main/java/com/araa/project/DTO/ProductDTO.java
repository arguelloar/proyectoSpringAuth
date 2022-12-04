package com.araa.project.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductDTO {
    private String name;
    private int stock;
    private BigDecimal price;
    private String description;
}
