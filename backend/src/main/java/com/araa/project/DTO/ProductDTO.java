package com.araa.project.DTO;
import java.math.BigDecimal;
public record ProductDTO(String name, int stock, BigDecimal price, String description) {
}
