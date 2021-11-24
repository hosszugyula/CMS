package com.example.CMS.repository;

import com.example.CMS.model.AppUser;
import com.example.CMS.model.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUserName(String userName);

    String getPasswordOnlyById(Long id);


    boolean existsByUserName(String username);
}
