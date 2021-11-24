package com.example.CMS.service;

import com.example.CMS.model.AppUser;
import com.example.CMS.repository.AppUserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public AppUser getAppUserForUpdateById(Long id) throws NotFoundException {

        Optional<AppUser> OptionalAppUser = userRepository.findById(id);

        if (OptionalAppUser.isEmpty()) {
            throw new NotFoundException("User does not exist with this ID"+id);
        } else {
            AppUser user = OptionalAppUser.get();
            user.setEncryptedPassword(null);
            return user;
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

    public AppUser saveAppUser(AppUser user) throws IllegalArgumentException {

        if (userRepository.existsByUserName(user.getUserName())) {
            System.out.println("Username is already taken: " + user.getUserName());
            throw new IllegalArgumentException("Username is already taken: " + user.getUserName());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setEncryptedPassword(encoder.encode(user.getEncryptedPassword()));

        return userRepository.save(user);
    }

 
}
