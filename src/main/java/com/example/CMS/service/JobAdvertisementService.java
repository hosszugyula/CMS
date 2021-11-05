package com.example.CMS.service;

import com.example.CMS.repository.JobAdvertisementRepository;
import com.example.CMS.model.JobAdvertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
