package com.araa.project.Controller;


import com.araa.project.DTO.ProductDTO;
import com.araa.project.Entity.Product;
import com.araa.project.Exception.ProductNotFoundException;
import com.araa.project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private ProductService productService;


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> productGet(@PathVariable Long id){
        Optional<Product> productById = productService.findById(id);
        if(productById.isPresent()){
            return new ResponseEntity<>(productById.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public @ResponseBody ResponseEntity<?> productList(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public @ResponseBody ResponseEntity<String> productAdd(@RequestPart @ModelAttribute ProductDTO productDTO) throws IOException {

        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setStock(productDTO.stock());
        product.setDescription(productDTO.description());
        product.setPhoto(productDTO.photo());
        productService.save(product);
        return ResponseEntity.ok("Product added");
    }

    //Si ya existe updatear info o stock

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<String> productUpdate(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        if(productDTO.name() != null){
            Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with "+id+" not found"));
            product.setName(productDTO.name());
            product.setPrice(productDTO.price());
            product.setStock(productDTO.stock());
            product.setDescription(productDTO.description());
            productService.save(product);
            return ResponseEntity.ok("Product with "+id+" updated");
        }
        return new ResponseEntity<>("Bad request",HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}/photo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> productPhotoUpdate(@PathVariable Long id, @RequestPart String photo) throws IOException {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with "+id+" not found"));
        product.setPhoto(photo);
        productService.save(product);

        return ResponseEntity.ok("Product with id : "+id+" photo updated");
    }


    //Si existe borrar
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<String> productDelete(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            productService.deleteById(id);
            return ResponseEntity.ok("Product with id "+id+" has been deleted");
        }
        return new ResponseEntity<>("Product with id "+id+"not found",HttpStatus.NOT_FOUND);
    }
}
