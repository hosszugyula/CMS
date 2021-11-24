package com.example.CMS.controller;

import com.example.CMS.model.AppUser;
import com.example.CMS.model.AppUserDetails;
import com.example.CMS.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsersController {

    private AppUserService appUserService;

    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
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
    public String addUserForm(Model model) {
        List<AppUser> appUsersList = appUserService.getUsers();

        model.addAttribute("appUsersList", appUsersList);
        AppUser user = new AppUser();
        user.setDetails(new AppUserDetails());
        model.addAttribute("user", user);
        return "appUser/appUsers.html";

    }

    @PostMapping("/users")
    public String addUserSubmit(@ModelAttribute("user") AppUser user, Model model) throws Exception {

        try {
            if (user.equals(appUserService.saveAppUser(user))) {
                AppUser userActual = new AppUser();
                userActual.setDetails(new AppUserDetails());
                model.addAttribute("user", userActual);
                model.addAttribute("error", false);

                List<AppUser> appUsersList = appUserService.getUsers();
                model.addAttribute("appUsersList", appUsersList);
                return "appUser/appUsers.html";
            }
            ;
        } catch (IllegalArgumentException e) {

            model.addAttribute("user", user);
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
            List<AppUser> appUsersList = appUserService.getUsers();
            model.addAttribute("appUsersList", appUsersList);
            return "appUser/appUsers.html";
        }
        model.addAttribute("user", user);
        model.addAttribute("error", true);
        model.addAttribute("message", "Something went wrong!!");
        return "appUser/appUsers.html";

    }

}
