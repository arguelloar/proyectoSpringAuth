package com.araa.project.Controller;


import com.araa.project.DTO.ProductDTO;
import com.araa.project.Entity.Photo;
import com.araa.project.Entity.Product;
import com.araa.project.Exception.ProductNotFoundException;
import com.araa.project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public @ResponseBody ResponseEntity<String> productAdd(@RequestPart @ModelAttribute ProductDTO productDTO, @RequestPart MultipartFile[] photo) throws IOException {

        List<Photo> photoList = new ArrayList<>();
        Arrays.stream(photo).forEach(_photo -> {
            Photo uploadPhoto = new Photo();
            try {
                uploadPhoto.setContentType(_photo.getContentType());
                uploadPhoto.setName(_photo.getOriginalFilename());
                uploadPhoto.setBytes(_photo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            photoList.add(uploadPhoto);
        });

        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setStock(productDTO.stock());
        product.setDescription(productDTO.description());
        product.setPhotos(photoList);
        productService.save(product);
        return ResponseEntity.ok("Product added");
    }

    //Si ya existe updatear info o stock

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<String> productUpdate(@PathVariable Long id, @RequestPart @ModelAttribute ProductDTO productDTO){
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with "+id+" not found"));
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setStock(productDTO.stock());
        product.setDescription(productDTO.description());
        productService.save(product);
        return ResponseEntity.ok("Product with "+id+" updated");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}/photos")
    public @ResponseBody ResponseEntity<String> productPhotoUpdate(@PathVariable Long id, @RequestPart MultipartFile[] photo){
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with "+id+" not found"));
        Collection<Photo> photos = product.getPhotos();

        Arrays.stream(photo).forEach(_photo -> {
            Photo uploadPhoto = new Photo();
            try {
                uploadPhoto.setContentType(_photo.getContentType());
                uploadPhoto.setName(_photo.getOriginalFilename());
                uploadPhoto.setBytes(_photo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            photos.add(uploadPhoto);
        });

        product.setPhotos(photos);
        productService.save(product);

        return ResponseEntity.ok("Product photos updated on id :"+id);
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}/photo/{photoId}")
    public @ResponseBody ResponseEntity<String> productPhotoDelete(@PathVariable Long id, @PathVariable Long photoId){
        productService.findById(id).ifPresentOrElse((p) -> {
            Collection<Photo> photos = p.getPhotos();
            Optional<Photo> _photo = photos.stream()
                    .filter(photo -> photo.getId() == photoId)
                    .findFirst();

            if(_photo.isPresent()){
                photos.remove(_photo.get());
                p.setPhotos(photos);
                productService.save(p);
                productService.deletePhotoById(photoId);
            }else {
                throw new ProductNotFoundException("Product photo with id "+photoId+" not found");
            }
        },() -> {
            throw new ProductNotFoundException("Product with "+id+" not found");
        });
        return new ResponseEntity<>("Product with photo id "+id+"=>"+photoId+" has been deleted", HttpStatus.OK);
    }
}
