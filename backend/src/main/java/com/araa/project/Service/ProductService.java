package com.araa.project.Service;

import com.araa.project.Entity.Product;
import com.araa.project.Repository.PhotoRepository;
import com.araa.project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoRepository photoRepository;

    public List<Product> findAll(){
       return productRepository.findAll();
    }


    public Optional<Product> findById(Long aLong){
        return productRepository.findById(aLong);
    }

    public Optional<Product> findByName(String name){
        return findByName(name);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public void deletePhotoById(Long id){
        photoRepository.deleteById(id);
    }

}
