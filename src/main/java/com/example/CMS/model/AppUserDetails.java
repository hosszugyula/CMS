package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails {

    private String firstName;
    private String surName;
    private String introduction;
    private LocalDate birthDate;

    @ElementCollection
    private List<String> interests = new ArrayList<>();


}
