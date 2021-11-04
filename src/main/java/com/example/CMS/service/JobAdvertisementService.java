package com.example.CMS.service;

import com.example.CMS.repository.JobAdvertisementRepository;
import com.example.CMS.model.JobAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobAdvertisementService {


    private JobAdvertisementRepository jobAdvertisementRepo;

    @Autowired
    public void setJobAdvertisementRepo(JobAdvertisementRepository jobAdvertisementRepo) {
        this.jobAdvertisementRepo = jobAdvertisementRepo;
    }

    public JobAdvertisement getJobAdvertisementById(long id){
        Optional<JobAdvertisement> jA = jobAdvertisementRepo.findById(id);

        if(jA.isEmpty()){
            return null;
        }else {
            return jA.get();
        }

    }
}
