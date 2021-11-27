package com.example.CMS.repository;

import com.example.CMS.model.JobAdvertisement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobAdvertisementRepository extends CrudRepository<JobAdvertisement, Long> {


    @Override
    Optional<JobAdvertisement> findById(Long id);

    @Override
    List<JobAdvertisement> findAll();
}
