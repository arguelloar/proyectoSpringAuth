package com.araa.project.Controller;


import com.araa.project.Entity.Photo;
import com.araa.project.Entity.Product;
import com.araa.project.DTO.ProductDTO;
import com.araa.project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
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


    @PostMapping("/admin/add")
    public @ResponseBody ResponseEntity<String> productAdd(@RequestPart @ModelAttribute ProductDTO productDTO, @RequestPart MultipartFile photo) throws IOException {

        Photo uploadPhoto = new Photo();
        uploadPhoto.setContentType(photo.getContentType());
        uploadPhoto.setName(photo.getOriginalFilename());
        uploadPhoto.setBytes(photo.getBytes());

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setPhotos(Arrays.asList(uploadPhoto));
        productService.save(product);
        return ResponseEntity.ok("Product added");
    }

    //Si ya existe updatear info o stock

    @PutMapping("/admin/update")
    public @ResponseBody ResponseEntity<String> productUpdate(@Validated @ModelAttribute ProductDTO productDTO){
        return ResponseEntity.ok("Product added");
    }

    //Si existe borrar
    @DeleteMapping("/admin/delete")
    public @ResponseBody ResponseEntity<String> productDelete(@Validated @ModelAttribute ProductDTO productDTO){
        return ResponseEntity.ok("Product added");
    }
}
