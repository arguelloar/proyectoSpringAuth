package com.araa.project.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;
@Data
public class ProductDTO {
    private String name;
    private int stock;
    private BigDecimal price;

    //Need to implement
    private String photo;
}
