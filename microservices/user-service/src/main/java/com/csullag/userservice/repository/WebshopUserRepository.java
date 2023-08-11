package com.csullag.userservice.repository;

import com.csullag.userservice.model.WebshopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebshopUserRepository extends JpaRepository<WebshopUser, Long> {

    Boolean existsByUsername(String username);

    Optional<WebshopUser> findByUsername(String username);

    Optional<WebshopUser> findByFacebookId(String userId);
}
