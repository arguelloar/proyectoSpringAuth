package com.araa.project.Repository;

import com.araa.project.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Override
    List<Product> findAll();

    @Override
    Optional<Product> findById(Long aLong);

    Optional<Product> findByName(String name);

}
