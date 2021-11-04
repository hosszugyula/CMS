package com.example.CMS.Repository;

import com.example.CMS.model.JobAdvertisement;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobAdvertismentRepository  extends CrudRepository<JobAdvertisement, Long> {

    @Override
    Optional<JobAdvertisement> findById(Long id);

}
