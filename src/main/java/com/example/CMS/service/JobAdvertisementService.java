package com.example.CMS.service;

import com.example.CMS.model.AppUser;
import com.example.CMS.repository.JobAdvertisementRepository;
import com.example.CMS.model.JobAdvertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepo;

    public JobAdvertisement getJobAdvertisementById(long id) {
        Optional<JobAdvertisement> jA = jobAdvertisementRepo.findById(id);

        if (jA.isEmpty()) {
            return null;
        } else {
            return jA.get();
        }

    }

    public List<JobAdvertisement> jobAdvertisements(){
        return jobAdvertisementRepo.findAll();
    }

    public JobAdvertisement saveJobAdvertisement(JobAdvertisement job) throws IllegalArgumentException {

        return jobAdvertisementRepo.save(job);
    }

    public JobAdvertisement updateJobAdvertisement(JobAdvertisement updatedJob) {

        return jobAdvertisementRepo.save(updatedJob);
    }

    public void delete(Long id) {
        jobAdvertisementRepo.deleteById(id);
    }
}
