package com.araa.project.Controller;


import com.araa.project.Entity.Product;
import com.araa.project.DTO.ProductDTO;
import com.araa.project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private ProductService productService;



    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> productGet(@PathVariable Long id){
        Optional<Product> productById = productService.findById(id);
        if(productById.isPresent()){
            return new ResponseEntity<>(productById.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<?> productList(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }

    //Implementar si ya existe agregar mas stock

    @PostMapping("/admin/add")
    public @ResponseBody ResponseEntity<String> productAdd(@Validated @ModelAttribute ProductDTO productDTO){
        productService.save(productDTO);
        return ResponseEntity.ok("Product added");
    }

    //Si ya existe updatear info o stock

    @PostMapping("/admin/update")
    public @ResponseBody ResponseEntity<String> productUpdate(@Validated @ModelAttribute ProductDTO productDTO){
        productService.save(productDTO);
        return ResponseEntity.ok("Product added");
    }

    //Si existe borrar
    @PostMapping("/admin/delete")
    public @ResponseBody ResponseEntity<String> productDelete(@Validated @ModelAttribute ProductDTO productDTO){
        productService.save(productDTO);
        return ResponseEntity.ok("Product added");
    }
}
