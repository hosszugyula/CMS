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
            throw new IllegalArgumentException("Username is already taken: " + user.getUserName()+"from save");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setEncryptedPassword(encoder.encode(user.getEncryptedPassword()));

        return userRepository.save(user);
    }


    public AppUser updateAppUser(AppUser updatedUser) {

        AppUser originalUser = userRepository.findById(updatedUser.getId()).get();

        if (!originalUser.getUserName().equals(updatedUser.getUserName())){
            if (userRepository.existsByUserName(updatedUser.getUserName())) {
                System.out.println("Username is already taken: " + updatedUser.getUserName());
                throw new IllegalArgumentException("Username is already taken: " + updatedUser.getUserName()+"from update");
            }
        }
        System.out.println(updatedUser.getEncryptedPassword());
        if(updatedUser.getEncryptedPassword() == null){
            updatedUser.setEncryptedPassword(originalUser.getEncryptedPassword());
        }else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            updatedUser.setEncryptedPassword(encoder.encode(updatedUser.getEncryptedPassword()));

        }

        System.out.println(updatedUser.getEncryptedPassword());
        return userRepository.save(updatedUser);
    }
}
