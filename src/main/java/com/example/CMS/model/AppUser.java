package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String encryptedPassword;
    private AppUserDetails details;

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> roleNames = new ArrayList<>();

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

}
