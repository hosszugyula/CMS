package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails {

    private String firstName;
    private String surName;
    private LocalDate birthDate;
    private List<String> interests;
    private String introduction;


}
