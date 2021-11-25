package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;

    @ElementCollection
    private List<String> interests = new ArrayList<>();


}
