package com.auth.service.repository;

import com.auth.service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  UserCredRepository extends JpaRepository<UserCredential, String> {
    Optional<UserCredential> findByUsername(String username);

    Boolean existsByUsername(String username);


}
