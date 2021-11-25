package com.example.CMS.controller;

import com.example.CMS.model.AppUser;
import com.example.CMS.model.AppUserDetails;
import com.example.CMS.model.JobAdvertisement;
import com.example.CMS.service.AppUserService;
import javassist.NotFoundException;
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

    @GetMapping("/users/update/{id}")
    public String getUserForUpdate(@PathVariable(value = "id") Long id, Model model) {
        List<AppUser> appUsersList = appUserService.getUsers();

        model.addAttribute("appUsersList", appUsersList);
        AppUser user = new AppUser();
        try {
            user = appUserService.getAppUserForUpdateById(id);
            System.out.println("getUserForUpdate"+user.getEncryptedPassword());
        } catch (NotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
        }
        System.out.println(user.getId());
        model.addAttribute("user", user);
        return "appUser/appUsers.html";

    }

    @PostMapping("/users")
    public String addUserSubmit(@ModelAttribute("user") AppUser user, Model model) {

        System.out.println(user.getId());
        try {
            if (user.getId() == null) {
                if (user.equals(appUserService.saveAppUser(user))) {
                    modelCreator(model, new AppUser(), false, null);
                    return "appUser/appUsers.html";
                }
            } else {
                System.out.println("addUserSubmit:" + user.getEncryptedPassword());
                if (user.equals(appUserService.updateAppUser(user))) {
                    modelCreator(model, new AppUser(), false, null);
                    return "appUser/appUsers.html";
                }
            }

        } catch (IllegalArgumentException e) {

            modelCreator(model, user, true, e.getMessage());
            return "appUser/appUsers.html";
        }
        modelCreator(model, user, true, "Something went wrong!!");
        return "appUser/appUsers.html";

    }

    @RequestMapping(value = "/users/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Long id, @ModelAttribute("user") AppUser user, Model model) {
        appUserService.delete(id);
        List<AppUser> appUsersList = appUserService.getUsers();
        model.addAttribute("appUsersList", appUsersList);
        return "appUser/appUsers.html";
    }

    private Model modelCreator(Model model, AppUser user, boolean error, String message) {
        model.addAttribute("user", user);
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        List<AppUser> appUsersList = appUserService.getUsers();
        model.addAttribute("appUsersList", appUsersList);
        return model;
    }

}
