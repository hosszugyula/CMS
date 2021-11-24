package com.example.CMS.controller;

import com.example.CMS.model.AppUser;
import com.example.CMS.model.AppUserDetails;
import com.example.CMS.model.JobAdvertisement;
import com.example.CMS.service.JobAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JobsController {
    private JobAdvertisementService jobAdvertisementService;

    @Autowired
    public void setJobAdvertisementService(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
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

    @GetMapping("/jobs")
    public String addJobForm(Model model) {
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();

        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        JobAdvertisement job = new JobAdvertisement();
        model.addAttribute("job", job);
        return "jobAdvertisement/jobAdvertisements.html";

    }

    @PostMapping("/jobs")
    public String addJobSubmit(@ModelAttribute("job") JobAdvertisement job, Model model) throws Exception {

        try {
            if (job.equals(jobAdvertisementService.saveJobAdvertisement(job))) {
                JobAdvertisement jobActual = new JobAdvertisement();
                model.addAttribute("job", jobActual);
                model.addAttribute("error", false);

                List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();
                model.addAttribute("jobAdvertisementList", jobAdvertisementList);
                return "jobAdvertisement/jobAdvertisements.html";
            }
            ;
        } catch (IllegalArgumentException e) {

            model.addAttribute("job", job);
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
            List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();
            model.addAttribute("jobAdvertisementList", jobAdvertisementList);
            return "jobAdvertisement/jobAdvertisements.html";
        }
        model.addAttribute("job", job);
        model.addAttribute("error", true);
        model.addAttribute("message", "Something went wrong!!");
        return "jobAdvertisement/jobAdvertisements.html";
    }
}
