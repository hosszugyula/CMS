package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails {

    private String first_name;
    private String sur_name;
    private String introduction;
    private LocalDate birth_date;

    @ElementCollection
    private List<String> interests = new ArrayList<>();


}
