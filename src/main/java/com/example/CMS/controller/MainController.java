package com.example.CMS.controller;

import com.example.CMS.model.AppUser;
import com.example.CMS.model.AppUserDetails;
import com.example.CMS.model.JobAdvertisement;
import com.example.CMS.service.AppUserService;
import com.example.CMS.service.JobAdvertisementService;
import com.example.CMS.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private JobAdvertisementService jobAdvertisementService;
    private AppUserService appUserService;

    @Autowired
    public void setJobAdvertisementService(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // (1) (en)
        // After user login successfully.
        // (vi)
        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
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

    @RequestMapping(value = "/users")
    public String listOfUsersPage(Model model) {


        List<AppUser> appUsersList = appUserService.getUsers();

        model.addAttribute("appUsersList", appUsersList);

        return "appUser/appUsers.html";
    }

    @RequestMapping(value = "/users/{id}")
    public String userPage(@PathVariable(value = "id") Long id, Model model) throws Exception {

        AppUser aU = appUserService.getAppUserById(id);
        //TODO 404 or error page
        if (aU == null) {
            throw new Exception("Nincs ilyen id-val felhasználó");
        }

        model.addAttribute("appUser", aU);

        return "appUser/appUsersPage.html";
    }


}
