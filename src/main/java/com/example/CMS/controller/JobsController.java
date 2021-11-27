package com.example.CMS.controller;

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

    @GetMapping("/jobs/update/{id}")
    public String getJobForUpdate(@PathVariable(value = "id") Long id, Model model) {
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();

        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        JobAdvertisement job = new JobAdvertisement();
        job = jobAdvertisementService.getJobAdvertisementById(id);
        System.out.println("getJobForUpdate"+job.getId());
        System.out.println(job.getId());
        model.addAttribute("job", job);
        return "jobAdvertisement/jobAdvertisements.html";

    }

    @PostMapping("/jobs")
    public String addJobSubmit(@ModelAttribute("job") JobAdvertisement job, Model model) throws Exception {

        System.out.println(job.getId());
        try {
            if (job.getId() == null) {
                if (job.equals(jobAdvertisementService.saveJobAdvertisement(job))) {
                    modelCreator(model, new JobAdvertisement(), false, null);
                    return "jobAdvertisement/jobAdvertisements.html";
                }
            } else {
                if (job.equals(jobAdvertisementService.updateJobAdvertisement(job))) {
                    modelCreator(model, new JobAdvertisement(), false, null);
                    return "jobAdvertisement/jobAdvertisements.html";
                }
            }

        } catch (IllegalArgumentException e) {

            modelCreator(model, job, true, e.getMessage());
            return "jobAdvertisement/jobAdvertisements.html";
        }
        modelCreator(model, job, true, "Something went wrong!!");
        return "jobAdvertisement/jobAdvertisements.html";
    }

    @RequestMapping(value = "/jobs/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Long id, @ModelAttribute("job") JobAdvertisement job, Model model) {
        jobAdvertisementService.delete(id);
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();
        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        return "jobAdvertisement/jobAdvertisements.html";
    }

    private Model modelCreator(Model model, JobAdvertisement job, boolean error, String message) {
        model.addAttribute("job", job);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<JobAdvertisement> jobAdvertisementList = jobAdvertisementService.jobAdvertisements();
        model.addAttribute("jobAdvertisementList", jobAdvertisementList);
        return model;
    }

}
