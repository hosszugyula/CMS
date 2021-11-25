package com.example.CMS.utils;

import com.example.CMS.model.AppUser;
import com.example.CMS.model.AppUserDetails;
import com.example.CMS.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    @Value("${initUsers}")
    private boolean initUsers;

    private final AppUserRepository appUserRepo;
    private final BCryptPasswordEncoder encoder;


    @PostConstruct
    private void initForTesting() {
        AppUserDetails details = new AppUserDetails();
        details.setBirth_date(LocalDate.of(2000, 11, 16));
        details.setFirst_name("Kiraly");
        details.setSur_name("Baro");
        details.setIntroduction("Szeretem a penzt");


        if (initUsers) {
            appUserRepo.save(AppUser.builder()
                    .userName("dbadmin1")
                    .encryptedPassword(encoder.encode("123"))
                    .roleNames(List.of("ROLE_ADMIN"))
                    .build());


            appUserRepo.save(AppUser.builder()
                    .userName("dbuser1")
                    .encryptedPassword(encoder.encode("123"))
                    .roleNames(List.of("ROLE_USER"))
                    .details(details)
                    .build());

            details.setSur_name("Csaszar");

            appUserRepo.save(AppUser.builder()
                    .userName("dbuser2")
                    .encryptedPassword(encoder.encode("123"))
                    .roleNames(List.of("ROLE_USER"))
                    .details(details)
                    .build());
        }
    }


}
