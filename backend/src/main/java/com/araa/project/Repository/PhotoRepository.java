package com.araa.project.Repository;

import com.araa.project.Entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo,Long>{
}
