package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ElementCollection
    private List<String> roleNames;

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

}
