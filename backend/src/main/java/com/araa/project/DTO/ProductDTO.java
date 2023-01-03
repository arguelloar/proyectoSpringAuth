package com.araa.project.DTO;
import java.math.BigDecimal;
public record ProductDTO(String name, BigDecimal price, String description, int stock) {
}
