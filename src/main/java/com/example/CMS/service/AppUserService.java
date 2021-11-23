package com.example.CMS.service;

import com.example.CMS.model.AppUser;
import com.example.CMS.repository.AppUserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    public AppUser getAppUserById(Long id) throws NotFoundException {

        Optional<AppUser> appUser = userRepository.findById(id);

        if (appUser.isEmpty()) {
            throw new NotFoundException("User does not exist with this ID");
        } else {
            return appUser.get();
        }

    }

    public List<AppUser> getAppUsers() {
        return userRepository.findAll();
    }

    public List<AppUser> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(x -> !x.getRoleNames().contains("ROLE_ADMIN"))
                .collect(Collectors.toList());
    }

    public AppUser saveAppUser(AppUser user) throws Exception {

        AppUser appUser = userRepository.findByUserName(user.getUserName());

        if (appUser != null) {
            System.out.println("Username is already taken ! " + user.getUserName());
            throw new Exception("Username is already taken ! " + user.getUserName());
        }
        return userRepository.save(user);
    }

}
