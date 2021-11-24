package com.example.CMS.controller;

import com.example.CMS.model.JobAdvertisement;
import com.example.CMS.service.JobAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class JobsController {
    private JobAdvertisementService jobAdvertisementService;

    @Autowired
    public void setJobAdvertisementService(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    @RequestMapping(value = "/jobs")
    public String listOfJobsPage(Model model) {


        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();

        model.addAttribute("jobAdvertisementList", jobAdvertisementList);

        return "jobAdvertisement/jobAdvertisements.html";
    }

    @RequestMapping(value = "/jobs/{id}")
    public String jobPage(@PathVariable(value = "id") Long id, Model model) throws Exception {

        JobAdvertisement jA = jobAdvertisementService.getJobAdvertisementById(id);
        //TODO 404 or error page
        if (jA == null) {
            throw new Exception("Nincs ilyen id-val hírdetés");
        }

        model.addAttribute("jobAdvertisement", jA);

        return "jobAdvertisement/jobAdvertisementPage.html";
    }
}
