package com.example.CMS.service;

import com.example.CMS.Repository.JobAdvertismentRepository;
import com.example.CMS.model.JobAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobAdvertisementService {


    private JobAdvertismentRepository jobAdvertismentRepo;

    @Autowired
    public void setJobAdvertismentRepo(JobAdvertismentRepository jobAdvertismentRepo) {
        this.jobAdvertismentRepo = jobAdvertismentRepo;
    }

    public JobAdvertisement getJobAdvertisementById(long id){
        Optional<JobAdvertisement> jA = jobAdvertismentRepo.findById(id);

        if(jA.isEmpty()){
            return null;
        }else {
            return jA.get();
        }

    }
}
