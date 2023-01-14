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
    @Query("DELETE FROM RefreshToken r WHERE r.owner_id= ?1")
    void deleteById(Long id);


    @Query("SELECT u FROM RefreshToken u WHERE u.owner_id= ?1")
    Optional<RefreshToken> findById(Long aLong);
}
