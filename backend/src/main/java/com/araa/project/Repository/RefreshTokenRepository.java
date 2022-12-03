package com.araa.project.Repository;

import com.araa.project.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM proyecto.refresh_token WHERE owner_id= ?1")
    void deleteById(Long id);


    @Query(nativeQuery = true, value = "SELECT * FROM proyecto.refresh_token WHERE owner_id= ?1")
    Optional<RefreshToken> findById(Long aLong);
}
