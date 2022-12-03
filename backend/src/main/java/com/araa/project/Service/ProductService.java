package com.araa.project.Service;


import com.araa.project.Entity.Product;
import com.araa.project.DTO.ProductDTO;
import com.araa.project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll(){
       return repository.findAll();
    }


    public Optional<Product> findById(Long aLong){
        return repository.findById(aLong);
    }

    public Optional<Product> findByName(String name){
        return findByName(name);
    }

    public void save(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setPhoto(productDTO.getPhoto());
        repository.save(product);
    }


}
