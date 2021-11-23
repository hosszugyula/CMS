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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

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

    @GetMapping("/users")
    public  String addUserForm(Model model){
        List<AppUser> appUsersList = appUserService.getUsers();

        model.addAttribute("appUsersList", appUsersList);
        AppUser user = new AppUser();
        user.setDetails(new AppUserDetails());
        model.addAttribute("user",user);
        return "appUser/appUsers.html";

    }

    @PostMapping("/users")
    public  String addUserSubmit(@ModelAttribute("user") AppUser user, Model model) throws Exception {

        try {
            if(user.equals(appUserService.saveAppUser(user))){
                AppUser userActual = new AppUser();
                userActual.setDetails(new AppUserDetails());
                model.addAttribute("user",userActual);
                model.addAttribute("error",false);

                List<AppUser> appUsersList = appUserService.getUsers();
                model.addAttribute("appUsersList", appUsersList);
                return "appUser/appUsers.html" ;
            };
        }catch (IllegalArgumentException e){

            model.addAttribute("user",user);
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
            List<AppUser> appUsersList = appUserService.getUsers();
            model.addAttribute("appUsersList", appUsersList);
            return "appUser/appUsers.html";
        }
        model.addAttribute("user",user);
        model.addAttribute("error",true);
        model.addAttribute("message","Something went wrong!!");
        return "appUser/appUsers.html";

    }


}
