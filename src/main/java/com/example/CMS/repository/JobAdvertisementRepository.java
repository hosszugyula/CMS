package com.example.CMS.repository;

import com.example.CMS.model.JobAdvertisement;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobAdvertisementRepository extends CrudRepository<JobAdvertisement, Long> {

    @Override
    Optional<JobAdvertisement> findById(Long id);

}
